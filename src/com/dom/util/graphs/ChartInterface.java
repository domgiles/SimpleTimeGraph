package com.dom.util.graphs;

import java.awt.*;

public interface ChartInterface {
    public void putData(Integer[] dataArray) throws Exception;

    public void putData(Long[] dataArray) throws Exception;

    public void paintChart(Graphics g) throws Exception;
}
