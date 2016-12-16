package com.vynaloze.pewpewpew.gui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.preference.PreferenceManager;

import com.vynaloze.pewpewpew.R;

public class SpaceShip {

    private final RectF rectF;    //to handle collisions
    private Bitmap bitmap;

    private float posX;        //counting from the left side of the screen
    private final float posY;
    private final float sizeX;
    private final float sizeY;
    private float speed;

    private Status status;


    public SpaceShip(Context context, int screenSizeX, int screenSizeY) {
        rectF = new RectF();

        sizeX = screenSizeX / 7;
        sizeY = screenSizeY / 10;

        float x = screenSizeX / 2;
        float y = screenSizeY - 50;
        posX = x;
        posY = y;
        rectF.bottom = y;
        rectF.top = y + sizeY;
        rectF.left = x;
        rectF.right = x + sizeX;

        getAndApplyPreferences(context);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) sizeX, (int) sizeY, true);

        status = Status.STOP;
    }

    private void getAndApplyPreferences(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int imageID = preferences.getInt("SHIP_IMAGE_ID", R.drawable.ship1);
        int speed = preferences.getInt("SPEED", 300);

        bitmap = BitmapFactory.decodeResource(context.getResources(), imageID);
        this.speed = speed;
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

    public float getSizeX() {
        return sizeX;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void refresh(float fps) {
        // check if ship is not at the edge
        if (posX < -sizeX) {
            posX = -sizeX;
        }
        if (posX + sizeX > 8 * sizeX) {
            posX = 7 * sizeX;
        }
        // move ship
        if (status == Status.TO_LEFT) {
            posX -= (speed / fps);
        }
        if (status == Status.TO_RIGHT) {
            posX += (speed / fps);
        }
        rectF.left = posX;
        rectF.right = posX + sizeX;
    }

    public enum Status {TO_LEFT, TO_RIGHT, STOP}
}
