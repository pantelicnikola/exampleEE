package services;

import connections.PostgresConnection;

import java.sql.*;

public class UtilService {

    public static boolean checkAccountExists(int idAccount, Connection con) {

        String query = "select * from public.account where account_number = ?";
        PreparedStatement ps = null;
        boolean ret = false;

        try {

            ps = con.prepareStatement(query);
            ps.setInt(1, idAccount);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Account " + idAccount + " exists");
                ret =  true;
            } else {
                System.out.println("Account " + idAccount + " does not exist");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }


    public static boolean checkSufficientFunds(int idFromAccount, double amount, Connection con) {

        boolean ret = false;
        PreparedStatement ps = null;
        try {

            String query = "select * from public.account where account_number = ? and balance > ?";

            ps = con.prepareStatement(query);
            ps.setInt(1, idFromAccount);
            ps.setDouble(2, amount);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Account " + idFromAccount + " has sufficient funds");
                ret =  true;
            } else {
                System.out.println("Account " + idFromAccount + " has insufficient funds");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }

    public static boolean checkAmountPositive(double amount) {
        if (amount > 0) {
            return true;
        } else {
            System.out.println("Amount is negative number");
            return false;
        }
    }

//    public static boolean checkDateFormat(String format, String date) {
//        boolean ret = false;
//
//    }

}
