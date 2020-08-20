package com.dom.util.graphs;


import javax.swing.*;
import java.awt.*;


public class GradientLabel extends JLabel {

    public GradientLabel(Color color) {
        super();
        this.setBackground(color);
    }

    public GradientLabel() {
        super();
    }


    protected void paintComponent(Graphics g) {
        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = getBackground();
        Color color2 = color1.darker().darker();
        int w = getWidth();
        int h = getHeight();

        // Paint a gradient from top to bottom
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        setOpaque(false);
        super.paintComponent(g);
        setOpaque(true);
    }

}
