package main;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    HashMap<ProductType, HashMap<ProductName, Integer>> products;
    private String name;

    public Warehouse(String name) {
        this.name = name;
        this.products = new HashMap<>();
        this.products.put(ProductType.FRUITS, new HashMap<>());
        this.products.get(ProductType.FRUITS).put(ProductName.BANANA, this.initialQuantity());
        this.products.get(ProductType.FRUITS).put(ProductName.APPLE, this.initialQuantity());
        this.products.get(ProductType.FRUITS).put(ProductName.ORANGE, this.initialQuantity());

        this.products.put(ProductType.VEGETABLES, new HashMap<>());
        this.products.get(ProductType.VEGETABLES).put(ProductName.CUCUMBER, this.initialQuantity());
        this.products.get(ProductType.VEGETABLES).put(ProductName.EGGPLANT, this.initialQuantity());
        this.products.get(ProductType.VEGETABLES).put(ProductName.POTATO, this.initialQuantity());

        this.products.put(ProductType.MEATS, new HashMap<>());
        this.products.get(ProductType.MEATS).put(ProductName.PORK, this.initialQuantity());
        this.products.get(ProductType.MEATS).put(ProductName.BEEF, this.initialQuantity());
        this.products.get(ProductType.MEATS).put(ProductName.CHICKEN, this.initialQuantity());
    }

    int initialQuantity() {
        return 15;
    }

    int minimumQuantity(){
        return 8;
    }

    int supplyQuantity(){
        return 25;
    }

    int deliverQuantity(){
        return 5;
    }

    public synchronized void supply(){ //sipi
        while (!hasDeficitProducts()) {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting because there are no deficit products in " +
                                                                          this.name);
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + " is supplying " + this.name);
        this.supplyDeficits();
        notifyAll();
    }

    public synchronized void deliver(ProductName productName){ //zemi
        while(isDeficit(productName)){
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting because " + productName + " is deficit" +
                                   " in " + this.name);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int quantity = deliverProduct(productName);
        System.out.println(Thread.currentThread().getName() + " is getting " + quantity + " " + productName +
                            " from " + this.name);
        notifyAll();
    }

    private boolean isDeficit(ProductName productName) {
        for(HashMap<ProductName, Integer> hashMap : this.products.values()) {
            for (Map.Entry<ProductName, Integer> entry : hashMap.entrySet()) {
                if (entry.getKey() == productName && entry.getValue() < this.minimumQuantity()) {
                    return true;
                }
            }
        }
        return false;
    }

    private int deliverProduct(ProductName productName) {
        int countOfDeliveredProducts = this.deliverQuantity();
        for(HashMap<ProductName, Integer> hashMap : this.products.values()){
            for(Map.Entry<ProductName, Integer> entry : hashMap.entrySet()){
                if(entry.getKey() == productName){
                    int previousQuantity = entry.getValue();
                    hashMap.put(entry.getKey(), previousQuantity - countOfDeliveredProducts);
                }
            }
        }

        return countOfDeliveredProducts;
    }

    private boolean hasDeficitProducts() {
        for(HashMap<ProductName, Integer> hashMap : this.products.values()){
            for(int value : hashMap.values()){
                if(value < this.minimumQuantity()){
                    return true;
                }
            }
        }

        return false;
    }

    void supplyDeficits() {
        for(HashMap<ProductName, Integer> hashMap : this.products.values()){
            for(Map.Entry<ProductName, Integer> entry : hashMap.entrySet()){
                if(entry.getValue() < this.minimumQuantity()){
                    int previousValue = hashMap.get(entry.getKey());
                    hashMap.put(entry.getKey(), previousValue + this.supplyQuantity());
                }
            }
        }
    }

    public void printAvailability() {
        System.out.println( "-------------------" + this.name + "-------------------");
        for(HashMap<ProductName, Integer> p : products.values()){
            for(Map.Entry<ProductName, Integer> e : p.entrySet()){
                System.out.println(e.getKey() + " - " + e.getValue());
            }
        }
        System.out.println("---------------------------------------------------------");
    }
}