package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarqueePanel extends JPanel {
    private JLabel label;
    private Timer timer;
    private final int width;
    private final int height;

    public MarqueePanel(String text, int width, int height) {
        this.width = width;
        this.height = height;
        
        setLayout(null);
        setBackground(new Color(247, 245, 238));
        
        // 重寫paintComponent以設定剪裁區域
        JPanel clipPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setClip(0, 0, width, height);
            }
        };
        clipPanel.setBackground(new Color(247, 245, 238));
        clipPanel.setBounds(0, 0, width, height);
        clipPanel.setLayout(null);
        add(clipPanel);

        label = new JLabel(text);
        label.setBounds(0, 0, 1600, height);
        label.setBackground(new Color(247, 245, 238));
        label.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        clipPanel.add(label);
        
        setupTimer();
    }

    private void setupTimer() {
        final int startX = width;  // 從容器右側開始
        final int[] position = {startX};  // 初始位置
        
        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                position[0] -= 2;  // 每次移動 2 個像素
                
                // 當文字完全移出視窗時，重置位置
                if (position[0] < -label.getPreferredSize().width) {
                    position[0] = startX;  // 重置到起始位置
                }
                
                label.setLocation(position[0], 0);
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}