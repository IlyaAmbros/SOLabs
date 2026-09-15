package lab1;

import java.util.Timer;
import java.util.TimerTask;
/*
    Таймер выполняющийся за фиксированное время. Подождать, выполнить, остановиться
 */
public class AlarmTimer {
    private static final int SECOND = 1000;

    private Timer timer;

    public void schedule(int seconds, Runnable onTrigger) {
        stop();

        timer = new Timer("AlarmTimer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stop();
                if (onTrigger != null) {
                    onTrigger.run();
                }
            }
        }, seconds * SECOND);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
