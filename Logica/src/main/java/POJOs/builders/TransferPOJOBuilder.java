package POJOs.builders;

import POJOs.TransferPOJO;

import java.sql.Timestamp;

public final class TransferPOJOBuilder {
    private int id;
    private int fromAccount;
    private int toAccount;
    private double amount;
    private Timestamp date;
    private String reason;

    private TransferPOJOBuilder() {
    }

    public static TransferPOJOBuilder aTransferPOJO() {
        return new TransferPOJOBuilder();
    }

    public TransferPOJOBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public TransferPOJOBuilder withFromAccount(int fromAccount) {
        this.fromAccount = fromAccount;
        return this;
    }

    public TransferPOJOBuilder withToAccount(int toAccount) {
        this.toAccount = toAccount;
        return this;
    }

    public TransferPOJOBuilder withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public TransferPOJOBuilder withDate(Timestamp date) {
        this.date = date;
        return this;
    }

    public TransferPOJOBuilder withReason(String reason) {
        this.reason = reason;
        return this;
    }

    public TransferPOJO build() {
        TransferPOJO transferPOJO = new TransferPOJO();
        transferPOJO.setId(id);
        transferPOJO.setFromAccount(fromAccount);
        transferPOJO.setToAccount(toAccount);
        transferPOJO.setAmount(amount);
        transferPOJO.setDate(date);
        transferPOJO.setReason(reason);
        return transferPOJO;
    }
}
