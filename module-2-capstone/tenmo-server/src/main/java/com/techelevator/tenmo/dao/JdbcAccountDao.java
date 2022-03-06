package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class JdbcAccountDao implements AccountDAO{
    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double viewBalance(double id){
        double balance = 0;
        String sqlBalanceSelect = "Select balance FROM accounts WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlBalanceSelect, id);
        while (results.next()){
            balance = results.getDouble("balance");
        }
        return balance;
    }

    @Override
    public void transfersFunds(Transfers transfers) {
        BigDecimal fromBalance = new BigDecimal(0);
        BigDecimal toBalance = new BigDecimal(0);
        BigDecimal transfersAmount = new BigDecimal(transfers.getAmount());
        String sqlTransfersSelectFrom = "SELECT * FROM accounts WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlTransfersSelectFrom, transfers.getAccountFrom());
        while (result.next()){
            fromBalance = (new BigDecimal(result.getDouble("balance")).setScale(2, RoundingMode.HALF_UP));
        }
        String sqlTransfersSelectTo = "SELECT * FROM accounts WHERE account_id = ?";
        SqlRowSet result2 = jdbcTemplate.queryForRowSet(sqlTransfersSelectTo, transfers.getAccountTo());
        while (result2.next()){
            toBalance = (new BigDecimal(result2.getDouble("balance")).setScale(2, RoundingMode.HALF_UP));
        }
        fromBalance = fromBalance.subtract(transfersAmount);
        toBalance = toBalance.add(transfersAmount);
        double fromBalanced = fromBalance.doubleValue();
        double toBalanced = toBalance.doubleValue();
        String sqlTransferFrom = "UPDATE accounts SET balance = ?  WHERE account_id = ?";
        jdbcTemplate.update(sqlTransferFrom, fromBalanced, transfers.getAccountFrom());

        String sqlTransferTo = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sqlTransferTo, toBalanced, transfers.getAccountTo());

        String sqlUpdateStatus = "UPDATE transfers SET transfer_status_id = 2 WHERE transfer_id = ?";
        jdbcTemplate.update(sqlUpdateStatus, transfers.getId());


    }
}
