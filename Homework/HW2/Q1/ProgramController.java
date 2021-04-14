package SUT.AP.HW2.Q1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgramController {
    private static final Pattern createConfectionaryPattern = Pattern.compile("^create confectionary$");
    private static final Pattern addCustomerPattern = Pattern.compile("^add customer id " +
            "([0-9]+) name ([a-zA-Z ]+)$");
    private static final Pattern increaseBalancePattern = Pattern.compile("^increase balance " +
            "customer ([0-9]+) amount ([0-9]+)$");
    private static final Pattern addWarehousePattern = Pattern.compile("^add warehouse " +
            "material ([a-zA-Z ]+) amount ([0-9]+)$");
    private static final Pattern increaseMaterialPattern = Pattern.compile("^increase warehouse " +
            "material ([a-zA-Z ]+) amount ([0-9]+)$");
    private static final Pattern addSweetPattern = Pattern.compile("^add sweet name " +
            "([a-zA-Z ]+) price ([0-9]+) materials:((?: [a-zA-Z ]+ [0-9]+(?:,|$))+)");
    private static final Pattern addSweetMaterialsPattern = Pattern.compile(" ([a-zA-Z ]+) ([0-9]+)(?:,|$)");
    private static final Pattern increaseSweetPattern = Pattern.compile("^increase sweet " +
            "([a-zA-Z ]+) amount ([0-9]+)+$");
    private static final Pattern addDiscountPattern = Pattern.compile("^add discount code " +
            "([0-9]+) price ([0-9]+)$");
    private static final Pattern addDiscountToCustomerPattern = Pattern.compile("^add discount code " +
            "code ([0-9]+) to customer id ([0-9]+)$");
    private static final Pattern sellSweetPattern = Pattern.compile("^sell sweet " +
            "([a-zA-Z ]+) amount ([0-9]+) to customer ([0-9]+)$");
    private static final Pattern acceptTransactionPattern = Pattern.compile("^accept transaction " +
            "([0-9]+)$");
    private static final Pattern printTransactionsPattern = Pattern.compile("^print transactions list$");
    private static final Pattern printIncomePattern = Pattern.compile("^print income$");
    private static final Pattern endPattern = Pattern.compile("^end$");
    public static Confectionary confectionary;

    public void run() {
        Scanner input = new Scanner(System.in);
        String command;
        Matcher matcher;
        boolean end = false;

        while (!end) {
            command = input.nextLine().trim();

            if (endPattern.matcher(command).find()) end = true;
            else if (createConfectionaryPattern.matcher(command).find()) confectionary = new Confectionary();
            else if (confectionary == null) throwInvalidCommand();
            else if ((matcher = addCustomerPattern.matcher(command)).find()) addCustomer(matcher);
            else if ((matcher = increaseBalancePattern.matcher(command)).find()) chargeCustomerBalance(matcher);
            else if ((matcher = addWarehousePattern.matcher(command)).find()) addWarehouse(matcher);
            else if ((matcher = increaseMaterialPattern.matcher(command)).find()) increaseWarehouseMaterial(matcher);
            else if ((matcher = addSweetPattern.matcher(command)).find()) addSweet(matcher);
            else if ((matcher = increaseSweetPattern.matcher(command)).find()) increaseSweet(matcher);
            else if ((matcher = addDiscountPattern.matcher(command)).find()) addDiscount(matcher);
            else if ((matcher = addDiscountToCustomerPattern.matcher(command)).find()) addDiscountToCustomer(matcher);
            else if ((matcher = sellSweetPattern.matcher(command)).find()) sellSweet(matcher);
            else if ((matcher = acceptTransactionPattern.matcher(command)).find()) acceptTransaction(matcher);
            else if (printTransactionsPattern.matcher(command).find()) printTransactions();
            else if (printIncomePattern.matcher(command).find()) printIncome();
            else {
                throwInvalidCommand();
            }
        }
    }

    private void throwInvalidCommand() {
        System.out.println("invalid command");
    }

    private void throwCustomerNotFound() {
        System.out.println("customer not found");
    }

    private void throwSweetNotFound() {
        System.out.println("sweet not found");
    }

    private void addCustomer(Matcher matcher) {
        new Customer(matcher.group(2), Integer.parseInt(matcher.group(1)));
    }

    private void chargeCustomerBalance(Matcher matcher) {
        int id = Integer.parseInt(matcher.group(1));
        int amount = Integer.parseInt(matcher.group(2));
        if (!Customer.exists(id)) {
            throwCustomerNotFound();
            return;
        }
        Customer.getCustomerById(id).increaseBalance(amount);
    }

    private void increaseWarehouseMaterial(Matcher matcher) {
        String materialName = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));
        if (!Warehouse.exists(materialName)) {
            System.out.println("warehouse not found");
            return;
        }
        Warehouse.getWarehouseByName(materialName).increaseMaterial(amount);
    }

    private void addSweet(Matcher matcher) {
        String name = matcher.group(1);
        int price = Integer.parseInt(matcher.group(2));
        String materials = matcher.group(3);
        Matcher materialsMatcher = addSweetMaterialsPattern.matcher(materials);

        HashMap<String, Integer> materialsHashmap = new HashMap<>();
        ArrayList<String> notFounds = new ArrayList<>();

        while (materialsMatcher.find()) {
            String materialName = materialsMatcher.group(1);
            int amount = Integer.parseInt(materialsMatcher.group(2));
            materialsHashmap.put(materialName, amount);

            if (!Warehouse.exists(materialName)) {
                notFounds.add(materialName);
            }
        }

        if (!notFounds.isEmpty()) {
            System.out.print("not found warehouse(s):");
            for (String notFound : notFounds) {
                System.out.print(" " + notFound);
            }
            System.out.print("\n");
            return;
        }
        new Sweet(name, price, materialsHashmap);
    }

    private void increaseSweet(Matcher matcher) {
        String name = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));

        if (!Sweet.exists(name)) {
            throwSweetNotFound();
            return;
        }
        Sweet.getSweetByName(name).increaseSweet(amount);
    }

    private void addDiscount(Matcher matcher) {
        int code = Integer.parseInt(matcher.group(1));
        int price = Integer.parseInt(matcher.group(2));
        Confectionary.addDiscount(code, price);
    }

    private void addDiscountToCustomer(Matcher matcher) {
        int code = Integer.parseInt(matcher.group(1));
        int customerId = Integer.parseInt(matcher.group(2));

        if (!Confectionary.isDiscountValid(code)) {
            System.out.println("discount code not found");
            return;
        }

        if (!Customer.exists(customerId)) {
            throwCustomerNotFound();
            return;
        }
        Customer.getCustomerById(customerId).setDiscountCode(code);
    }

    private void sellSweet(Matcher matcher) {
        String name = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));
        int customerId = Integer.parseInt(matcher.group(3));

        if (!Sweet.exists(name)) {
            throwSweetNotFound();
            return;
        }
        Sweet sweet = Sweet.getSweetByName(name);

        if (sweet.getAmount() < amount) {
            System.out.println("insufficient sweet");
            return;
        }
        if (!Customer.exists(customerId)) {
            throwCustomerNotFound();
            return;
        }
        Customer customer = Customer.getCustomerById(customerId);

        int discountCode = customer.getDiscountCode();
        int price = sweet.getPrice() * amount - Confectionary.getPriceByCode(discountCode);

        if (customer.getBalance() < price) {
            System.out.println("customer has not enough money");
            return;
        }

        sweet.setAmount(sweet.getAmount() - amount);
        new Transaction(customerId, sweet.getPrice() * amount, discountCode);
        customer.setDiscountCode(-1);
        System.out.println("transaction " + (Transaction.getIdCounter() - 1) + " successfully created");
    }

    private void acceptTransaction(Matcher matcher) {
        int id = Integer.parseInt(matcher.group(1));
        if (!Transaction.exists(id)) {
            System.out.println("no waiting transaction with this id was found");
            return;
        }

        Transaction.getTransactionById(id).setAccepted(true);
        confectionary.increaseBalance(Transaction.getTransactionById(id).getFinalPayment());
    }

    private void printTransactions() {
        HashMap<Integer, Transaction> transactions = Transaction.getTransactions();
        int theLastId = Transaction.getIdCounter() - 1;

        for (int id = 1; id <= theLastId; id++) {
            Transaction transaction = transactions.get(id);
            if (transaction.isTransactionAccepted()) {
                System.out.println(transaction);
            }
        }
    }

    private void printIncome() {
        System.out.println(confectionary.getBalance());
    }

    private void addWarehouse(Matcher matcher) {
        String materialName = matcher.group(1);
        int amount = Integer.parseInt(matcher.group(2));

        if (Warehouse.exists(materialName)) {
            System.out.println("warehouse having this material already exists");
            return;
        }
        new Warehouse(materialName, amount);
    }
}
