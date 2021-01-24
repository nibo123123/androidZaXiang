package com.zhuanghongji.mpandroidchart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by zhuanghongji on 2015/12/15.
 */
public class MyValueFormatter implements ValueFormatter {

    // import java.text.DecimalFormat;
    private DecimalFormat mFormat;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + "$"; // e.g. append a dollar-sign
    }
}
