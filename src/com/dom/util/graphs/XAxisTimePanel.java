package com.dom.util.graphs;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class XAxisTimePanel extends JPanel implements Runnable {

    private static final Logger logger = Logger.getLogger(XAxisTimePanel.class.getName());
    private Thread thread = null;
    private int numOfTicks = 120;
    private int buffer = 10;
    private int gap = 10;
    private int xAxisHeight = 0;
    private DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
    private LinkedList<TimeBox> timeBoxes = null;
    private FontRenderContext frc = null;
    private long refreshRate = 1000;
    private Color textColor = Color.darkGray;
    private Color tickColor = Color.darkGray;
    private boolean AntiAliased = true;
    private boolean drawXAxis = false;
    private Chart parent = null;


    public XAxisTimePanel(Chart parent, long refreshRate, int numOfTicks, Color textColor, Color tickColor, boolean drawXAxis) {
        super();
        this.refreshRate = refreshRate;
        this.numOfTicks = numOfTicks;
        this.textColor = textColor;
        this.tickColor = tickColor;
        this.drawXAxis = drawXAxis;
        this.parent = parent;
        thread = new Thread(this);
        thread.start();
    }


    public XAxisTimePanel(Chart parent) {
        thread = new Thread(this);
        thread.start();
        this.parent = parent;
    }

    public void run() {

        try {
            while (true) {
                if (timeBoxes != null) {
                    int ts = getWidth() / numOfTicks;
                    for (TimeBox tb : timeBoxes) {
                        tb.setXLoc(tb.getXLoc() - ts);
                    }
                    TimeBox lastBox = timeBoxes.getLast();
                    if ((lastBox.getXLoc() + lastBox.getWidth()) < 0) {
                        timeBoxes.removeLast();
                    }
                    TimeBox firstBox = timeBoxes.getFirst();
                    Rectangle2D bounds = getFont().getStringBounds(df.format(new Date()), frc);
                    parent.getYAxis().setXAxisHeight((int)bounds.getHeight());
                    if ((firstBox.getXLoc() + firstBox.width) < getWidth()) {
                        //logger.finest( "firstBox Pos = " + firstBox.getXLoc() + " width = " + firstBox.getWidth());
                        int w = (int)bounds.getWidth(); // rough width of time
                        double timePeriod = refreshRate * numOfTicks;
                        int maxNumberOfDates = (int) Math.round((double) getWidth() / (double) w);
                        double timeStep = timePeriod / (maxNumberOfDates - 1);
                        TimeBox newBox = new TimeBox(firstBox.getXLoc() + firstBox.width + buffer, w, df.format(new Date(System.currentTimeMillis() + (int) timeStep)));
                        timeBoxes.addFirst(newBox);
                    }

                }
                repaint();
                Thread.sleep(refreshRate);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception in run()", e);
        }
    }


    public void paint(Graphics g) {


        super.paint(g);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        try {
            if (timeBoxes == null) {
                timeBoxes = new LinkedList<>();
                Graphics2D g2d = (Graphics2D) getGraphics();
                frc = g2d.getFontRenderContext();
                logger.finest("Dimensions( " + getWidth() + ", " + getHeight() + ")");
                double timePeriod = refreshRate * numOfTicks;
                logger.finest("End Time for this chart should be " + df.format(new Date(System.currentTimeMillis() - (long) timePeriod)));

                String typicalTime = df.format(new Date());
                long startTime = System.currentTimeMillis();
                Rectangle2D bounds = getFont().getStringBounds(typicalTime, frc);

                xAxisHeight = ((int) bounds.getHeight()) + 2;

                int maxNumberOfDates = (int) Math.round(getWidth() / (bounds.getWidth()));

                buffer = ((int) ((getWidth() - ((maxNumberOfDates - 2) * bounds.getWidth())) / maxNumberOfDates - 2)) + gap;

                double timeStep = timePeriod / (maxNumberOfDates - 1);

                int pointer = getWidth();
                String time = df.format(new Date(startTime));
                int w = (int) getFont().getStringBounds(time, frc).getWidth();
                int x = pointer - (int) ((w / 2));
                TimeBox tb = new TimeBox(x, w, time);
                //logger.finest("TimeBox(" + x + ", " + w + ", " + time+ ")");
                timeBoxes.add(tb);
                pointer -= (w);

                for (int i = 1; i < maxNumberOfDates; i++) {
                    time = df.format(new Date(startTime - Math.round(timeStep * i)));
                    w = (int) getFont().getStringBounds(time, frc).getWidth();
                    x = pointer - (int) ((w / 2) + (buffer));
                    tb = new TimeBox(x, w, time);
                    //logger.finest("TimeBox(" + x + ", " + w + ", " + time+ ")");
                    timeBoxes.add(tb);
                    pointer -= (w + buffer);
                }
            }
            for (TimeBox t : timeBoxes) {
                if (isAntiAliased()) {
                    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                }
                g.setColor(textColor);
                g.drawString(t.getTime(), t.getXLoc(), (int) (getHeight() / 1.5));
                g.setColor(tickColor);
                g.drawLine(t.getXLoc() + (t.getWidth() / 2), 0, t.getXLoc() + (t.getWidth() / 2), 2);

            }
            if (isDrawXAxis()) {
                g.setColor(tickColor);
                g.drawLine(0, 0, this.getWidth(), 0);

            }

        } catch (Exception e) {
            logger.log(Level.FINE, "Exception in painting XAxis : ", e);
        }

    }

    public long getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(long rr) {
        if (this.refreshRate != rr)
            logger.finest("Refreshrate changed to " + rr);
        this.refreshRate = rr;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getTickColor() {
        return tickColor;
    }

    public void setTickColor(Color tickColor) {
        this.tickColor = tickColor;
    }

    public boolean isAntiAliased() {
        return AntiAliased;
    }

    public void setAntiAliased(boolean AntiAliased) {
        this.AntiAliased = AntiAliased;
    }

    public boolean isDrawXAxis() {
        return drawXAxis;
    }

    public void setDrawXAxis(boolean drawXAxis) {
        this.drawXAxis = drawXAxis;
    }

    public int getBufferGap() {
        return gap;
    }

    public void setBufferGap(int buffer) {
        this.gap = buffer;
    }

    public int getxAxisHeight() {
        return xAxisHeight;
    }


    private class TimeBox {
        int xLoc = 0;
        int width = 0;
        String time = null;

        public TimeBox(int x, int w, String t) {
            xLoc = x;
            width = w;
            time = t;
        }

        public int getXLoc() {
            return xLoc;
        }

        public void setXLoc(int xLoc) {
            this.xLoc = xLoc;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

}
