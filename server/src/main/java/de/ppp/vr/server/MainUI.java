package de.ppp.vr.server;

import javax.swing.*;
import java.awt.*;

public class MainUI {

    public static final int WIDTH = 600, HEIGHT = 400;
    public static final int[][] screenBuffer = new int[WIDTH][HEIGHT];
    private static JPanel panel;

    public static void init() {
        JFrame frame = new JFrame("VR");
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;

                for (int x = 0; x < screenBuffer.length; x++) {
                    for (int y = 0; y < screenBuffer[y].length; y++) {
                        if(screenBuffer[x][y] != 0) g2.fillRect(x, y, 10, 10);
                    }
                }
            }
        };
        frame.add(panel);
    }

    public static void update() {
        panel.paintAll(panel.getGraphics());
    }

}
