package lab1;

import java.util.Timer;
import java.util.TimerTask;

/*
    Таймер выполняющийся c повторяющимися промежутками.
 */
public class CountdownTimer {
    private static final int SECOND = 1000;

    private Timer timer;
    private int remainingSeconds;

    public void start(int seconds, Runnable onTick, Runnable onFinish) {
        stop();
        remainingSeconds = seconds;

        timer = new Timer("CountdownTimer");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingSeconds <= 0) {
                    stop();
                    if (onFinish != null) {
                        onFinish.run();
                    }
                    return;
                }

                remainingSeconds--;
                if (onTick != null) {
                    onTick.run();
                }

                if (remainingSeconds <= 0) {
                    stop();
                    if (onFinish != null) {
                        onFinish.run();
                    }
                }
            }
        }, SECOND, SECOND);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void reset(int defaultSeconds) {
        stop();
        remainingSeconds = defaultSeconds;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
