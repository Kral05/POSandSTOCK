package util;

import javax.swing.*;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PasswordUtil {
    
    private static boolean isPasswordVisible = false;
    
    public static void addPasswordMasking(JTextField passwordField, StringBuilder actualPassword) {
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLetterOrDigit(keyChar) || Character.isWhitespace(keyChar) || !Character.isISOControl(keyChar)) {
                    actualPassword.append(keyChar);
                    updatePasswordDisplay(passwordField, actualPassword.toString(), isPasswordVisible);
                }
                e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && actualPassword.length() > 0) {
                    actualPassword.setLength(actualPassword.length() - 1);
                    updatePasswordDisplay(passwordField, actualPassword.toString(), isPasswordVisible);
                }
            }
        });
    }

    public static void updatePasswordDisplay(JTextField textField, String actualPassword, boolean showPassword) {
        if (showPassword) {
            textField.setText(actualPassword);
        } else {
            StringBuilder displayText = new StringBuilder();
            for (int i = 0; i < actualPassword.length(); i++) {
                if (i == actualPassword.length() - 1) {
                    displayText.append(actualPassword.charAt(i));
                } else {
                    displayText.append('*');
                }
            }
            textField.setText(displayText.toString());
        }
    }

    public static void togglePasswordVisibility(JTextField passwordField, StringBuilder actualPassword, JButton toggleButton) {
        isPasswordVisible = !isPasswordVisible;
        updatePasswordDisplay(passwordField, actualPassword.toString(), isPasswordVisible);
        
        // 獲取並調整圖標大小
        String iconPath = isPasswordVisible ? "/eye-open.png" : "/eye-closed.png";
        ImageIcon originalIcon = new ImageIcon(PasswordUtil.class.getResource(iconPath));
        Image image = originalIcon.getImage();
        Image newimg = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(newimg);
        
        toggleButton.setIcon(scaledIcon);
    }
}