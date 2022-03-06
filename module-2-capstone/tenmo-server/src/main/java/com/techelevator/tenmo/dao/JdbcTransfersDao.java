package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransfersDao implements TransfersDao {
    private JdbcTemplate jdbcTemplate;
    public JdbcTransfersDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;

}

    @Override
    public List<Transfers> transfersHistory(int accountId) {
        List<Transfers> transferList = new ArrayList<Transfers>();
        String sql = "SELECT * FROM transfers WHERE account_to = ? OR account_from = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while(result.next()) {
            int id = result.getInt("transfer_id");
            int typeId = result.getInt("transfer_type_id");
            int statusId = result.getInt("transfer_status_id");
            int accountFrom = result.getInt("account_from");
            int accountTo = result.getInt("account_to");
            double amount = result.getDouble("amount");
            Transfers transfer = new Transfers(id, typeId, statusId, accountFrom, accountTo, amount);
            transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public Transfers viewTransfers(int transferId) {
        Transfers transfers = null;
        String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        while(result.next()) {
            int id = result.getInt("transfer_id");
            int typeId = result.getInt("transfer_type_id");
            int statusId = result.getInt("transfer_status_id");
            int accountFrom = result.getInt("account_from");
            int accountTo = result.getInt("account_to");
            double amount = result.getDouble("amount");
            transfers = new Transfers(id, typeId, statusId, accountFrom, accountTo, amount);
        }
        return transfers;
    }

    @Override
    public Transfers createTransfers(int type, int status, int from, int to, double amount) {
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, type, status, from, to, amount);
        String sql2 = "SELECT transfer_id FROM transfers ORDER BY transfer_id DESC LIMIT 1";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql2);
        int id = 0;
        while(result.next()) {
            id = result.getInt("transfer_id");
        }
        Transfers transfers = new Transfers(id, type, status, from, to, amount);
        return transfers;
    }
}
