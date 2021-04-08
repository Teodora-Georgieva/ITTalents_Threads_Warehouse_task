package main;

public class Demo {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse("Warehouse");
        Shop.warehouse = warehouse;

        Supplier supplier = new Supplier(warehouse);
        supplier.start();

        Shop shop1 = new Shop("Shop1");
        Shop shop2 = new Shop("Shop2");
        Shop shop3 = new Shop("Shop3");

        new Thread(shop1, "ThreadShop1").start();
        new Thread(shop2, "ThreadShop2").start();
        new Thread(shop3, "ThreadShop3").start();

        Client client1 = new Client(shop1, "Client1");
        Client client2 = new Client(shop1, "Client2");
        Client client3 = new Client(shop1, "Client3");

        Client client4 = new Client(shop2, "Client4");
        Client client5 = new Client(shop2, "Client5");
        Client client6 = new Client(shop2, "Client6");

        Client client7 = new Client(shop3, "Client7");
        Client client8 = new Client(shop3, "Client8");
        Client client9 = new Client(shop3, "Client9");

        client1.start();
        client2.start();
        client3.start();
        client4.start();
        client5.start();
        client6.start();
        client7.start();
        client8.start();
        client9.start();

        Thread statistics = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    warehouse.printAvailability();
                    shop1.printAvailability();
                    shop2.printAvailability();
                    shop3.printAvailability();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        statistics.setDaemon(true);
        statistics.start();
    }
}