package util;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClockUtils {
    public static Timer startClock(JLabel clockLabel) {
        Timer clockTimer = new Timer(1000, e -> updateClock(clockLabel));
        clockTimer.start();
        return clockTimer;
    }

    private static void updateClock(JLabel clockLabel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        clockLabel.setText(LocalDateTime.now().format(formatter));
    }
}
