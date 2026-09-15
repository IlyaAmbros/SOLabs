package lab1;

import java.util.Timer;
import java.util.TimerTask;

public class StopwatchTimer {
    private static final int SECOND = 1000;

    private Timer timer;
    private int elapsedSeconds;

    public void start(Runnable onTick) {
        if (timer != null) {
            return;
        }

        timer = new Timer("StopwatchTimer");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedSeconds++;
                if (onTick != null) {
                    onTick.run();
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

    public void reset() {
        stop();
        elapsedSeconds = 0;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    public String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
