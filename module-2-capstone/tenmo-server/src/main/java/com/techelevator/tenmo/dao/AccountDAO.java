package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.stereotype.Component;


@Component
public interface AccountDAO {

    public double viewBalance(double id);

    public void transfersFunds(Transfers transfer);


}
