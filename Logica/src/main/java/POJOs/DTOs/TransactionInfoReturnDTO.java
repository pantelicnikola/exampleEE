package POJOs.DTOs;

import java.sql.Timestamp;

public class TransactionInfoReturnDTO {

    private String movementType;
    private int from;
    private int to;
    private Timestamp date;
    private double amount;

    public TransactionInfoReturnDTO() {
    }

    public TransactionInfoReturnDTO(String movementType, int from, int to, Timestamp date, double amount) {

        this.movementType = movementType;
        this.from = from;
        this.to = to;
        this.date = date;
        this.amount = amount;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

