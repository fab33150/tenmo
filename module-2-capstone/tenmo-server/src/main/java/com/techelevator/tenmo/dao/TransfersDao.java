package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransfersDao {
    public List<Transfers> transfersHistory(int accountId);
    public Transfers viewTransfers(int transferId);
    public Transfers createTransfers(int type, int status, int from, int to, double amount);
}
