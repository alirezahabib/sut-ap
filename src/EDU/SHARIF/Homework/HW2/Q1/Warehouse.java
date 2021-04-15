package EDU.SHARIF.Homework.HW2.Q1;

import java.util.HashMap;

public class Warehouse {
    private static final HashMap<String, Warehouse> warehouses = new HashMap<>();
    private int amount;
    private String materialName;

    public Warehouse(String materialName, int amount) {
        this.materialName = materialName;
        this.amount = amount;
        warehouses.put(materialName, this);
    }

    public static boolean exists(String materialName) {
        return warehouses.containsKey(materialName);
    }

    public static Warehouse getWarehouseByName(String name) {
        return warehouses.get(name);
    }

    public void increaseMaterial(int amount) {
        this.amount += amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void decreaseMaterial(int amount) {
        this.amount -= amount;
    }
}
