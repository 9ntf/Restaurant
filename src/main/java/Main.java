import restaraunt.Restaurant;

public class Main {
    public static void main(String[] args) {

        final Restaurant restaurant = new Restaurant();

        new Thread(null, restaurant::processOrder, "Официант 1").start();

        new Thread(null, restaurant::newVisitor, "Посетитель 1").start();
        new Thread(null, restaurant::newVisitor, "Посетитель 2").start();
        new Thread(null, restaurant::newVisitor, "Посетитель 3").start();
        new Thread(null, restaurant::newVisitor, "Посетитель 4").start();


    }
}
