package services;

import java.sql.*;

import POJOs.DTOs.*;
import POJOs.TransferPOJO;
import connections.HBaseConnection;
import connections.PostgresConnection;

import java.util.ArrayList;
import java.util.List;

import static services.UtilService.checkAccountExists;
import static services.UtilService.checkAmountPositive;
import static services.UtilService.checkSufficientFunds;

public class MyService {
    public static void transferOperation(TransferOperationDTO in) {
        String queryUpdateTo = "update public.account set balance = balance + ? where account_number = ?";
        String queryUpdateFrom = "update public.account set balance = balance - ? where account_number = ?";
        String queryInsertTransaction = "insert into public.transfer ( from_account, to_account, amount, transfer_time, reason) values (?, ?, ?, now(), ?)";

        Connection con = null;
        PreparedStatement ps = null;

        try {

            con = PostgresConnection.getInstance().getConnection();

            if (!checkAccountExists(in.getFromAccount(), con)) {
                return;
            }
            if (!checkAccountExists(in.getToAccount(), con)) {
                return;
            }
            if (!checkSufficientFunds(in.getFromAccount(), in.getAmountOfTransfer(), con)) {
                return;
            }
            if (!checkAmountPositive(in.getAmountOfTransfer())) {
                return;
            }

            ps = con.prepareStatement(queryUpdateTo);
            ps.setDouble(1, in.getAmountOfTransfer());
            ps.setInt(2, in.getToAccount());
            ps.executeUpdate();

            ps = con.prepareStatement(queryUpdateFrom);
            ps.setDouble(1, in.getAmountOfTransfer());
            ps.setInt(2, in.getFromAccount());
            ps.executeUpdate();

            ps = con.prepareStatement(queryInsertTransaction);
            ps.setInt(1, in.getFromAccount());
            ps.setInt(2, in.getToAccount());
            ps.setDouble(3, in.getAmountOfTransfer());
            ps.setString(4, in.getReasonOfTransfer());
            ps.executeUpdate();

            con.commit();

        } catch(SQLException e){
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void depositOperation(DepositOperationDTO in) {

        String queryUpdateTo = "update public.account set balance = balance + ? where account_number = ?";
        String queryInsertTransaction = "insert into public.transfer ( to_account, amount, transfer_time, reason) values ( ?, ?, now(), ?)";

        Connection con = null;
        PreparedStatement ps = null;

        try {

            con = PostgresConnection.getInstance().getConnection();

            if (!checkAccountExists(in.getToAccount(), con)) {
                return;
            }
            if (!checkAmountPositive(in.getAmountOfTransfer())) {
                return;
            }

            ps = con.prepareStatement(queryUpdateTo);
            ps.setDouble(1, in.getAmountOfTransfer());
            ps.setInt(2, in.getToAccount());
            ps.executeUpdate();

            ps = con.prepareStatement(queryInsertTransaction);
            ps.setInt(1, in.getToAccount());
            ps.setDouble(2, in.getAmountOfTransfer());
            ps.setString(3, in.getReasonOfDeposit());
            ps.executeUpdate();

            con.commit();

        } catch(SQLException e){
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void withdrawalOperation(WithdrawalOperationDTO in) {

        String queryUpdateFrom = "update public.account set balance = balance - ? where account_number = ?";
        String queryInsertTransaction = "insert into public.transfer ( from_account, amount, transfer_time, reason) values ( ?, ?, now(), ?)";
        Connection con = null;
        PreparedStatement ps = null;

        try {

            con = PostgresConnection.getInstance().getConnection();

            if (!checkAccountExists(in.getFromAccount(), con)) {
                return;
            }
            if (!checkAmountPositive(in.getAmountOfTransfer())) {
                return;
            }

            ps = con.prepareStatement(queryUpdateFrom);
            ps.setDouble(1, in.getAmountOfTransfer());
            ps.setInt(2, in.getFromAccount());
            ps.executeUpdate();

            ps = con.prepareStatement(queryInsertTransaction);
            ps.setInt(1, in.getFromAccount());
            ps.setDouble(2, in.getAmountOfTransfer());
            ps.setString(3, in.getReasonOfWithdrawal());
            ps.executeUpdate();

            con.commit();

        } catch (Throwable t) {
            t.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static AccountInfoDTO getAccountInfo(int idAccount) {

        String query = "select * from public.account where account_number = ?";
        AccountInfoDTO account = new AccountInfoDTO();
        Connection con = null;
        PreparedStatement ps = null;

        try {

            con = PostgresConnection.getInstance().getConnection();

            if (!checkAccountExists(idAccount, con)) {
                return account;
            }

            ps = con.prepareStatement(query);
            ps.setInt(1, idAccount);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account.setAccountNumber(rs.getInt("account_number"));
                account.setHolder(rs.getString("holder"));
                account.setBalance(rs.getDouble("balance"));
            }

        } catch(Throwable t){
            t.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return account;
        }
    }

    public static List<TransactionInfoReturnDTO> transactionInfo(TransactionInfoDTO in) {

        String query = "select * from public.transfer where transfer_time between ? and ? and (from_account = ? or to_account = ?)";
        Connection con = null;
        PreparedStatement ps = null;
        List<TransactionInfoReturnDTO> ret = new ArrayList<TransactionInfoReturnDTO>();

        try {
            con = PostgresConnection.getInstance().getConnection();

            if (!checkAccountExists(in.getAccountNumber(), con)) {
                return ret;
            }

            ps = con.prepareStatement(query);
            ps.setTimestamp(1, in.getDateFrom());
            ps.setTimestamp(2, in.getDateTo());
            ps.setInt(3, in.getAccountNumber());
            ps.setInt(4, in.getAccountNumber());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransactionInfoReturnDTO transaction = new TransactionInfoReturnDTO();
                if (rs.getInt("from_account") == in.getAccountNumber()) {
                    transaction.setMovementType("from");
                } else {
                    transaction.setMovementType("to");
                }
                transaction.setDate(rs.getTimestamp("transfer_time"));
                transaction.setFrom(rs.getInt("from_account"));
                transaction.setTo(rs.getInt("to_account"));
                transaction.setAmount(rs.getDouble("amount"));
                ret.add(transaction);
            }


        } catch(SQLException e){
            e.printStackTrace();
        } finally {

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return ret;
        }
    }

    public static List<TransferPOJO> getTransactionsForAccount(int idAccount) {

        String query = "select * from public.transfer where from_account = ? or to_account = ?";
        Connection con = null;
        PreparedStatement ps = null;
        List<TransferPOJO> ret = new ArrayList<TransferPOJO>();

        try {
            con = PostgresConnection.getInstance().getConnection();

            if (!checkAccountExists(idAccount, con)) {
                return null;
            }

            ps = con.prepareStatement(query);
            ps.setInt(1, idAccount);
            ps.setInt(2, idAccount);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TransferPOJO transferPOJO = new TransferPOJO();
                transferPOJO.setId(rs.getInt("id"));
                transferPOJO.setFromAccount(rs.getInt("from_account"));
                transferPOJO.setToAccount(rs.getInt("to_account"));
                transferPOJO.setAmount(rs.getDouble("amount"));
                transferPOJO.setDate(rs.getTimestamp("transfer_time"));
                transferPOJO.setReason(rs.getString("reason"));
                ret.add(transferPOJO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static List<String> testPhoenix() {
        List<String> ret = new ArrayList<String>();
        try {
            String query = "select name from test_phoenix";
            Connection conn = HBaseConnection.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                ret.add(rs.getString("name"));
            }

            rs.close();
            stm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
