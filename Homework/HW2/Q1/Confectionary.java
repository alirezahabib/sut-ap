package SUT.AP.HW2.Q1;

import java.util.HashMap;

public class Confectionary {
    private int balance;
    private static final HashMap<Integer, Integer> discounts = new HashMap<>();

    public Confectionary() {
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void increaseBalance(int balance) {
        this.balance += balance;
    }

    public static boolean isDiscountValid(int code){
        return discounts.containsKey(code);
    }

    public static void addDiscount(int code, int price){
        if (discounts.containsKey(code)) {
            System.out.println("discount with this code already exists");
            return;
        }
        discounts.put(code, price);
    }

    public static int getPriceByCode(int code) {
        if (code == -1) return 0;
        return discounts.get(code);
    }
}
