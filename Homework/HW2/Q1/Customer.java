package SUT.AP.HW2.Q1;

import java.util.HashMap;

public class Customer {
    private static final HashMap<Integer, Customer> customers = new HashMap<>();
    private String name;
    private int id;
    private int balance;
    private int discountCode = -1;

    public Customer(String name, int id) {
        if (Customer.customers.containsKey(id)) {
            System.out.println("customer with this id already exists");
            return;
        }
        this.name = name;
        this.id = id;
        customers.put(id, this);
    }

    public static Customer getCustomerById(int id) {
        return customers.get(id);
    }

    public static boolean exists(int id) {
        return Customer.customers.containsKey(id);
    }

    public void increaseBalance(int balance) {
        this.balance += balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(int discountCode) {
        this.discountCode = discountCode;
    }

    public void decreaseBalance(int balance) {
        this.balance -= balance;
    }
}
