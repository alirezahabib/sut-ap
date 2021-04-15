package homework.hw2.q1;

import java.util.HashMap;

public class Transaction {
    private static final HashMap<Integer, Transaction> transactions = new HashMap<>();
    private static int idCounter = 1;
    private final int id;
    private final int customerId;
    private final int amount;
    private final int discountCode;
    private final int discountPrice;
    private int finalPayment;
    private boolean isAccepted;

    public Transaction(int customerId, int amount, int discountCode) {
        this.customerId = customerId;
        this.amount = amount;
        this.discountCode = discountCode;
        this.discountPrice = Confectionary.getPriceByCode(discountCode);
        this.id = idCounter++;
        transactions.put(this.id, this);
    }

    public static Transaction getTransactionById(int id) {
        return transactions.get(id);
    }

    public static boolean exists(int id) {
        return transactions.containsKey(id);
    }

    public static HashMap<Integer, Transaction> getTransactions() {
        return transactions;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public int getId() {
        return id;
    }

    public int getDiscountCode() {
        return discountCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getAmount() {
        return amount;
    }

    public int getFinalPayment() {
        return finalPayment;
    }

    public boolean isTransactionAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        if (!accepted) return;

        finalPayment = (amount > discountPrice) ? (amount - discountPrice) : 0;
        Customer.getCustomerById(customerId).decreaseBalance(finalPayment);
        isAccepted = true;
    }

    public String toString() {
        return String.format("transaction %d: %d %d %d %d", id, customerId, amount, discountCode, finalPayment);
    }
}
