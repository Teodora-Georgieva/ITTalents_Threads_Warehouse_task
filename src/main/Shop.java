package main;

import java.util.*;

public class Shop extends Warehouse implements Runnable{
    public static Warehouse warehouse;

    public Shop(String name) {
        super(name);
    }

    @Override
    int initialQuantity() {
        return 0;
    }

    @Override
    int minimumQuantity() {
        return 5;
    }

    @Override
    int supplyQuantity() {
        return 5;
    }

    @Override
    int deliverQuantity() {
        Random r = new Random();
        return r.nextInt(4) + 1;
    }

    @Override
    public void run() {
        while(true){
            this.supply();
        }
    }

    @Override
    void supplyDeficits() {
        List<ProductName> deficitProducts = getDeficitProducts();
        for (ProductName deficitProduct : deficitProducts) {
            warehouse.deliver(deficitProduct);
        }

        super.supplyDeficits();
    }

    private List<ProductName> getDeficitProducts() {
        List<ProductName> deficitProducts = new ArrayList<>();
        for(HashMap<ProductName, Integer> hashMap : this.products.values()){
            for(Map.Entry<ProductName, Integer> entry : hashMap.entrySet()){
                if(entry.getValue() < this.minimumQuantity()){
                    deficitProducts.add(entry.getKey());
                }
            }
        }

        return deficitProducts;
    }
}