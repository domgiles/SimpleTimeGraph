package com.dom.util.graphs;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class YAxis extends JPanel {

    private static final Logger logger = Logger.getLogger(YAxis.class.getName());
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final int TICK_WIDTH = 4;
    private static final int TICK_WIDTH_BORDER = 2;
    private static final double V_TICKBUFFER = 25d;
    private static final double YOFFSET = 0;
    protected int horizontalSpacer = 0;
    protected double verticalBuffer = 0D;
    protected double xAxisHeight=0D;
    protected double scaler = 1.0d;
    protected Chart chart;
    protected List<Integer> tickLocations = null;
    private double maxValue = 1;
    private Color tickColor = this.getForeground();
    private int maxTextWidth = 0;
    private boolean dynamicWidth = false;
    private Font renderingFont = DEFAULT_FONT;


    public YAxis(Chart chart) {
        this.chart = chart;
        setFont(DEFAULT_FONT);
    }

    @Override
    public void setFont(Font font) {
        renderingFont = font;
//        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        tickLocations = new ArrayList<>();

        super.paint(g);
        if (chart != null) {
            maxValue = (chart.getMaxYVal() / scaler == 0) ? 1 : chart.getMaxYVal() / scaler;
            if (maxValue != 0) {
                setPreferredSize(new Dimension(getYAxisDecorationsWidth() + horizontalSpacer, 10));
            }
        }

        try {
            g.setFont(renderingFont);
            int fontHeight = renderingFont.getSize();
            FontRenderContext frc = g2.getFontRenderContext();
            LineMetrics metrics = g2.getFont().getLineMetrics(Integer.toString(fontHeight), frc);
            double ascent = metrics.getAscent();


            verticalBuffer =  g2.getFont().getStringBounds("0", frc).getHeight();
//            double height = (drawingSouthVerticalBuffer) ? (getHeight() - verticalBuffer) : (getHeight() - (verticalBuffer * 2)); // We only have a southern buffer
            double realHeight = getHeight() - verticalBuffer - xAxisHeight; // We only have a southern buffer
            double width = getWidth();

            double maxPossTicks = Math.floor(realHeight / (ascent + V_TICKBUFFER));

            maxPossTicks = Math.min(maxPossTicks, maxValue);
            double numberOfTicks = maxPossTicks;

            double tickHeight = ((realHeight) / (numberOfTicks)); // Height - 3D takes the chart edges into consideration

            double drawValueStep = maxValue / numberOfTicks;
            double drawValue = 0;

            Rectangle2D bounds;
            int textWidth;

            g.setColor(tickColor);
            double tickLoc = realHeight + (ascent / 2D); // Adjust Height -1 places the tick on the chart lower axis rather than just below it and
            g.drawLine((int) width - TICK_WIDTH, (int) tickLoc, (int) width, (int) tickLoc);
            maxTextWidth = 0;
            for (int i = 0; i <= numberOfTicks; i++) {
                double drawAt = tickLoc - tickHeight;
                g.setColor(tickColor);
                g2.drawLine((int) width - TICK_WIDTH, (int) (drawAt - YOFFSET), (int) width, (int) (drawAt - YOFFSET));
                tickLocations.add((int) drawAt);
                String text = Long.toString(Math.round(drawValue));
                bounds = g2.getFont().getStringBounds(text, frc);
                textWidth = (int) (bounds.getWidth()); // The width of our text + a buffer
                maxTextWidth = Math.max(maxTextWidth, textWidth);
                g.setColor(getForeground());
                g.drawString(text, (int) width - (TICK_WIDTH + textWidth + TICK_WIDTH_BORDER), (int) ((drawAt + (tickHeight + (ascent) / 2D)) - YOFFSET));
                tickLoc -= (tickHeight);
                drawValue = drawValue + drawValueStep;
            }
            chart.getPlotArea().setVborder((int) (verticalBuffer / 2D) -3);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected Exception", e);
        }
    }

    public int getNorthernBuffer() {
        FontMetrics fm = this.getFontMetrics(renderingFont);
        return (fm.getAscent() - 1);
    }


    public void setTickColor(Color tc) {
        tickColor = tc;
    }

    public int getMaxValue() {
        return (int) maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isDynamicWidth() {
        return dynamicWidth;
    }

    public void setDynamicWidth(boolean dynamicWidth) {
        this.dynamicWidth = dynamicWidth;
    }

    public void setYAxisWidth(int hborder) {
        if (hborder > (maxTextWidth + TICK_WIDTH + TICK_WIDTH_BORDER)) {
            horizontalSpacer = hborder - (maxTextWidth + TICK_WIDTH + TICK_WIDTH_BORDER);
        } else {
            horizontalSpacer = 0;
        }
    }

    public int getYAxisDecorationsWidth() { // Size of text and ticks
        return maxTextWidth + TICK_WIDTH + TICK_WIDTH_BORDER;
    }

    public int getYAxisHorizontalSpacer() {
        return horizontalSpacer;
    }

    public void setXAxisHeight(int vborder) {
        this.xAxisHeight = vborder;
    }

    public double getVerticalBuffer() {
        return verticalBuffer;
    }


    public List<Integer> getTickLocations() {
        return tickLocations;
    }

    public double getScaler() {
        return scaler;
    }

    public void setScaler(double scaler) {
        this.scaler = scaler;
    }
}
