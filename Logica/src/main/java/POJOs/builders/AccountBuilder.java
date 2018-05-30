package POJOs.builders;

import POJOs.AccountPOJO;

public final class AccountBuilder {
    private int accountNumber;
    private String holder;
    private double balance;

    private AccountBuilder() {
    }

    public static AccountBuilder anAccountPOJO() {
        return new AccountBuilder();
    }

    public AccountBuilder withAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public AccountBuilder withHolder(String holder) {
        this.holder = holder;
        return this;
    }

    public AccountBuilder withBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public AccountPOJO build() {
        AccountPOJO accountPOJO = new AccountPOJO();
        accountPOJO.setAccountNumber(accountNumber);
        accountPOJO.setHolder(holder);
        accountPOJO.setBalance(balance);
        return accountPOJO;
    }
}
