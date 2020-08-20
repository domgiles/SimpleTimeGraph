package com.dom.util.graphs;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class PlotArea extends JPanel implements Runnable, ComponentListener, MouseListener, MouseMotionListener, ChartInterface {


    public static final String[] LABEL_MAP =
            new String[]{"Data1", "Data2", "Data3", "Data4", "Data5", "Data6", "Data7", "Data8", "Data9", "Data10", "Data11", "Data12", "Data13", "Data14", "Data15", "Data16", "Data17", "Data18",
                    "Data19", "Data20", "Data21", "Data22", "Data23", "Data24", "Data25", "Data26", "Data27", "Data28", "Data29", "Data30", "Data31", "Data32", "Data33", "Data34", "Data35",
                    "Data36", "Data37", "Data38", "Data39", "Data40", "Data41", "Data42", "Data43", "Data44", "Data45", "Data46", "Data47", "Data48", "Data49", "Data50", "Data51", "Data52",
                    "Data53", "Data54", "Data55", "Data56", "Data57", "Data58", "Data59", "Data60", "Data61", "Data62", "Data63", "Data64", "Data65", "Data66", "Data67", "Data68", "Data69",
                    "Data70", "Data71", "Data72", "Data73", "Data74", "Data75", "Data76", "Data77", "Data78", "Data79", "Data80", "Data81", "Data82", "Data83", "Data84", "Data85", "Data86",
                    "Data87", "Data88", "Data89", "Data90", "Data91", "Data92", "Data93", "Data94", "Data95", "Data96", "Data97", "Data98", "Data99", "Data100", "Data101", "Data102", "Data103",
                    "Data104", "Data105", "Data106", "Data107", "Data108", "Data109", "Data110", "Data111", "Data112", "Data113", "Data114", "Data115", "Data116", "Data117", "Data118", "Data119",
                    "Data120", "Data121", "Data122", "Data123", "Data124", "Data125", "Data126", "Data127", "Data128", "Data129", "Data130", "Data131", "Data132", "Data133", "Data134", "Data135",
                    "Data136", "Data137", "Data138", "Data139", "Data140", "Data141", "Data142", "Data143", "Data144", "Data145", "Data146", "Data147", "Data148", "Data149", "Data150", "Data151",
                    "Data152", "Data153", "Data154", "Data155", "Data156", "Data157", "Data158", "Data159", "Data160", "Data161", "Data162", "Data163", "Data164", "Data165", "Data166", "Data167",
                    "Data168", "Data169", "Data170", "Data171", "Data172", "Data173", "Data174", "Data175", "Data176", "Data177", "Data178", "Data179", "Data180", "Data181", "Data182", "Data183",
                    "Data184", "Data185", "Data186", "Data187", "Data188", "Data189", "Data190", "Data191", "Data192", "Data193", "Data194", "Data195", "Data196", "Data197", "Data198", "Data199",
                    "Data200", "Data201", "Data202", "Data203", "Data204", "Data205", "Data206", "Data207", "Data208", "Data209", "Data210", "Data211", "Data212", "Data213", "Data214", "Data215",
                    "Data216", "Data217", "Data218", "Data219", "Data220", "Data221", "Data222", "Data223", "Data224", "Data225", "Data226", "Data227", "Data228", "Data229", "Data230", "Data231",
                    "Data232", "Data233", "Data234", "Data235", "Data236", "Data237", "Data238", "Data239", "Data240", "Data241", "Data242", "Data243", "Data244", "Data245", "Data246", "Data247",
                    "Data248", "Data249", "Data250", "Data251", "Data252", "Data253", "Data254", "Data255", "Data256"};
    public static final Color[] COLOR_MAP =
            new Color[]{new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255), new Color(255, 200, 0), new Color(0, 255, 255), new Color(255, 0, 255), new Color(255, 255, 0),
                    new Color(255, 175, 175), new Color(203, 244, 72), new Color(24, 211, 99), new Color(160, 208, 198), new Color(175, 211, 249), new Color(111, 248, 40), new Color(58, 37, 66),
                    new Color(225, 240, 189), new Color(144, 229, 194), new Color(72, 3, 209), new Color(126, 240, 97), new Color(168, 130, 203), new Color(159, 135, 10), new Color(175, 232, 70),
                    new Color(159, 15, 102), new Color(133, 30, 100), new Color(239, 107, 58), new Color(238, 85, 56), new Color(141, 106, 141), new Color(252, 122, 221), new Color(164, 234, 37),
                    new Color(238, 120, 26), new Color(206, 207, 203), new Color(55, 159, 152), new Color(75, 51, 217), new Color(181, 60, 234), new Color(240, 75, 155), new Color(207, 182, 109),
                    new Color(82, 169, 63), new Color(160, 241, 245), new Color(130, 116, 53), new Color(216, 11, 225), new Color(16, 64, 81), new Color(184, 171, 140), new Color(78, 165, 32),
                    new Color(42, 11, 73), new Color(17, 133, 183), new Color(83, 151, 19), new Color(200, 223, 253), new Color(134, 50, 53), new Color(10, 250, 177), new Color(108, 79, 115),
                    new Color(93, 228, 43), new Color(7, 123, 31), new Color(143, 29, 172), new Color(31, 175, 70), new Color(248, 168, 6), new Color(162, 86, 103), new Color(224, 73, 109),
                    new Color(156, 216, 192), new Color(69, 251, 9), new Color(133, 134, 51), new Color(17, 132, 166), new Color(0, 3, 144), new Color(53, 217, 110), new Color(55, 230, 226),
                    new Color(168, 181, 200), new Color(77, 83, 124), new Color(24, 101, 216), new Color(109, 249, 43), new Color(62, 229, 31), new Color(201, 82, 183), new Color(225, 54, 145),
                    new Color(142, 53, 6), new Color(169, 98, 236), new Color(190, 163, 116), new Color(121, 17, 6), new Color(182, 87, 229), new Color(91, 158, 180), new Color(154, 118, 44),
                    new Color(201, 174, 203), new Color(81, 140, 194), new Color(4, 254, 7), new Color(23, 146, 210), new Color(189, 170, 223), new Color(211, 226, 163), new Color(246, 142, 34),
                    new Color(244, 62, 217), new Color(222, 32, 253), new Color(144, 152, 121), new Color(201, 242, 41), new Color(128, 3, 161), new Color(14, 225, 196), new Color(231, 220, 22),
                    new Color(118, 149, 69), new Color(222, 49, 186), new Color(190, 157, 218), new Color(120, 30, 41), new Color(168, 117, 210), new Color(190, 42, 180), new Color(110, 147, 179),
                    new Color(57, 138, 18), new Color(44, 113, 184), new Color(192, 203, 72), new Color(107, 202, 112), new Color(164, 75, 100), new Color(81, 86, 88), new Color(132, 246, 173),
                    new Color(156, 11, 46), new Color(110, 252, 229), new Color(179, 115, 225), new Color(211, 224, 20), new Color(242, 17, 120), new Color(69, 69, 164), new Color(45, 142, 153),
                    new Color(14, 246, 24), new Color(45, 172, 182), new Color(130, 204, 179), new Color(225, 205, 119), new Color(89, 51, 44), new Color(227, 179, 100), new Color(154, 170, 249),
                    new Color(6, 253, 167), new Color(198, 105, 200), new Color(108, 195, 102), new Color(108, 47, 50), new Color(123, 94, 204), new Color(240, 114, 30), new Color(199, 11, 45),
                    new Color(107, 43, 34), new Color(193, 218, 97), new Color(9, 79, 8), new Color(208, 76, 90), new Color(231, 34, 186), new Color(111, 63, 124), new Color(207, 41, 168),
                    new Color(20, 110, 91), new Color(125, 123, 137), new Color(80, 188, 24), new Color(48, 163, 39), new Color(14, 239, 243), new Color(231, 135, 161), new Color(103, 137, 55),
                    new Color(154, 83, 131), new Color(56, 145, 208), new Color(59, 49, 193), new Color(101, 68, 205), new Color(149, 16, 150), new Color(31, 135, 49), new Color(145, 124, 16),
                    new Color(205, 2, 198), new Color(42, 162, 188), new Color(191, 97, 78), new Color(8, 97, 112), new Color(162, 209, 148), new Color(47, 201, 143), new Color(201, 222, 149),
                    new Color(211, 62, 2), new Color(168, 177, 152), new Color(142, 160, 159), new Color(178, 158, 179), new Color(242, 3, 198), new Color(77, 192, 39), new Color(229, 105, 232),
                    new Color(120, 145, 105), new Color(239, 93, 26), new Color(240, 139, 96), new Color(4, 73, 29), new Color(165, 201, 118), new Color(185, 109, 240), new Color(185, 35, 198),
                    new Color(219, 228, 185), new Color(111, 45, 70), new Color(144, 48, 92), new Color(53, 178, 150), new Color(175, 189, 38), new Color(40, 25, 219), new Color(106, 130, 48),
                    new Color(241, 245, 223), new Color(49, 7, 203), new Color(172, 3, 56), new Color(65, 162, 15), new Color(182, 250, 241), new Color(100, 32, 128), new Color(127, 137, 217),
                    new Color(244, 74, 106), new Color(93, 50, 40), new Color(24, 0, 9), new Color(9, 75, 236), new Color(95, 229, 88), new Color(80, 179, 24), new Color(184, 221, 244),
                    new Color(223, 80, 199), new Color(142, 25, 121), new Color(86, 87, 15), new Color(37, 251, 107), new Color(97, 129, 175), new Color(214, 156, 71), new Color(217, 224, 43),
                    new Color(49, 204, 228), new Color(135, 47, 42), new Color(183, 137, 125), new Color(4, 59, 207), new Color(63, 199, 22), new Color(220, 40, 55), new Color(76, 137, 100),
                    new Color(207, 102, 235), new Color(82, 249, 167), new Color(130, 122, 74), new Color(53, 218, 250), new Color(232, 56, 113), new Color(165, 252, 151), new Color(154, 78, 126),
                    new Color(131, 211, 16), new Color(116, 213, 141), new Color(200, 19, 68), new Color(234, 206, 112), new Color(107, 135, 141), new Color(226, 25, 128), new Color(209, 62, 142),
                    new Color(89, 133, 38), new Color(17, 104, 28), new Color(183, 216, 142), new Color(16, 176, 86), new Color(229, 193, 226), new Color(199, 19, 113), new Color(166, 77, 96),
                    new Color(76, 110, 29), new Color(228, 54, 38), new Color(199, 24, 144), new Color(146, 107, 14), new Color(63, 1, 68), new Color(201, 24, 3), new Color(70, 116, 47),
                    new Color(238, 76, 107), new Color(167, 253, 237), new Color(91, 104, 195), new Color(95, 103, 120), new Color(164, 97, 143), new Color(212, 147, 14), new Color(21, 69, 50),
                    new Color(56, 159, 45), new Color(219, 42, 75), new Color(50, 178, 185), new Color(149, 113, 21), new Color(49, 66, 37), new Color(76, 236, 25), new Color(196, 62, 85),
                    new Color(44, 53, 77), new Color(195, 242, 243), new Color(74, 199, 60), new Color(44, 127, 134), new Color(72, 54, 136), new Color(51, 247, 71), new Color(120, 27, 19),
                    new Color(18, 99, 120), new Color(17, 178, 241), new Color(221, 183, 96), new Color(23, 47, 43)};
    protected static final Font BOX_FONT = new Font("Helvetica", Font.PLAIN, 10);
    private static final Logger logger = Logger.getLogger(PlotArea.class.getName());
    private static final BasicStroke DASHED_LINE = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, new float[]{2.0f, 4.0f}, 0.0f);
    private final NumberFormat nf = NumberFormat.getInstance();
    protected BasicStroke solidLine = new BasicStroke(1.0f);
    protected boolean displayDynamicOverLays = false;
    protected boolean draw50percentile = false;
    protected boolean drawChartValues = false;
    protected boolean drawDataString = false;
    protected boolean drawGrid = false;
    protected boolean drawingCurrentValue = false;
    protected boolean drawingFloatingLegend = false;
    protected boolean drawOnlyNonZeroLegendValues = true;
    protected boolean dynamicallyReset = false;
    protected boolean isdrawingAdditionalValueLines = false;
    protected boolean maxSetManually = false;
    protected boolean drawingMessage = false;
    protected boolean drawInsetLabel = false;
    protected Color chartBackgroundColor = Color.white;
    protected Color chartStrokeColor = Color.red;
    protected Color clrHi = new Color(255, 230, 130);
    protected Color clrLo = new Color(255, 0, 0);
    protected Color fontColor = Color.black;
    protected Color gridColor = new Color(146, 146, 146, 125);
    protected Color[] chartForegroundColors = getColorArray(8);
    protected double drawingRatio = 1.0f;
    protected Double legendValueScaler = 1.0d;
    protected Double maxYVal = 0d;
    protected Double requestedMaxYVal = 0d;
    protected Double minYVal = Double.MAX_VALUE;
    protected double ratio = 1.0f;
    protected float chartStrokeThickness = 1.0f;
    protected float spacer = 50.0f;
    protected Font dataFont = new Font("Arial", Font.PLAIN, 11);
    protected Font messageFont = null;
    protected Font insetLabelFont;
    protected int hborder = 0;
    protected int height = 0;
    protected int oldXLocation = 0;
    protected int oldYLocation = 0;
    protected int vborder = 0;
    protected int width = 0;
    protected int xAxisItemCount = 50;
    protected long currentValue = 0;
    protected Point currentMouseLocation = new Point();
    protected String dataString = null;
    protected String title = null;
    protected String insetLabel;
    protected Chart.ChartLocation insetLocation;
    // Data structures holding Chart Data
    protected List<Integer> tickMarkers = null;
    protected List<LinkedList<Long>> dataSets = new ArrayList<>();
    protected LookAndFeel lookAndFeel = LookAndFeel.WHITE;
    protected Map<String, Color> dataLabelToColorMap = Collections.synchronizedMap(new HashMap<>());
    protected Map<String, Integer> additionalValueLines = new HashMap<>(2);
    protected String[] dataLabels = getLabelArray(8);
    private Thread thisThread = null;


    public PlotArea(Color[] baseColor) {
        super();
        this.addComponentListener(this);
        calculateOffsets();
        chartForegroundColors = baseColor;
        thisThread = new Thread(this);
        thisThread.start();
        this.setMinYVal(0d);
    }
    //private final BasicStroke thickDashedLine = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, new float[] { 8.0f, 5.0f }, 0.0f);

    public PlotArea(Color[] baseColor, int initialSize) {
        super();
        this.addComponentListener(this);
        init(initialSize, 1);
        calculateOffsets();
        chartForegroundColors = baseColor;
        thisThread = new Thread(this);
        thisThread.start();
        this.setMinYVal(0d);
    }

    public PlotArea() {
        super();
        this.addComponentListener(this);
        calculateOffsets();
        thisThread = new Thread(this);
        thisThread.start();
        this.setMinYVal(0d);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Color[] getColorArray(int size) {
        return Arrays.copyOf(COLOR_MAP, size);

    }

    public static String[] getLabelArray(int size) {
        return Arrays.copyOf(LABEL_MAP, size);

    }

    private static Color getMixedColor(Color c1, float pct1, Color c2, float pct2) {
        float[] clr1 = c1.getComponents(null);
        float[] clr2 = c2.getComponents(null);
        for (int i = 0; i < clr1.length; i++) {
            clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
        }
        return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
    }

    protected void calculateOffsets() {

        width = getSize().width;
        height = getSize().height;
        spacer = ((float) (this.getSize().width - (hborder * 2)) / (float) (xAxisItemCount - 1));
    }

    protected synchronized Shape createGraphShape(LinkedList<Long> graphData) {

        float xLoc = hborder;
        float yEnd = height - vborder;
        GeneralPath gp = new GeneralPath();
        float yMax = 0;
        try {
            gp.moveTo(xLoc, yEnd);
            float yValue = 0;
            for (long value : graphData) {
                yValue = yEnd - (value * (float) ratio);
                yMax = Math.max(yValue, yMax);
                gp.lineTo(xLoc, yValue);
                xLoc += spacer;
            }
            gp.lineTo(xLoc - spacer, yEnd);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected Exception when creating graph shape", e);
        }
        gp.closePath();
        return gp;
    }

    protected void calcRatios() {
        ratio = ((double) (height - (vborder * 2))) / ((getMaxYVal() == 0) ? 1d : getMaxYVal());
        drawingRatio = ((double) (height - (vborder * 2))) / 75d;  // Where the hell is 75d from?
    }

    protected void drawCurrentValue(Graphics2D g) {
        drawMyString(g, Long.toString(currentValue));

    }

    public void setInsetTitle(String label, Font font, Chart.ChartLocation location) {
        drawInsetLabel = true;
        insetLabel = label;
        insetLabelFont = font;
        insetLocation = location;
    }

    protected void drawInsetTitle(Graphics2D g) {
        int fontSize = (11.0f * drawingRatio < 11) ? 11 : (int) (11.0f * drawingRatio);
        dataFont = (insetLabelFont == null) ? new Font("Arial", Font.PLAIN, fontSize) : insetLabelFont;
        g.setColor(getFontColor());
        FontRenderContext frc = g.getFontRenderContext();
        Rectangle2D bounds = dataFont.getStringBounds(insetLabel, frc);
        LineMetrics metrics = dataFont.getLineMetrics(insetLabel, frc);
        float width = (float) bounds.getWidth(); // The width of our text
        float lineheight = metrics.getHeight(); // Total line height
        float ascent = metrics.getAscent(); // Top of text to baseline
        float x0 = 0;
        float y0 = 0;
        float xOffset = 5;
        float yOffset = 10;
        if (insetLocation == Chart.ChartLocation.TOP_LEFT) {
            y0 = ascent + vborder;
            x0 = xOffset;
        } else if (insetLocation == Chart.ChartLocation.BOTTOM_LEFT) {
            y0 = getHeight() - yOffset;
            x0 = xOffset;
        } else if (insetLocation == Chart.ChartLocation.TOP_RIGHT) {
            y0 = ascent;
            x0 = getWidth() - width - xOffset;
        } else if (insetLocation == Chart.ChartLocation.BOTTOM_RIGHT) {
            y0 = getHeight() - yOffset;
            x0 = getWidth() - width - xOffset;
        }
        g.setFont(insetLabelFont);
        g.drawString(insetLabel, x0, y0);
    }

    protected void drawMyString(Graphics2D g, String string) {
        try {
            int fontSize = (11.0f * drawingRatio < 11) ? 11 : (int) (11.0f * drawingRatio);
            dataFont = (messageFont == null) ? new Font("Arial", Font.PLAIN, fontSize) : messageFont;
            g.setColor(getFontColor());
            FontRenderContext frc = g.getFontRenderContext();
            Rectangle2D bounds = dataFont.getStringBounds(string, frc);
            LineMetrics metrics = dataFont.getLineMetrics(string, frc);
            float width = (float) bounds.getWidth(); // The width of our text
            float lineheight = metrics.getHeight(); // Total line height
            float ascent = metrics.getAscent(); // Top of text to baseline
            float x0 = (float) (getWidth() - width) / 2;
            float y0 = (float) (getHeight() - lineheight) / 2 + ascent;
            g.setFont(dataFont);
            g.drawString(string, x0, y0);
        } catch (NoSuchElementException ignored) {
        }
    }

    private void setColourProfile(Color color) {

        int maxChannel = 0;

        try {
            int r = color.getRed();
            maxChannel = r;
            int g = color.getGreen();
            maxChannel = Math.max(g, maxChannel);
            int b = color.getBlue();
            maxChannel = Math.max(b, maxChannel);
            if (maxChannel == r) {
                clrHi = new Color(setColorRatio(r, 255), setColorRatio(g, 230), setColorRatio(b, 230));
                clrLo = new Color(setColorRatio(r, 100), setColorRatio(g, 10), setColorRatio(b, 10));
            } else if (maxChannel == b) {
                clrHi = new Color(setColorRatio(r, 230), setColorRatio(g, 230), setColorRatio(b, 255));
                clrLo = new Color(setColorRatio(r, 10), setColorRatio(g, 10), setColorRatio(b, 100));
            } else {
                clrHi = new Color(setColorRatio(r, 230), setColorRatio(g, 255), setColorRatio(b, 230));
                clrLo = new Color(setColorRatio(r, 10), setColorRatio(g, 100), setColorRatio(b, 10));
            }

        } catch (Exception e) {
            //Syslog.debug(this,"Exception Setting color profile" + e.getMessage());
        }
    }

    protected void drawGrid(Graphics2D g) {
        int yValue = 0;
        int height = this.getHeight();
        int width = this.getWidth();
        int halfHeight = height / 2;
        int offSetHeight = (height - vborder);
        int offSetWidth = ((width - hborder)) - 1; // + (int)(2 * drawingRatio)) -4;
        g.setColor(gridColor);
        g.drawLine(hborder, vborder, hborder, height - vborder);
        g.drawLine(offSetWidth, vborder, offSetWidth, height - vborder);
        g.setStroke(DASHED_LINE);
        g.drawLine(hborder, vborder, offSetWidth, vborder);
        g.drawLine(hborder, offSetHeight, offSetWidth, offSetHeight);
        if (draw50percentile) {
            g.drawLine(hborder, halfHeight, offSetWidth, halfHeight);
        }
        float yEnd = height - vborder;

        if (tickMarkers != null) {
            for (Integer tickmarker : tickMarkers) {
                g.drawLine(hborder, tickmarker, offSetWidth, tickmarker);
            }
        }

        if (isdrawingAdditionalValueLines) {
            for (Map.Entry<String, Integer> e : additionalValueLines.entrySet()) {
                yValue = (int) (yEnd - ((Integer) e.getValue() * ratio));
                g.drawLine(hborder, yValue, offSetWidth, yValue);
                drawLineText(g, e.getKey(), yValue);
            }
        }
        g.setStroke(solidLine);
    }

    protected void drawCrossHairs(Graphics2D g) {
        int xValue = (int) currentMouseLocation.getX();
        int height = this.getHeight();
        g.setColor(Color.lightGray);
        g.setStroke(DASHED_LINE);
        g.drawLine(xValue, vborder, xValue, height - vborder);
        g.setStroke(solidLine);
    }

    protected Long[] getValuesFromGraph(int xLocation) {
        Long[] value = new Long[dataSets.size()];
        int width = this.getWidth();
        int dataPoints = dataSets.get(0).size();
        int closestValue = (int) (((double) xLocation / (double) width) * dataPoints);
        for (int i = 0; i < dataSets.size(); i++) {
            LinkedList<Long> ll = dataSets.get(i);
            value[i] = ll.get(closestValue);
        }
        return value;
    }

    private TreeMap<KeyValuePair<String, Long>, String> getDataStrings(Long[] dataValues) {

        TreeMap<KeyValuePair<String, Long>, String> result = new TreeMap(new ValueComparator());
        for (int i = 0; i < dataValues.length; i++) {
            result.put(new KeyValuePair(dataLabels[i], dataValues[i]), dataLabels[i]);
        }
        return result;
    }

    private Dimension getLabelBoxSize(Graphics2D g, TreeMap<KeyValuePair<String, Long>, String> dataStrings) {
        Dimension result = new Dimension(0, 0);
        int width = 0;
        try {
            FontRenderContext frc = g.getFontRenderContext();
            for (String text : dataStrings.values()) {
                width = Math.max(width, (int) BOX_FONT.getStringBounds(text, frc).getWidth());
            }
            int height = (int) BOX_FONT.getStringBounds(dataLabels[0], frc).getHeight();
            height = (height * dataStrings.size()) + (4 * dataStrings.size());
            result.setSize(width, height);
        } catch (Exception e) {
            logger.fine("Exception thrown : dataStrings size = " + dataStrings.size());
            for (KeyValuePair<String, Long> kvp : dataStrings.keySet()) {
                logger.fine("k = " + kvp.getKey() + " v = " + kvp.getValue());
            }
            throw e;
        }

        return result;
    }

    private Dimension getValueBoxSize(Graphics2D g, TreeMap<KeyValuePair<String, Long>, String> dataStrings) {
        Dimension result = new Dimension(0, 0);
        int width = 0;
        int numberNonZeroValues = 0;
        FontRenderContext frc = g.getFontRenderContext();
        for (KeyValuePair<String, Long> me : dataStrings.keySet()) {
            if (drawOnlyNonZeroLegendValues) {
                if (me.getValue() <= 0)
                    numberNonZeroValues++;
            }
            width = Math.max(width, (int) BOX_FONT.getStringBounds(nf.format(me.getValue() / legendValueScaler), frc).getWidth());
        }
        int height = (int) BOX_FONT.getStringBounds(dataLabels[0], frc).getHeight();
        int numberOfItemstoDraw = (drawOnlyNonZeroLegendValues) ? (dataStrings.size() - numberNonZeroValues) : dataStrings.size();
        height = (height * numberOfItemstoDraw) + (4 * numberOfItemstoDraw);
        result.setSize(width, height);

        return result;
    }

    protected synchronized void drawBlackTransparentBox(Graphics2D g) { // Not sure if synchronizing is necessary but there's lots of unsafe thread code here.

        int height = this.getHeight();
        int width = this.getWidth();
        int xValue = (int) currentMouseLocation.getX();
        int yValue = (int) currentMouseLocation.getY();

        Long[] dataValues = getValuesFromGraph(xValue);

        TreeMap<KeyValuePair<String, Long>, String> dataStrings = getDataStrings(dataValues);

        g.setFont(BOX_FONT);

        Dimension labelSize = getLabelBoxSize(g, dataStrings);
        Dimension valueSize = getValueBoxSize(g, dataStrings);

        FontRenderContext frc = g.getFontRenderContext();
        int lineHeight = (int) BOX_FONT.getStringBounds(dataLabels[0], frc).getHeight();

        int boxWidth = labelSize.width + valueSize.width + 30;
        int boxHeight = Math.min(labelSize.height, valueSize.height);

        int boxYLoc = yValue;
        int boxLoc;
        if (xValue > (width / 2)) // Cursor is on right hand side of Canvas
            boxLoc = xValue - (boxWidth + 10);
        else
            boxLoc = xValue + 10;
        if (yValue > (height / 2)) // Cursor is on bottom side of Canvas
            boxYLoc = yValue - boxHeight;


        g.setColor(Color.black);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.fill(new Rectangle(boxLoc, boxYLoc, boxWidth, boxHeight));
        g.setStroke(solidLine);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.setColor(Color.white);
        g.setFont(BOX_FONT);


        int textGap = 4;
        int yPos = boxYLoc + lineHeight;
        for (KeyValuePair<String, Long> me : dataStrings.keySet()) {
            if (drawOnlyNonZeroLegendValues) {
                if (me.getValue() > 0) {
                    g.drawString(me.getKey(), boxLoc + 5, yPos);
                    drawLegendColorSquare(g, dataLabelToColorMap.get(me.getKey()), new Point(boxLoc + labelSize.width + 10, yPos - lineHeight + 3));
                    g.drawString(nf.format(me.getValue() / legendValueScaler), boxLoc + labelSize.width + 25, yPos);
                    yPos = yPos + lineHeight + textGap;
                }
            } else {
                g.drawString(me.getKey(), boxLoc + 5, yPos);
                drawLegendColorSquare(g, dataLabelToColorMap.get(me.getKey()), new Point(boxLoc + labelSize.width + 10, yPos - lineHeight + 3));
                g.drawString(nf.format(me.getValue() / legendValueScaler), boxLoc + labelSize.width + 25, yPos);
                yPos = yPos + lineHeight + textGap;
            }
        }
    }

    protected void drawLegendColorSquare(Graphics2D g2d, Color color, Point position) {
        Paint currentPaint = g2d.getPaint();
        Color color2 = color.darker().darker();
        int h = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, color, 0, h, color2);

        g2d.setPaint(gp);
        g2d.fillRect(position.x, position.y, 10, 10);
        g2d.setPaint(currentPaint);

    }

    protected void drawLineText(Graphics2D g, String text, int yValue) {
        try {
            int fontSize = (6.0f * drawingRatio < 6) ? 6 : (int) (6.0f * drawingRatio);
            dataFont = new Font("Arial", Font.PLAIN, fontSize);
            g.setColor(gridColor);
            float x0 = 5;
            float y0 = yValue - 4; // - ascent;
            g.setFont(dataFont);
            g.drawString(text, x0, y0);
        } catch (NoSuchElementException ignored) {
        }
    }

    private int setColorRatio(int value, int diff) {
        return maxrgb((int) (((float) diff / 255.0f) * value));
    }

    private int maxrgb(int rgb) {
        rgb = Math.min(rgb, 255);
        return rgb;
    }

    public void init(int xAxisItemCount, int dataSetCount, Color[] colors, String[] labels) {
        this.setChartForegroundColors(colors);
        this.setDataLabels(labels);
        init(xAxisItemCount, dataSetCount);
    }

    public void init(int xAxisItemCount, int dataSetCount, Color[] colors) {
        this.setChartForegroundColors(colors);
        init(xAxisItemCount, dataSetCount);
    }

    public void init(int xAxisItemCount) {
        init(xAxisItemCount, 1);
    }

    public void init(int xAxisItemCount, int dataSetCount) {
        this.xAxisItemCount = xAxisItemCount;
        for (int i = 0; i < dataSetCount; i++) {
            LinkedList<Long> ll = new LinkedList<>();
            dataSets.add(ll);
            for (int x = 0; x < this.xAxisItemCount; x++) {
                ll.add(0L);
            }
        }
        setMaxYVal(0D);
    }

    public void addNewDataSet() {

        LinkedList<Long> ll = new LinkedList<>();
        dataSets.add(ll);
        for (int x = 0; x < this.xAxisItemCount; x++) {
            ll.add(0L);
        }
        logger.fine("addNewDataSet Called. DataSets size is now = " + dataSets.size());
    }

    public void addNewSeries(String label) {
        if (!dataLabelToColorMap.containsKey(label)) {
            addDataLabel(label);
            addNewDataSet();
        }
    }

    private BufferedImage createClipImage(Shape s, Graphics2D g) {
        GraphicsConfiguration gc = g.getDeviceConfiguration();
        BufferedImage img = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        Graphics2D g2 = img.createGraphics();
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, width, height);
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(s);
        g2.dispose();

        return img;
    }

    public void run() {

        try {
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "Exception on PrettyChart() thread ", ex);
        } finally {
            logger.finest("Leaving Pretty Chart");
        }

    }

    public void setChartStrokeThickness(float st) {
        chartStrokeThickness = st;
    }

    public BufferedImage colourGraph(Graphics g, Shape shape, Color color) {
        setColourProfile(color);
        BufferedImage graphImage = createClipImage(shape, (Graphics2D) g);
        Graphics2D g2 = graphImage.createGraphics();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            BasicStroke stroke = new BasicStroke(chartStrokeThickness * (float) drawingRatio);
            g2.setColor(clrHi);
            g2.setStroke(stroke);
            g2.draw(shape);
            g2.setPaint(new GradientPaint(0, 0, clrHi, 0, height, clrLo));
            g2.fill(shape);
            g2.dispose();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Fail!!", e);
        }
        return graphImage;
    }

    protected void drawDecoration(Graphics2D g) {
        if (isDrawGrid()) {
            drawGrid(g);
        }
        if (isDrawingCurrentValue()) {
            drawCurrentValue(g);
        }
        if (isDrawDataString()) {
            drawMyString(g, dataString);
        }
        if (drawInsetLabel) {
            drawInsetTitle(g);
        }
        if (isDrawingFloatingLegend()) {
            if (isDisplayDynamicOverLays()) {
                drawCrossHairs(g);
                drawBlackTransparentBox(g);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        try {
            paintChart(g);
        } catch (Exception e) {
            logger.log(Level.FINE, "Unable to draw the chart correctly", e);
        }
    }

    public void setLookandFeel(LookAndFeel lookAndFeel) {

        this.lookAndFeel = lookAndFeel;
        switch (lookAndFeel) {
            case BLACK: {
                setBackground(Color.black);
                setChartBackgroundColor(Color.black);
                setFontColor(new Color(45, 247, 14));
                setChartStrokeColor(new Color(45, 247, 14));
            }
            break;
            case WHITE: {
                setBackground(Color.white);
                setChartBackgroundColor(Color.white);
                setFontColor(Color.darkGray);
                setChartStrokeColor(Color.red);
            }
        }
    }

    public void componentResized(ComponentEvent componentEvent) {
        calculateOffsets();
    }

    public void componentMoved(ComponentEvent componentEvent) {
    }

    public void componentShown(ComponentEvent componentEvent) {
    }

    public void componentHidden(ComponentEvent componentEvent) {
    }

    public Color getChartStrokeColor() {
        return chartStrokeColor;
    }

    public void setChartStrokeColor(Color color) {
        this.chartStrokeColor = color;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public boolean isMaxSetManually() {
        return maxSetManually;
    }

    public void setMaxSetManually(boolean maxSetManually) {
        this.maxSetManually = maxSetManually;
    }

    public int getXAxisItemCount() {
        return xAxisItemCount;
    }

    public void setXAxisItemCount(int itemsToDisplay) {
        this.xAxisItemCount = itemsToDisplay;
    }

    public void setMaxYValue(long maxYValue) {
        requestedMaxYVal = (double) maxYValue;
    }

    public void setMaxYValue(double maxYValue) {
        requestedMaxYVal = maxYValue;
        if (requestedMaxYVal < getMinYVal()) {
            requestedMaxYVal = getMinYVal();
            setMaxYVal(getMinYVal());
        }
    }

    public Double getRequestedMaxYVal() {
        return requestedMaxYVal;
    }

    public Double getMinYVal() {
        return minYVal;
    }

    public void setMinYVal(Double minYVal) {
        this.minYVal = minYVal;
        if (minYVal > getRequestedMaxYVal()) {
            requestedMaxYVal = getMinYVal();

        }
        setMaxYVal(minYVal);

    }

    public Double getMaxYVal() {
        return maxYVal;
    }

    protected void setMaxYVal(Double maxYVal) {
        this.maxYVal = maxYVal;
    }

    protected void setMaxYVal(Long maxYVal) {
        this.maxYVal = maxYVal.doubleValue();
    }

    public boolean isDrawGrid() {
        return drawGrid;
    }

    public void setDrawGrid(boolean drawGrid) {
        this.drawGrid = drawGrid;
    }

    public boolean isDynamicallyReset() {
        return dynamicallyReset;
    }

    public void setDynamicallyReset(boolean dynamicallyReset) {
        logger.finest("Dynamically resizing chart set to " + dynamicallyReset);
        this.dynamicallyReset = dynamicallyReset;
    }

    public Color getGridColor() {
        return gridColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    public Font getDataFont() {
        return dataFont;
    }

    public void setDataFont(Font dataFont) {
        this.dataFont = dataFont;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color[] getChartForegroundColors() {
        return chartForegroundColors;
    }

    public synchronized void setChartForegroundColors(Color[] chartColor) {
        this.chartForegroundColors = chartColor;
        dataLabelToColorMap = new HashMap();
        int counter = 0;
        for (Color color : chartColor) {
            dataLabelToColorMap.put(dataLabels[counter], color);
        }
    }

    public boolean isDrawingCurrentValue() {
        return drawingCurrentValue;
    }

    public void setDrawingCurrentValue(boolean drawCurrentValue) {
        this.drawingCurrentValue = drawCurrentValue;
    }

    public List<LinkedList<Long>> getDataSets() {
        return dataSets;
    }

    public void setDataSets(List<LinkedList<Long>> data) {
        this.dataSets = data;
    }

    public Color getChartBackgroundColor() {
        return chartBackgroundColor;
    }

    public void setChartBackgroundColor(Color chartBackgroundColor) {
        this.chartBackgroundColor = chartBackgroundColor;
    }

    public void setAdditionalValueLines(Map<String, Integer> additionalValueLines) {
        this.additionalValueLines = additionalValueLines;
        this.isdrawingAdditionalValueLines = true;
    }

    public void setDrawingDataString(boolean b) {
        drawDataString = b;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        drawDataString = true;
        drawingCurrentValue = false;
        this.dataString = dataString;
        repaint();
    }

    public boolean isDrawDataString() {
        return drawDataString;
    }

    private void jbInit() throws Exception {
        logger.finest("jbInit invoked...");
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void mouseEntered(MouseEvent e) {
        displayDynamicOverLays = true;
        repaint();
    }

    public void mouseExited(MouseEvent e) {
        displayDynamicOverLays = false;
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        currentMouseLocation = e.getPoint();
        if ((oldXLocation >= (e.getLocationOnScreen().x + spacer)) || (oldXLocation <= (e.getLocationOnScreen().x - spacer)) || (oldYLocation >= (e.getLocationOnScreen().y + 5)) || (oldYLocation <= (e.getLocationOnScreen().y - 5))) {
            oldXLocation = e.getLocationOnScreen().x;
            oldYLocation = e.getLocationOnScreen().y;
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public boolean isDrawChartValues() {
        return drawChartValues;
    }

    public void setDrawChartValues(boolean drawChartValues) {
        this.drawChartValues = drawChartValues;
    }

    public boolean isDrawingFloatingLegend() {
        return drawingFloatingLegend;
    }

    public void setDrawingFloatingLegend(boolean drawCrossHairs) {
        this.drawingFloatingLegend = drawCrossHairs;
    }

    public boolean isDisplayDynamicOverLays() {
        return displayDynamicOverLays;
    }

    public void setDisplayDynamicOverLays(boolean displayDynamicOverLays) {
        this.displayDynamicOverLays = displayDynamicOverLays;
    }

    public synchronized void addDataLabel(String dataLabel) {
        if (!dataLabelToColorMap.containsKey(dataLabel)) {
            Color[] tempArray = new Color[chartForegroundColors.length + 1];
            System.arraycopy(chartForegroundColors, 0, tempArray, 0, chartForegroundColors.length);
            String[] tempStringArray = new String[dataLabels.length + 1];
            System.arraycopy(dataLabels, 0, tempStringArray, 0, dataLabels.length);
            dataLabels = tempStringArray;
            chartForegroundColors = tempArray;
            chartForegroundColors[dataLabelToColorMap.size()] = COLOR_MAP[dataLabelToColorMap.size()];
            dataLabels[dataLabelToColorMap.size()] = dataLabel;
            dataLabelToColorMap.put(dataLabel, COLOR_MAP[dataLabelToColorMap.size()]);
        }

    }

    public String[] getDataLabels() {
        return dataLabels;
    }

    public synchronized void setDataLabels(String[] dataLabels) {
        this.dataLabels = dataLabels;
        dataLabelToColorMap = new HashMap<>();
        int counter = 0;
        for (String label : dataLabels) {
            dataLabelToColorMap.put(label, chartForegroundColors[counter++]);
        }
    }

    public int getHborder() {
        return hborder;
    }

    public void setHborder(int hborder) {
        this.hborder = hborder;
    }

    public int getVborder() {
        return vborder;
    }

    public void setVborder(int vborder) {
        this.vborder = vborder;
    }

    public boolean isDraw50percentile() {
        return draw50percentile;
    }

    public void setDraw50percentile(boolean draw50percentile) {
        this.draw50percentile = draw50percentile;
    }

    public boolean isIsdrawingAdditionalValueLines() {
        return isdrawingAdditionalValueLines;
    }

    public void setIsdrawingAdditionalValueLines(boolean drawAdditionalValueLines) {
        this.isdrawingAdditionalValueLines = drawAdditionalValueLines;
    }

    public void setTickMarkers(List<Integer> tickMarkers) {
        this.tickMarkers = tickMarkers;
    }

    public boolean isDrawOnlyNonZeroLegendValues() {
        return drawOnlyNonZeroLegendValues;
    }

    public void setDrawOnlyNonZeroLegendValues(boolean drawOnlyNonZeroLegendValues) {
        this.drawOnlyNonZeroLegendValues = drawOnlyNonZeroLegendValues;
    }

    public Double getLegendValueScaler() {
        return legendValueScaler;
    }

    public void setLegendValueScaler(Double legendValueScaler) {
        this.legendValueScaler = legendValueScaler;
    }

    public boolean isDrawingMessage() {
        return drawingMessage;
    }

    public void setDisplayMessageFont(Font f) {
        messageFont = f;
    }

    public void dumpChartData() {
        System.out.print("dataLabels : \t\t\t[");
        Arrays.stream(dataLabels).forEach(s -> System.out.printf("\"%s\", ", s));
        System.out.print("]\ndataLabelToColorMap : \t[");
        dataLabelToColorMap.forEach((k, v) -> System.out.printf("[\"%s\",%s], ", k, v));
        System.out.print("]\ndataSets : [\n");
        dataSets.forEach(s -> {
            System.out.printf("Size = %s : ", s.size());
            System.out.printf(" %s, \n", s);
        });
        System.out.println("]");
    }

    public enum LookAndFeel {
        BLACK,
        WHITE;

    }


}

