package com.vynaloze.pewpewpew.gui;


import android.graphics.RectF;

import com.vynaloze.pewpewpew.logic.Size;

public class Bullet {

    private final RectF rectF;

    private float x;
    private float y;
    private final float speed = 300;
    private int width;
    private int height;
    private Size size;

    private boolean isActive;


    public Bullet() {
        isActive = false;
        rectF = new RectF();
    }

    public boolean shoot(float srcX, float srcY, double currentAmp) {
        if (isActive) return false;

        setProperSize(currentAmp);

        if (size == Size.NONE) return false;

        x = srcX;
        y = srcY;

        isActive = true;
        return true;
    }

    public void refresh(float fps) {
        y -= (speed / fps);
        rectF.left = x;
        rectF.right = x + width;
        rectF.top = y;
        rectF.bottom = y + height;
    }

    public RectF getRectF() {
        return rectF;
    }

    public boolean isActive() {
        return isActive;
    }

    public float getY() {
        return y;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private void setProperSize(double amp) {
        if (amp > 500) {
            size = Size.HUGE;
            height = 40;
            width = 40;
        } else if (amp > 200) {
            size = Size.MID;
            height = 20;
            width = 20;
        } else if (amp > 20) {
            size = Size.SMALL;
            height = 10;
            width = 10;
        } else {
            size = Size.NONE;
        }
    }

    public Size getSize() {
        return size;
    }
}
