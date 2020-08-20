package com.dom.util.graphs;

import java.util.Comparator;

public class ValueComparator implements Comparator {
    public ValueComparator() {
        super();
    }

    public int compare(Object value1, Object value2) {
        int result = 0;

        KeyValuePair<String, Long> c1 = (KeyValuePair<String, Long>) value1;
        KeyValuePair<String, Long> c2 = (KeyValuePair<String, Long>) value2;

        if (c1.getValue() < c2.getValue())
            result = 1;
        else if (c1.getValue() > c2.getValue())
            result = -1;
        return result;
    }
}
