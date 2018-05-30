package controllers;


import POJOs.DTOs.*;
import POJOs.TransferPOJO;
import services.MyService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("/controller")
public class MyController {

    @Path("/transferOperation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void transferOperation(TransferOperationDTO in) {

        if (in != null) {
            MyService.transferOperation(in);
        } else {
            System.out.println("No JSON to read");
        }
    }

    @Path("/depositOperation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void depositOperation(DepositOperationDTO in) {
        if (in != null) {
            MyService.depositOperation(in);
        } else {
            System.out.println("No JSON to read");
        }
    }

    @Path("/withdrawalOperation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void withdrawalOperation(WithdrawalOperationDTO in) {
        if (in != null) {
            MyService.withdrawalOperation(in);
        } else {
            System.out.println("No JSON to read");
        }
    }

    @Path("/getAccountInfo/{idAccount}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AccountInfoDTO getAccountInfo(@PathParam("idAccount") Integer idAccount) {
        return MyService.getAccountInfo(idAccount);
    }

    @Path("/transactionInfo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<TransactionInfoReturnDTO> transactionInfo(TransactionInfoDTO in) {
        if (in != null) {
            return MyService.transactionInfo(in);
        } else {
            return null;
        }
    }

    @Path("/transactionsForAccount/{idAccount}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TransferPOJO> transactionsForAccount(@PathParam("idAccount") Integer idAccount) {
        return MyService.getTransactionsForAccount(idAccount);
    }

    @Path("/testPhoenix")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> testPhoenix() {
        return MyService.testPhoenix();
    }

}