package POJOs.DTOs;

public class TransferOperationDTO {

    private int fromAccount;
    private int toAccount;
    private double amountOfTransfer;
    private String reasonOfTransfer;

    public int getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(int fromAccount) {
        this.fromAccount = fromAccount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }

    public double getAmountOfTransfer() {
        return amountOfTransfer;
    }

    public void setAmountOfTransfer(double amountOfTransfer) {
        this.amountOfTransfer = amountOfTransfer;
    }

    public String getReasonOfTransfer() {
        return reasonOfTransfer;
    }

    public void setReasonOfTransfer(String reasonOfTransfer) {
        this.reasonOfTransfer = reasonOfTransfer;
    }
}
