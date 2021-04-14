package SUT.AP.HW2.Q1;

import java.util.ArrayList;
import java.util.HashMap;

public class Sweet {
    private static final HashMap<String, Sweet> sweets = new HashMap<>();
    private final String name;
    private final int price;
    private final HashMap<String, Integer> materials;
    private int amount = 0;

    public Sweet(String name, int price, HashMap<String, Integer> materials) {
        this.name = name;
        this.price = price;
        this.materials = materials;
        sweets.put(name, this);
    }

    public static boolean exists(String name) {
        return sweets.containsKey(name);
    }

    public static Sweet getSweetByName(String name) {
        return sweets.get(name);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public HashMap<String, Integer> getMaterials() {
        return materials;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void increaseSweet(int amount) {
        ArrayList<String> insufficientMaterials = new ArrayList<>();

        for (String material : materials.keySet()) {
            if (Warehouse.getWarehouseByName(material).getAmount() < materials.get(material) * amount) {
                insufficientMaterials.add(material);
            }
        }

        if (!insufficientMaterials.isEmpty()) {
            System.out.print("insufficient material(s):");
            for (String material : insufficientMaterials) {
                System.out.print(" " + material);
            }
            System.out.print("\n");
            return;
        }
        decreaseMaterialsOfSweet(amount);
        this.amount += amount;
    }

    public void decreaseMaterialsOfSweet(int amount) {
        for (String material : materials.keySet()) {
            Warehouse.getWarehouseByName(material).decreaseMaterial(materials.get(material) * amount);
        }
    }
}
