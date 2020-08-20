package com.dom.util.graphs;

import com.dom.util.layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GraphTestFrame extends JFrame implements Runnable {

    private static final Logger logger = Logger.getLogger(GraphTestFrame.class.getName());
    Chart chart2 = new Chart(Chart.ChartType.AREACHART, 120, 12, new Color[]{new Color(4, 203, 0), // CPU Time : Green
            new Color(204, 255, 207), // Scheduler : Light Green
            new Color(0, 74, 231), // User I/O : Blue
            new Color(0, 143, 234), // System I/O : Cyan
            new Color(142, 26, 3), // Concurrency : Dark Red
            new Color(192, 41, 2), // Application : Red
            new Color(226, 105, 0), // Commit : Orange
            new Color(91, 69, 9), // Configuration : Brown
            new Color(115, 116, 85), // Administrative : Light Brown
            new Color(162, 147, 116), // Network : Gray Brown
            new Color(196, 182, 156), // Queuing : Light Gray Brown
            new Color(240, 110, 170)}, // Other : Pink
            new String[]{"CPU Time", // CPU Time : Green
                    "Scheduler", // Scheduler : Light Green
                    "User I/O", // User I/O : Blue
                    "System I/O", // System I/O : Cyan
                    "Concurrency", // Concurrency : Dark Red
                    "Application", // Application : Red
                    "Commit", // Commit : Orange
                    "Configuration", // Configuration : Brown
                    "Administrative", // Administrative : Light Brown
                    "Network", // Network : Gray Brown
                    "Queuing", // Queuing : Light Gray Brown
                    "Other"}, // Other : Pink
            PlotArea.LookAndFeel.BLACK);
    Random r = new Random();
    private Chart chart = new Chart(Chart.ChartType.LINECHART,
            120,
            2,
            new Color[]{Color.green, Color.red},
            new String[]{"Transactions Per Minute", "Latency"}, PlotArea.LookAndFeel.WHITE);
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JPanel chartParentPanel = new JPanel();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout(0);

    public GraphTestFrame() throws Exception {
        InitialisePanel();
        chart.setDrawingFloatingLegend(true);
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("An Important Limit", 25);
        chart.setAdditionalValueLines(hm);
        chart.setDrawingGrid(true);
        chart.setMinYValue(100D);
        chart.setDrawingXAxisTime(true);
        chart.setDrawingCurrentValue(true);
        chart.setDrawing50percentile(true);
        chart.getYAxis().setFont(new Font("Helvetica", Font.BOLD, 18));
        chart.setDrawingYAxisLabel(true);
        chart.setYAxisVisible(true);
        chart.setYAxisLabel("Some Important Values");


        chart2.setDrawingFloatingLegend(true);
        chart2.setDrawingYAxisLabel(true);
        chart2.setDrawingXAxisTime(true);
        chart2.setDrawingGrid(true);
        chart2.setYAxisVisible(true);
        chart2.setYAxisLabel("Sessions");

        Thread thisThread = new Thread(this);
        thisThread.start();

    }

    public static void main(String[] args) {
        try {
            GraphTestFrame graphTestFrame = new GraphTestFrame();
            graphTestFrame.setVisible(true);
            graphTestFrame.setSize(800, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Long[] data1 = new Long[]{20L, 30L};
        Long[] data2 = new Long[]{0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};

        while (true) {
            try {
                data1[0] = data1[0] + randomLong(-1, 2);
                data1[1] = data1[1] + randomLong(-1, 2);
                chart.putData(data1);
                for (int i = 0; i < data2.length; i++)
                    data2[i] = data2[i] + randomLong(0, 2);
                chart2.putData(data2);
                Thread.sleep(1000);

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Boom!!!", e);
            }
        }
    }

    public void InitialisePanel() {
        chartParentPanel.setBackground(Color.black);
        chartParentPanel.setLayout(verticalFlowLayout1);
        jScrollPane1.getViewport().add(chartParentPanel, null);
        this.getContentPane().add(jScrollPane1, null);
        verticalFlowLayout1.setVgap(0);
        verticalFlowLayout1.setHgap(0);
        verticalFlowLayout1.setMaximizeOtherDimension(true);
        chart.setPreferredSize(new Dimension(790, 300));
        chart2.setPreferredSize(new Dimension(790, 300));
        chartParentPanel.add(chart);
        chartParentPanel.add(chart2);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });
    }

    public long randomLong(long s, long e) {

        long result = 0;
        if ((e - s) > 0) {
            result = (Math.abs(r.nextLong()) % (e - s)) + s;
        } else if ((e - s) == 0) {
            result = e;
        }
        return result;
    }
}
