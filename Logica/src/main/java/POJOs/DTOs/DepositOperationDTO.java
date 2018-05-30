package POJOs.DTOs;

public class DepositOperationDTO {
    private int toAccount;
    private double amountOfTransfer;
    private String reasonOfDeposit;

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

    public String getReasonOfDeposit() {
        return reasonOfDeposit;
    }

    public void setReasonOfDeposit(String reasonOfDeposit) {
        this.reasonOfDeposit = reasonOfDeposit;
    }
}
