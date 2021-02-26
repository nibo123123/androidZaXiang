package com.example.slidephoto.common;

/**
 * Created by CHENCJ on 2021/2/25.
 */

public class PositionInfo {
    public int left;     //拼图缺块离整张图片左边距离
    public int top;      //拼图缺块离整张图片上方距离

    public PositionInfo(int left, int top) {
        this.left = left;
        this.top = top;
    }
}
