package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransfersDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController

public class Controller {
    private AccountDAO accountDao;
    private TransfersDao transfersDao;
    private UserDao userDao;

    public Controller(AccountDAO accountDao, TransfersDao transfersDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transfersDao = transfersDao;
        this.userDao = userDao;

    }

    @RequestMapping(path = "users", method = RequestMethod.GET)
    public List<User> list() {
        return userDao.findAll();
    }
    @RequestMapping(path = "user-ids/", method = RequestMethod.GET)
    public User findUser(@RequestParam int id){
        User user = userDao.findUserById(id);
        return user;
    }

    @RequestMapping(path = "user-names/", method = RequestMethod.GET)
    public User findUser(@RequestParam String name){
        User user = userDao.findByUsername(name);
        return user;
    }
    @RequestMapping(path = "users/{id}/balance", method = RequestMethod.GET)
    public double viewBalance(@PathVariable int id){
        return accountDao.viewBalance(id);
    }
    @RequestMapping(path = "new-transfers", method = RequestMethod.POST)
    public Transfers newTransfers(@RequestBody Transfers transfers){
        return transfers = transfersDao.createTransfers(transfers.getTypeId(), transfers.getStatusId(),
                transfers.getAccountFrom(), transfers.getAccountTo(), transfers.getAmount());
    }
    @RequestMapping(path = "new-transfers/approved", method = RequestMethod.PUT)
    public void transfersMoney(@RequestBody Transfers transfers){
        accountDao.transfersFunds(transfers);
    }
    @RequestMapping(path = "transfers/{id}",method = RequestMethod.GET)
    public Transfers viewTransfersById(@PathVariable("id") int transfersId){
        return transfersDao.viewTransfers(transfersId);
    }
    @RequestMapping(path = "user/{id}/transfers", method = RequestMethod.GET)
    public List<Transfers> getTransfersHistory(@PathVariable int id){
        return transfersDao.transfersHistory(id);
    }


}
