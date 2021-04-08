package main;

public class Supplier extends Thread{
    private Warehouse warehouse;

    public Supplier(Warehouse warehouse) {
        super("Supplier");
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        while (true){
            this.warehouse.supply();
        }
    }
}