package com.dom.util.graphs;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StackedPlotArea extends PlotArea {

    private static final Logger logger = Logger.getLogger(StackedPlotArea.class.getName());


    public StackedPlotArea() {
        super();
    }

    public StackedPlotArea(int initialSize, int dataSetCount) {
        super();
        init(initialSize, dataSetCount);
    }

    @Override
    protected Long[] getValuesFromGraph(int xLocation) {
        Long[] value = new Long[dataSets.size()];
        int width = this.getWidth();
        int dataPoints = dataSets.get(0).size();
        int closestValue = (int) (((double) xLocation / (double) width) * dataPoints);

        int stackTotal = 0;
        for (int i = 0; i < dataSets.size(); i++) {
            try {
                LinkedList<Long> ll = dataSets.get(i);
                value[i] = ll.get(closestValue) - stackTotal;
                stackTotal += value[i];
            } catch (IndexOutOfBoundsException e) {
                value[i] = 0L;
            }
        }
        return value;
    }

    @Override
    public void putData(Integer[] dataArray) throws Exception {
        Long[] newArray = new Long[dataArray.length];

        for (int i = 0; i < dataArray.length; i++) {
            try {
                newArray[i] = dataArray[i].longValue();
            } catch (IndexOutOfBoundsException e) {     // data change (unexpected)
                logger.log(Level.SEVERE, "Must have attempted to insert dataset large than items in chart : ", e);
            }
        }
        putData(newArray);
    }

    @Override
    public void putData(Long[] dataArray) throws Exception {

        try {
            long valueTotal = 0L;
            for (long v : dataArray) {
                valueTotal += v;
            }

            if (isMaxSetManually()) {
                if ((valueTotal > getMinYVal()) && (valueTotal > getMaxYVal())) {
                    if (valueTotal < getRequestedMaxYVal()) {
                        setMaxYVal(valueTotal);
                    } else {
                        setMaxYVal(getRequestedMaxYVal());
                    }
                }
            } else {
                if ((valueTotal > getMinYVal()) && (valueTotal > getMaxYVal())) {
                    setMaxYVal(valueTotal);
                }
            }


            int pointer = 0;
            long previousValue = 0;

            Long firstEntriesValue = dataSets.get(dataSets.size() - 1).getFirst();

            for (Long newValue : dataArray) {
                long currValue = newValue;
                LinkedList<Long> data = dataSets.get(pointer++);
                currValue += previousValue;
                data.add(currValue);
                data.removeFirst();
                previousValue = currValue;
            }

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

            currentValue = valueTotal;
            calculateOffsets();
            calcRatios();
            repaint();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new Exception("Added data values count don't match initialisation parameters");
        } catch (Exception e) {
            logger.log(Level.SEVERE,
                    "Unexpected Exception when adding data to graph",
                    e);
        }
    }

    public void paintChart(Graphics g) throws Exception {
        try {
            if (width > 0 && height > 0) {
                g.setColor(getChartBackgroundColor());
                g.fillRect(0, 0, width, height);
                int colorCode = getChartForegroundColors().length - 1;
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                ListIterator<LinkedList<Long>> li = dataSets.listIterator(dataSets.size());

                while (li.hasPrevious()) {
                    Shape graphShape = createGraphShape(li.previous());
                    BufferedImage graphImage = colourGraph(g, graphShape, getChartForegroundColors()[colorCode--]);
                    g.drawImage(graphImage, 0, 0, null);
                }
                drawDecoration((Graphics2D) g);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new Exception("Added data values count don't match initialisation parameters", e);
        } catch (Exception e) {
            logger.log(Level.FINE, "Unexpected Exception when drawing the graph", e);
        }
    }
}
