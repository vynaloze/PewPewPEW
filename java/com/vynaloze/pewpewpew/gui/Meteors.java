package com.vynaloze.pewpewpew.gui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import com.vynaloze.pewpewpew.R;
import com.vynaloze.pewpewpew.logic.Size;

import java.util.Random;

public class Meteors {

    private final RectF rectF;    // to handle collisions
    private Bitmap bitmap;
    private final Context context;

    private float posX;        //counting from the left side of the screen
    private float posY;
    private float sizeX, sizeY;

    private final float screenX;      // screen size

    private float speed;
    private int hp;
    private boolean isActive;

    private final Random random = new Random();

    public Meteors(Context context, int screenSizeX) {
        rectF = new RectF();
        this.screenX = screenSizeX;
        this.context = context;
    }

    public boolean fly() {
        if (isActive) return false;

        // 50% chance to spawn small meteor, 30% - medium and 20 - huge
        int n = random.nextInt(100);
        if (n < 50) {
            sizeX = 50;
            sizeY = 50;
            speed = 50;
            hp = 1;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aliens);
        } else if (n < 80) {
            sizeX = 75;
            sizeY = 75;
            speed = 30;
            hp = 2;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dishes);
        } else {
            sizeX = 125;
            sizeY = 125;
            speed = 15;
            hp = 3;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.christmas);
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, (int) sizeX, (int) sizeY, true);

        // randomly choose a position on the screen
        posX = random.nextInt((int) (screenX - sizeX));
        posY = 0;
        isActive = true;
        return true;
    }

    public void refresh(float fps) {
        posY += (speed / fps);
        rectF.left = posX;
        rectF.right = posX + sizeY;
        rectF.top = posY;
        rectF.bottom = posY + sizeX;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public RectF getRectF() {
        return rectF;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public int getHp() {
        return hp;
    }

    public void lowerHp(Size size) {
        switch (size) {
            case SMALL:
                hp--;
                break;
            case MID:
                hp -= 2;
                break;
            case HUGE:
                hp -= 3;
        }
    }
}
