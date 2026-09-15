package lab1;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private static final int DEFAULT_COUNTDOWN_SECONDS = 10;
    private static final int DEFAULT_ALARM_SECONDS = 5;

    private final JLabel stopwatchLabel = new JLabel("00:00:00", SwingConstants.CENTER);
    private final JLabel countdownLabel = new JLabel("00:00:10", SwingConstants.CENTER);
    private final JLabel alarmLabel = new JLabel("00:00:05", SwingConstants.CENTER);

    private final StopwatchTimer stopwatchTimer = new StopwatchTimer();
    private final CountdownTimer countdownTimer = new CountdownTimer();
    private final AlarmTimer alarmTimer = new AlarmTimer();

    public Main() {
        super("Swing Timer Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 10, 10));
        setSize(380, 420);
        setLocationRelativeTo(null);

        Font bigFont = new Font("SansSerif", Font.BOLD, 28);
        stopwatchLabel.setFont(bigFont);
        countdownLabel.setFont(bigFont);
        alarmLabel.setFont(bigFont);

        JPanel stopwatchPanel = new JPanel(new BorderLayout(10, 10));
        stopwatchPanel.setBorder(BorderFactory.createTitledBorder("Секундомер"));
        stopwatchPanel.add(stopwatchLabel, BorderLayout.CENTER);

        JPanel stopwatchButtons = new JPanel(new FlowLayout());
        JButton startStopwatchButton = new JButton("Старт");
        JButton pauseStopwatchButton = new JButton("Пауза");
        JButton resetStopwatchButton = new JButton("Сброс");
        stopwatchButtons.add(startStopwatchButton);
        stopwatchButtons.add(pauseStopwatchButton);
        stopwatchButtons.add(resetStopwatchButton);
        stopwatchPanel.add(stopwatchButtons, BorderLayout.SOUTH);

        JPanel countdownPanel = new JPanel(new BorderLayout(10, 10));
        countdownPanel.setBorder(BorderFactory.createTitledBorder("Таймер с ограничением"));
        countdownPanel.add(countdownLabel, BorderLayout.CENTER);

        JPanel countdownButtons = new JPanel(new FlowLayout());
        JButton startCountdownButton = new JButton("Старт");
        JButton pauseCountdownButton = new JButton("Пауза");
        JButton resetCountdownButton = new JButton("Сброс");
        countdownButtons.add(startCountdownButton);
        countdownButtons.add(pauseCountdownButton);
        countdownButtons.add(resetCountdownButton);
        countdownPanel.add(countdownButtons, BorderLayout.SOUTH);

        JPanel alarmPanel = new JPanel(new BorderLayout(10, 10));
        alarmPanel.setBorder(BorderFactory.createTitledBorder("Будильник"));
        alarmPanel.add(alarmLabel, BorderLayout.CENTER);

        JPanel alarmSettings = new JPanel(new FlowLayout());
        JLabel alarmLabelText = new JLabel("Секунды: ");
        JSpinner alarmSpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_ALARM_SECONDS, 1, 600, 1));
        JButton startAlarmButton = new JButton("Установить");
        JButton cancelAlarmButton = new JButton("Отменить");
        alarmSettings.add(alarmLabelText);
        alarmSettings.add(alarmSpinner);
        alarmSettings.add(startAlarmButton);
        alarmSettings.add(cancelAlarmButton);
        alarmPanel.add(alarmSettings, BorderLayout.SOUTH);

        add(stopwatchPanel);
        add(countdownPanel);
        add(alarmPanel);

        startStopwatchButton.addActionListener(e -> startStopwatch());
        pauseStopwatchButton.addActionListener(e -> stopStopwatch());
        resetStopwatchButton.addActionListener(e -> resetStopwatch());

        startCountdownButton.addActionListener(e -> startCountdown());
        pauseCountdownButton.addActionListener(e -> stopCountdown());
        resetCountdownButton.addActionListener(e -> resetCountdown());

        startAlarmButton.addActionListener(e -> {
            int seconds = (int) alarmSpinner.getValue();
            startAlarm(seconds);
        });
        cancelAlarmButton.addActionListener(e -> stopAlarm());

        repaintLabels();
    }

    private void startStopwatch() {
        stopwatchTimer.start(() -> SwingUtilities.invokeLater(() ->
                stopwatchLabel.setText(stopwatchTimer.formatTime(stopwatchTimer.getElapsedSeconds()))));
    }

    private void stopStopwatch() {
        stopwatchTimer.stop();
    }

    private void resetStopwatch() {
        stopwatchTimer.reset();
        repaintLabels();
    }

    private void startCountdown() {
        countdownTimer.start(DEFAULT_COUNTDOWN_SECONDS,
                () -> SwingUtilities.invokeLater(() ->
                        countdownLabel.setText(countdownTimer.formatTime(countdownTimer.getRemainingSeconds()))),
                () -> SwingUtilities.invokeLater(() -> {
                    countdownLabel.setText("00:00:00");
                    JOptionPane.showMessageDialog(this, "Время закончилось!");
                }));
    }

    private void stopCountdown() {
        countdownTimer.stop();
    }

    private void resetCountdown() {
        countdownTimer.reset(DEFAULT_COUNTDOWN_SECONDS);
        repaintLabels();
    }

    private void startAlarm(int seconds) {
        alarmLabel.setText(formatTime(seconds));
        alarmTimer.schedule(seconds, () -> SwingUtilities.invokeLater(() -> {
            alarmLabel.setText("00:00:00");
            JOptionPane.showMessageDialog(this, "Будильник сработал!", "Оповещение",
                    JOptionPane.INFORMATION_MESSAGE);
        }));
    }

    private void stopAlarm() {
        alarmTimer.stop();
        alarmLabel.setText("00:00:00");
    }

    private void repaintLabels() {
        stopwatchLabel.setText(stopwatchTimer.formatTime(stopwatchTimer.getElapsedSeconds()));
        countdownLabel.setText(countdownTimer.formatTime(DEFAULT_COUNTDOWN_SECONDS));
        alarmLabel.setText("00:00:05");
    }

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}