package com.dom.util.graphs;

import com.dom.util.graphs.PlotArea.LookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Chart extends JPanel {


    private static final Logger logger = Logger.getLogger(Chart.class.getName());
    protected boolean drawing50percentile = true;
    protected boolean drawingAdditionalValueLines = true;
    protected boolean drawingChartValues = false;
    protected boolean drawingCurrentValue = true;
    protected boolean drawingDataString = false;
    protected boolean drawingFloatingLegend = false;
    protected boolean drawingGrid = true;
    protected boolean drawingXAxisTime = false;
    protected boolean drawingYAxisLabel = false;
    protected boolean drawingYAxisTicks = false;
    protected boolean dynamicallyReset = false;
    protected boolean maxSetManually = false;
    protected Color[] chartForegroundColors = Chart.getColorArray(8);
    protected String[] dataLabels = Chart.getLabelArray(8);
    private PlotArea plotArea = new StackedPlotArea();
    private BorderLayout borderLayout1 = new BorderLayout(0, 0);
    private BorderLayout borderLayout2 = new BorderLayout(0, 0);
    private BorderLayout borderLayout3 = new BorderLayout(0, 0);
    private BorderLayout northBufferLayout = new BorderLayout(0, 0);
    private BorderLayout southBufferLayout = new BorderLayout(0, 0);
    private ChartType chartType = Chart.ChartType.AREACHART;
    private Color backGroundColor = Color.white;
    private Color gridColor = Color.gray;
    private Color TitleFontColor = Color.black;
    private Color xAxisFontColor = Color.darkGray;
    private Color yAxisFontColor = Color.black;
    private Font titleFont = new Font("Arial", Font.BOLD, 13);
    private Font xAxisFont = new Font("Arial", Font.PLAIN, 10);
    private Font yAxisFont = new Font("Arial", Font.PLAIN, 11);
    private int eastBuffer = 0;
    private int northBuffer = 0;
    private int southBuffer = 0;
    private long refreshRate = 1000;
    private JLabel eastInset = new JLabel();
    private JLabel northInset = new JLabel();
    private JLabel southInset = new JLabel();
    private JLabel westInset = new JLabel();
    private JPanel chartPanel = new JPanel();
    private JPanel drawingArea = new JPanel();
    private JPanel northChartBuffer = new JPanel();
    private JPanel southChartBuffer = new JPanel();
    private LookAndFeel lookAndFeel = LookAndFeel.WHITE;
    private String titleLabel = "";
    private String xAxisLabel = "";
    private String yAxisLabel = "";
    //private VTextIcon vTextIcon = new VTextIcon(westInset,"YAxis");
    private XAxisTimePanel xAxisTimePanel = null;
    private YAxis yAxis = new YAxis(this);


    public Chart() {
        super();
        this.chartType = chartType;
        init();
        plotArea.init(120, 1, new Color[]{Color.red});
    }

    public Chart(ChartType chartType, int xAxisItemCount) {
        super();
        this.chartType = chartType;
        init();
        plotArea.init(xAxisItemCount, 1, new Color[]{Color.red});
    }

    public Chart(ChartType chartType, int xAxisItemCount, int dataSetCount) {
        super();
        this.chartType = chartType;
        init();
        plotArea.init(xAxisItemCount, dataSetCount, Arrays.copyOfRange(chartForegroundColors, 0, dataSetCount), Arrays.copyOfRange(dataLabels, 0, dataSetCount));
    }

    public Chart(ChartType chartType, int xAxisItemCount, Color[] chartColors) {
        super();
        this.chartType = chartType;
        init();
        plotArea.init(xAxisItemCount, 1, chartColors);
    }

    public Chart(ChartType chartType, int xAxisItemCount, int dataSetCount, Color[] chartColors, String[] dataLabels) {
        super();
        this.chartType = chartType;
        init();
        plotArea.init(xAxisItemCount, dataSetCount, chartColors, dataLabels);
    }

    public Chart(ChartType chartType, int xAxisItemCount, int dataSetCount, Color[] chartColors, String[] dataLabels, LookAndFeel lf) {
        super();
        assert chartColors.length == dataSetCount;
        assert dataLabels.length == chartColors.length;
        this.chartType = chartType;
        this.lookAndFeel = lf;
        init();
        plotArea.init(xAxisItemCount, dataSetCount, chartColors, dataLabels);
    }

    public static Color[] getColorArray(int size) {
        return Arrays.copyOf(PlotArea.COLOR_MAP, size);

    }

    public static String[] getLabelArray(int size) {
        return Arrays.copyOf(PlotArea.LABEL_MAP, size);

    }

    public void init() {

        switch (chartType) {
            case LINECHART:
                plotArea = new LinePlotArea();
                break;
            case AREACHART:
                plotArea = new StackedPlotArea();
                break;
            default:
        }
        setLookandFeel(lookAndFeel);
        westInset.setFont(yAxisFont);
        southInset.setFont(xAxisFont);
        northInset.setFont(titleFont);
        //yAxis.setVerticalBuffer(5);
        yAxis.setTickColor(getGridColor());
        try {
            jbInit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error initialising chart components", e);
        }
    }

    public void putData(Integer[] dataArray) throws Exception {
        Long[] newArray = new Long[dataArray.length];

        for (int i = 0; i < dataArray.length; i++) {
            if (dataArray[i] != null)
                newArray[i] = Long.valueOf(dataArray[i]);
            else
                newArray[i] = 0L;
        }
        putData(newArray);
    }


    public void putData(Long[] data) throws Exception {
        if (isDrawingYAxisTicks()) {
            plotArea.setTickMarkers(yAxis.getTickLocations());
        }
        plotArea.putData(data);
        yAxis.revalidate();
        yAxis.repaint();
    }

    public void addNewSeries(String label) {
        plotArea.addNewSeries(label);
    }

    public void paintChart() {
        yAxis.repaint();
        plotArea.repaint();
    }

    public void jbInit() {
        northChartBuffer.setPreferredSize(new Dimension(0, 0));
        this.setLayout(borderLayout1);
        chartPanel.setLayout(borderLayout2);
        drawingArea.setLayout(borderLayout3);
        northChartBuffer.setLayout(northBufferLayout);
        southChartBuffer.setLayout(southBufferLayout);
        eastInset.setOpaque(true);
        westInset.setOpaque(true);
        northInset.setHorizontalAlignment(SwingConstants.CENTER);
        southInset.setHorizontalAlignment(SwingConstants.CENTER);
//        drawingArea.add(northChartBuffer, BorderLayout.NORTH);
        drawingArea.add(southChartBuffer, BorderLayout.SOUTH);
        drawingArea.add(plotArea, BorderLayout.CENTER);
        //chartPanel.add(yAxis, BorderLayout.WEST);
        chartPanel.add(drawingArea, BorderLayout.CENTER);
//        this.add(northInset, BorderLayout.NORTH);
        //this.add(southInset, BorderLayout.SOUTH);
        this.add(eastInset, BorderLayout.EAST);
        this.add(westInset, BorderLayout.WEST);
        this.add(chartPanel, BorderLayout.CENTER);
    }


    public void setYAxisVisible(boolean yAxisVisible) {
        if (!yAxisVisible) {
            chartPanel.remove(yAxis);
            revalidate();
        } else {
            if (yAxis == null)
                yAxis = new YAxis(this);
            setNorthBuffer(yAxis.getNorthernBuffer());
            logger.finest("YAxis Northern = " + yAxis.getNorthernBuffer());
            chartPanel.add(yAxis, BorderLayout.WEST);
            FontMetrics metrics = this.getFontMetrics(xAxisFont);
            double fontHeight = metrics.getAscent() + metrics.getDescent() + (metrics.getAscent() + metrics.getDescent() / 2D);
            revalidate();
        }
    }

    public boolean isDrawingYAxisLabel() {
        return drawingYAxisLabel;
    }


    public void setDrawingYAxisLabel(boolean drawingYAxisLabel) {
        this.drawingYAxisLabel = drawingYAxisLabel;
        if (yAxis != null) {
            if (!isDrawingYAxisLabel()) {
                //westInset.setIcon(new VTextIcon(westInset,""));
                westInset.setText("");
                westInset.setUI(new VerticalLabelUI(true));
                westInset.setHorizontalAlignment(JLabel.CENTER);
                revalidate();
            } else {
                westInset.setFont(yAxis.getFont());
                //westInset.setIcon(new VTextIcon(westInset,yAxisLabel));
                westInset.setText(yAxisLabel);
                westInset.setUI(new VerticalLabelUI(true));
                westInset.setHorizontalAlignment(JLabel.CENTER);
                revalidate();
            }
        }
    }

    public void setMaxYValue(Double maxVal) {
        plotArea.setMaxYValue(maxVal);
        plotArea.setMaxSetManually(true);
    }

//    public void setMaxYValue(Double maxVal) {
//        baseChart.setMaxYVal(maxVal);
//    }
//    
//    public void setMinYValue(Double maxVal) {
//        baseChart.setMinYVal(maxVal);
//    }

    public void setMinYValue(Double minVal) {
        plotArea.setMinYVal(minVal);
//        yAxis.setMinValue(minVal);
    }

    public Double getMaxYVal() {
        return plotArea.getMaxYVal();
    }

    public void setChartStrokeThickness(float st) {
        plotArea.setChartStrokeThickness(st);
    }

    public boolean isDynamicallyReset() {
        return plotArea.isDynamicallyReset();
    }

    public void setDynamicallyReset(boolean dynamicallyReset) {
        plotArea.setDynamicallyReset(dynamicallyReset);
    }

    public boolean isDrawingGrid() {
        return plotArea.isDrawGrid();
    }

    public void setDrawingGrid(boolean drawingGrid) {
        plotArea.setDrawGrid(drawingGrid);
    }

    public boolean isDrawingCurrentValue() {
        return plotArea.isDrawingCurrentValue();
    }

    public void setDrawingCurrentValue(boolean drawingCurrentValue) {
        plotArea.setDrawingCurrentValue(drawingCurrentValue);
    }

    public boolean isDrawing50percentile() {
        return plotArea.isDraw50percentile();
    }

    public void setDrawing50percentile(boolean drawing50percentile) {
        plotArea.setDraw50percentile(drawing50percentile);
    }

    public boolean isDrawingAdditionalValueLines() {
        return plotArea.isdrawingAdditionalValueLines;
    }

    public void setDrawingAdditionalValueLines(boolean drawingAdditionalValueLines) {
        plotArea.setIsdrawingAdditionalValueLines(drawingAdditionalValueLines);
    }

    public boolean isDrawingFloatingLegend() {
        return plotArea.isDrawingFloatingLegend();
    }

    public void setDrawingFloatingLegend(boolean drawingFloatingLegend) {
        plotArea.setDrawingFloatingLegend(drawingFloatingLegend);
    }

    public boolean isDrawOnlyNonZeroLegendValues() {
        return plotArea.isDrawOnlyNonZeroLegendValues();
    }

    public void setDrawOnlyNonZeroLegendValues(boolean drawOnlyNonZeroLegendValues) {
        plotArea.setDrawOnlyNonZeroLegendValues(drawOnlyNonZeroLegendValues);
    }

    public String getYAxisLabel() {
        return yAxisLabel;
    }

    public void setYAxisLabel(String yAxisLabel) {
        if (drawingYAxisLabel) {
            this.yAxisLabel = yAxisLabel;
            westInset.setText(yAxisLabel);
            westInset.setUI(new VerticalLabelUI(true));
            westInset.setHorizontalAlignment(JLabel.CENTER);
            revalidate();
        }
    }

    public String getXAxisLabel() {
        return xAxisLabel;
    }

    public void setXAxisLabel(String XAxisLabel) {
        this.xAxisLabel = XAxisLabel;
        southInset.setFont(new Font("Arial", Font.PLAIN, 10));
        southInset.setText(xAxisLabel);
        revalidate();
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(String titleLabel) {
        this.titleLabel = titleLabel;
        northInset.setText(titleLabel);
        revalidate();
    }

    public int getNorthBuffer() {
        return northBuffer;
    }

    public void setNorthBuffer(int northBuffer) {
        this.northBuffer = northBuffer;
        northChartBuffer.setPreferredSize(new Dimension(200, northBuffer));
    }

    public int getSouthBuffer() {
        return southBuffer;
    }

    public void setSouthBuffer(int southBuffer) {
        this.southBuffer = southBuffer;
        southChartBuffer.setPreferredSize(new Dimension(200, southBuffer));
    }

    public int getEastBuffer() {
        return eastBuffer;
    }

    public void setEastBuffer(int eastBuffer) {
        this.eastBuffer = eastBuffer;
        eastInset.setPreferredSize(new Dimension(eastBuffer, 10));
    }

    public int getYAxisTextAndTicksWidth() {
        return yAxis.getYAxisDecorationsWidth();
    }

    public int getYAxisWidth() {
        return yAxis.getYAxisDecorationsWidth() + yAxis.getYAxisHorizontalSpacer();
//        return westBuffer;
    }

    public void setYAxisWidth(int westBuffer) {
        yAxis.setYAxisWidth(westBuffer);
        revalidate();
    }

    public Chart.ChartType getChartType() {
        return chartType;
    }

    public void setChartType(Chart.ChartType chartType) {
        this.chartType = chartType;
    }

    public Font getYAxisFont() {
        return yAxisFont;
    }

    public void setYAxisFont(Font yAxisFont) {
        this.yAxisFont = yAxisFont;
    }

    public Font getXAxisFont() {
        return xAxisFont;
    }

    public void setXAxisFont(Font xAxisFont) {
        this.xAxisFont = xAxisFont;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }

    public Color[] getChartForegroundColors() {
        return chartForegroundColors;
    }

    public void setChartForegroundColors(Color[] chartForegroundColors) {
        this.chartForegroundColors = chartForegroundColors;
    }

    public Color getYAxisFontColor() {
        return yAxisFontColor;
    }

    public void setYAxisFontColor(Color yAxisFontColor) {
        this.yAxisFontColor = yAxisFontColor;
        westInset.setForeground(yAxisFontColor);
        repaint();
    }

    public Color getXAxisFontColor() {
        return xAxisFontColor;
    }

    public void setXAxisFontColor(Color xAxisFontColor) {
        this.xAxisFontColor = xAxisFontColor;
        southInset.setForeground(xAxisFontColor);
        repaint();
    }

    public void setDisplayMessage(String message) {
        plotArea.setDataString(message);
        repaint();
    }

    public void setInsetTitle(String label, ChartLocation location) {
        plotArea.setInsetTitle(label, new Font("Arial", Font.PLAIN, 12), location);
    }

    public void setInsetTitle(String label, ChartLocation location, Font font) {
        plotArea.setInsetTitle(label, font, location);
    }

    public void setDisplayMessageFont(Font f) {
        plotArea.setDisplayMessageFont(f);

    }

    public void setDrawingDisplayMessage(boolean b) {
        plotArea.setDrawingDataString(b);
    }

    public Color getTitleFontColor() {
        return TitleFontColor;
    }

    public void setTitleFontColor(Color TitleFontColor) {
        this.TitleFontColor = TitleFontColor;
        northInset.setForeground(TitleFontColor);
        repaint();
    }

    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public Color getGridColor() {
        return gridColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    public boolean isDrawingYAxisTicks() {
        return drawingYAxisTicks;
    }

//    public void setDrawingYAxisTicks(List<Integer> ticks) {
//        this.drawingYAxisTicks = true;
//        baseChart.setTickMarkers(ticks);
//    }

    public void setDrawingYAxisTicks(boolean drawingYAxisTicks) {
        this.drawingYAxisTicks = drawingYAxisTicks;
    }

    public YAxis getYAxis() {
        return yAxis;
    }

    public void setAdditionalValueLines(Map<String, Integer> additionalValueLines) {
        plotArea.setAdditionalValueLines(additionalValueLines);
    }

    public void setYAxisLabelFont(Font font) {
        westInset.setFont(font);
    }

    public void setYAxisLabelFontColor(Color color) {
        westInset.setForeground(color);
    }

    public Double getLegendValueScaler() {
        return plotArea.getLegendValueScaler();
    }

    public void setLegendValueScaler(Double legendValueScaler) {
        plotArea.setLegendValueScaler(legendValueScaler);
    }

    public long getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(long rr) {
        refreshRate = rr;
        xAxisTimePanel.setRefreshRate(refreshRate);
    }

    public boolean isDrawingXAxisTime() {
        return drawingXAxisTime;
    }

    public void setDrawingXAxisTime(boolean isDrawingAxis) {

        FontMetrics metrics = this.getFontMetrics(xAxisFont);
        double fontHeight = metrics.getAscent() + metrics.getDescent() + (metrics.getAscent() + metrics.getDescent() / 2D);
        if (isDrawingAxis) {
            if (!this.drawingXAxisTime) {
                this.drawingXAxisTime = true;
                logger.finest("Trying to add XSeries Panel");
                xAxisTimePanel = new XAxisTimePanel(this);
                xAxisTimePanel.setFont(xAxisFont);
                xAxisTimePanel.setOpaque(true);
                setLookandFeel(lookAndFeel);
                xAxisTimePanel.setPreferredSize(new Dimension(0,metrics.getHeight() + 6));
                southChartBuffer.add(xAxisTimePanel, BorderLayout.CENTER);
                plotArea.revalidate();
            }
        } else {
            if (yAxis != null)
                setSouthBuffer((int)yAxis.getVerticalBuffer());
            else
                setSouthBuffer(0);
//                yAxis.setXAxisHeight(xAxisTimePanel.getxAxisHeight());
            plotArea.revalidate();
        }
    }

    public XAxisTimePanel getXAxisTimePanel() {
        return xAxisTimePanel;
    }

    public PlotArea getPlotArea() {
        return plotArea;
    }

    public void setLookandFeel(LookAndFeel lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
        plotArea.setLookandFeel(lookAndFeel);
        switch (lookAndFeel) {
            case BLACK: {
                northChartBuffer.setBackground(Color.black);
                southChartBuffer.setBackground(Color.black);
                westInset.setBackground(Color.black);
                eastInset.setBackground(Color.black);
                chartPanel.setBackground(Color.black);
                chartPanel.setForeground(new Color(45, 247, 14));
                if (yAxis != null) {
                    yAxis.setForeground(new Color(4, 203, 0));
                    yAxis.setBackground(Color.black);
                    yAxis.setFont(new Font("Arial", Font.BOLD, 11));
                    setYAxisLabelFontColor(new Color(4, 203, 0));
                    setYAxisLabelFont(new Font("Arial", Font.BOLD, 11));
                }
                setGridColor(Color.lightGray);
                if (isDrawingXAxisTime()) {
                    xAxisTimePanel.setTextColor(new Color(4, 203, 0));
                    xAxisTimePanel.setFont(new Font("Arial", 1, 11));
                    xAxisTimePanel.setBackground(Color.black);
                }
            }
            break;
            case WHITE: {
                northChartBuffer.setBackground(Color.white);
                southChartBuffer.setBackground(Color.white);
                westInset.setBackground(Color.white);
                eastInset.setBackground(Color.white);
                chartPanel.setBackground(Color.white);
                chartPanel.setForeground(Color.darkGray);
                yAxis.setForeground(Color.darkGray);
                yAxis.setBackground(Color.white);
                yAxis.setFont(new Font("Arial", Font.PLAIN, 11));
                setYAxisLabelFontColor(Color.darkGray);
                setYAxisLabelFont(new Font("Arial", Font.PLAIN, 11));
                setGridColor(Color.darkGray);
                if (isDrawingXAxisTime()) {
                    xAxisTimePanel.setTextColor(Color.darkGray);
                    xAxisTimePanel.setFont(new Font("Arial", 1, 11));
                    xAxisTimePanel.setBackground(Color.white);
                }
            }
        }
    }

    public enum ChartType {
        LINECHART,
        AREACHART;
    }

    public enum ChartLocation {
        TOP_LEFT, BOTTOM_LEFT, TOP_RIGHT, BOTTOM_RIGHT
    }


}
