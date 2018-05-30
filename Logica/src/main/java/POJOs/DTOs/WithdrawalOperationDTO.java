package POJOs.DTOs;

public class WithdrawalOperationDTO {
    private int fromAccount;
    private double amountOfTransfer;
    private String reasonOfWithdrawal;

    public int getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(int fromAccount) {
        this.fromAccount = fromAccount;
    }

    public double getAmountOfTransfer() {
        return amountOfTransfer;
    }

    public void setAmountOfTransfer(double amountOfTransfer) {
        this.amountOfTransfer = amountOfTransfer;
    }

    public String getReasonOfWithdrawal() {
        return reasonOfWithdrawal;
    }

    public void setReasonOfWithdrawal(String reasonOfWithdrawal) {
        this.reasonOfWithdrawal = reasonOfWithdrawal;
    }
}
