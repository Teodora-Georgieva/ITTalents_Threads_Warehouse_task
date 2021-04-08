package main;

import java.util.Random;

public class Client extends Thread{
    private Shop shop;

    public Client(Shop shop, String name){
        super(name);
        this.shop = shop;
    }

    @Override
    public void run() {
        while (true){
            int totalCountOfProducts = ProductName.values().length;
            Random r = new Random();
            ProductName product = ProductName.values()[r.nextInt(totalCountOfProducts)];
            this.shop.deliver(product);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}