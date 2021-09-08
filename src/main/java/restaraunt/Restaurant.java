package restaraunt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private static final int ORDER_TIME = 3000;
    private static final int COOKING_TIME = 10000;
    private static final int EATING_TIME = 6000;

    private List<Order> orders = new ArrayList<>();
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //новый посетитель заказывает
    public void newVisitor() {
        try {
            lock.lock();
            System.out.printf("%s зашел в ресторан и делает заказ\n", Thread.currentThread().getName());
            Thread.sleep(ORDER_TIME);
            orders.add(new Order());
            condition.signal();
            condition.await();
            System.out.printf("%s получил заказ и сидит кушает\n", Thread.currentThread().getName());
            Thread.sleep(EATING_TIME);
            System.out.printf("После приема пищи %s уходит\n", Thread.currentThread().getName());
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        } finally {
            lock.unlock();
        }
    }

    //официант обрабатывает заказ
    public void processOrder() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                condition.await();
                System.out.printf("%s получил заказ и отнес его на кухню\n", Thread.currentThread().getName());
                orders.remove(0);
                Thread.sleep(COOKING_TIME);
                System.out.printf("%s относит готовый заказ посетителю\n", Thread.currentThread().getName());
                condition.signal();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
