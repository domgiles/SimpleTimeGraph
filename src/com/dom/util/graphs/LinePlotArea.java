package com.dom.util.graphs;


import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.logging.Logger;


public class LinePlotArea extends PlotArea {

    private static final Logger logger = Logger.getLogger(LinePlotArea.class.getName());

    public LinePlotArea() {
        super();
    }

    public LinePlotArea(int initialSize, int dataSetCount) {
        super();
        init(initialSize, dataSetCount);
    }

    public LinePlotArea(Color[] colors) {
        super();
        setChartForegroundColors(colors);
    }


    protected Shape createGraphShape(LinkedList<Long> graphData) {

        calculateOffsets();

        float xLoc = hborder;
        float yEnd = height - vborder;
        GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD, graphData.size());
        gp.moveTo(xLoc, yEnd);
        double yValue = 0;
        for (long value : graphData) {
            yValue = yEnd - (value * ratio);
            gp.lineTo(xLoc, yValue);
            xLoc += spacer;
        }

        ratio = (float) (height - (vborder * 2)) / ((getMaxYVal() == 0) ? 1 : getMaxYVal());
        return gp;
    }

    @Override
    public void putData(Integer[] dataArray) throws Exception {
        Long[] newArray = new Long[dataArray.length];

        for (int i = 0; i < dataArray.length; i++) {
            newArray[i] = new Long(dataArray[i]);
        }
        putData(newArray);
    }

    @Override
    public void putData(Long[] dataArray) throws Exception {

        long currentMax = 0;
        for (long v : dataArray) {
            currentMax = Math.max(currentMax, v);
        }


        if (isMaxSetManually()) {
            if ((currentMax > getMinYVal()) && (currentMax > getMaxYVal())) {
                if (currentMax < getRequestedMaxYVal()) {
                    setMaxYVal(currentMax);
                } else {
                    setMaxYVal(getRequestedMaxYVal());
                }
            }
        } else {
            if ((currentMax > getMinYVal()) && (currentMax > getMaxYVal())) {
                setMaxYVal(currentMax);
            }
        }


        int pointer = 0;

        Long firstEntriesValue = dataSets.get(dataSets.size() - 1).getFirst();

        try {
            for (Long newValue : dataArray) {
                LinkedList<Long> data = dataSets.get(pointer++);
                data.add(newValue);
                data.removeFirst();
            }
        } catch (Exception e) {
            throw new RuntimeException("Number of series/data sets does not match number specified at initialisation");
        }

        pointer = 0;
        if (isDynamicallyReset()) {
            if (firstEntriesValue == getMaxYVal().intValue()) {
                setMaxYVal(0d);
                long pointInTimeVal = 0;
                for (int i = 0; i < dataSets.get(0).size(); i++) {
                    pointInTimeVal = dataSets.get(dataSets.size() - 1).get(i);
                    setMaxYVal((getMaxYVal() < pointInTimeVal) ? pointInTimeVal : getMaxYVal());
                }
            }
        }


        currentValue = currentMax;
        //Syslog.debug(this,"Total = " + valueTotal);
        calculateOffsets();
        calcRatios();
        repaint();
    }


    public void paintChart(Graphics g) {

        int colorCode = 0;
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        BufferedImage graphImage = (BufferedImage) this.createImage(getSize().width, getSize().height);
        Graphics2D g2 = graphImage.createGraphics();
        g2.setColor(getChartBackgroundColor());
        g2.fillRect(0, 0, width, height);
        BasicStroke stroke = new BasicStroke(Math.abs(3.0f * ((float) height / 150.0f)));
        for (LinkedList<Long> data : dataSets) {
            Shape graphShape = createGraphShape(data);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getChartForegroundColors()[colorCode++]);
            g2.setStroke(stroke);
            g2.draw(graphShape);

        }
        g.drawImage(graphImage, 0, 0, null);
        drawDecoration((Graphics2D) g);
    }

}
