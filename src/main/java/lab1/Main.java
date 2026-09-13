package lab1;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        System.out.println(String.format("Главный поток: '%s'.", Thread.currentThread().getName())); // "main"

        System.out.println("Запуск таймеров...");

        final int second = 1000;

        // 1. Реагировать через определённый промежуток времени.
        Timer delayedTimer = new Timer("DelayedTimer");
        delayedTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("[1] Сработал таймер с задержкой: прошло 3 секунды.");
                delayedTimer.cancel();
            }
        }, 3 * second);

        // 2. Реагировать в течение определённого времени.
        Timer limitedTimer = new Timer("LimitedTimer");
        final int[] limitedCounter = {0};
        limitedTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                limitedCounter[0]++;
                System.out.println("[2] Таймер в течение времени: тик " + limitedCounter[0]);

                if (limitedCounter[0] >= 5) {
                    System.out.println("[2] Время истекло. Таймер остановлен.");
                    limitedTimer.cancel();
                }
            }
        }, second / 2, second);

        // 3. Реагировать с указанным периодом.
        Timer periodicTimer = new Timer("PeriodicTimer");
        final int[] periodicCounter = {0};
        periodicTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                periodicCounter[0]++;
                System.out.println("[3] Таймер с периодом: запуск №" + periodicCounter[0]);

                if (periodicCounter[0] >= 5) {
                    System.out.println("[3] Периодический таймер завершён.");
                    periodicTimer.cancel();
                }
            }
        }, 0, 2 * second);

        try {
            Thread.sleep(13 * second);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Главный поток был прерван.");
        }

        System.out.println("Работа приложения завершена.");
    }
}
