package com.vynaloze.pewpewpew;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vynaloze.pewpewpew.gui.Bullet;
import com.vynaloze.pewpewpew.gui.Meteors;
import com.vynaloze.pewpewpew.gui.SpaceShip;
import com.vynaloze.pewpewpew.logic.VolumeMeter;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements Runnable {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final VolumeMeter volumeMeter = new VolumeMeter();

    private final Context context;
    private final SurfaceHolder holder;

    private final int screenX;
    private final int screenY;
    private int score = 0;
    boolean lost = false;

    private SpaceShip ship;
    private final Bullet[] playerBullets = new Bullet[10];
    private final Meteors[] meteors = new Meteors[10];

    private final int FPS = 50;
    private int cyclesUntilNextShootPossible = 25;  //you can shoot 2 times per second


    public GameView(Context context, int x, int y) {
        super(context);
        this.context = context;
        this.screenX = x;
        this.screenY = y;
        this.holder = getHolder();
        initialiseGame();
    }

    private void initialiseGame() {
        ship = new SpaceShip(context, screenX, screenY);
        for (int i = 0; i < playerBullets.length; i++) {
            playerBullets[i] = new Bullet();
        }
        for (int i = 0; i < meteors.length; i++) {
            meteors[i] = new Meteors(context, screenX);
        }
    }

    @Override
    public void run() {
        // logic part
        update();
        // drawing part
        draw();
    }

    private void update() {
        // move the player's ship
        ship.refresh(FPS);

        // move the meteors
        Random random = new Random();
        int n = random.nextInt(50);
        for (Meteors meteor : meteors) {
            if (meteor.isActive()) {
                meteor.refresh(FPS);
            }
            if (n == 0) {
                boolean succeed = meteor.fly();
                if (succeed) break;
            }
        }

        // did a meteor bump into the edge of the screen
        for (Meteors meteor : meteors) {
            if (meteor.isActive()) {
                if (meteor.getPosY() > screenY) {
                    lost = true;
                }
            }
        }

        // update the players bullets
        cyclesUntilNextShootPossible--;
        for (Bullet bullet : playerBullets) {
            if (bullet.isActive()) {
                bullet.refresh(FPS);
            }
            if (cyclesUntilNextShootPossible < 0) {
                boolean succeed = bullet.shoot(ship.getPosX() + ship.getSizeX() / 2, ship.getPosY(), volumeMeter.getAmp());
                if (succeed) {
                    cyclesUntilNextShootPossible = 25;
                    break;
                }
            }
        }

        // has the player's bullet hit the top of the screen
        for (Bullet bullet : playerBullets) {
            if (bullet.getY() < 0) {
                bullet.setActive(false);
            }
        }

        // has the player's bullet hit a meteor
        for (Bullet bullet : playerBullets) {
            if (bullet.isActive()) {
                for (Meteors meteor : meteors) {
                    if (meteor.isActive()) {
                        if (RectF.intersects(bullet.getRectF(), meteor.getRectF())) {
                            bullet.setActive(false);
                            meteor.lowerHp(bullet.getSize());
                            if (meteor.getHp() <= 0) {
                                score += 10;
                                meteor.setActive(false);
                            }
                        }
                    }
                }
            }
        }

        // has a meteor hit the player ship
        for (Meteors meteor : meteors) {
            if (meteor.isActive()) {
                if (RectF.intersects(ship.getRectF(), meteor.getRectF())) {
                    lost = true;
                }
            }
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            Paint textPaint = new Paint(Color.argb(255, 204, 0, 0));
            textPaint.setTextSize(25);

            // draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // draw the player spaceship
            canvas.drawBitmap(ship.getBitmap(), ship.getPosX(), screenY - 50, null);

            // draw the meteors
            for (Meteors meteor : meteors) {
                if (meteor.isActive()) {
                    canvas.drawRect(meteor.getRectF(), new Paint(R.color.red)); // not visible tho
                    canvas.drawBitmap(meteor.getBitmap(), meteor.getPosX(), meteor.getPosY(), null);
                }
            }

            // draw the players bullets
            for (Bullet bullet : playerBullets) {
                if (bullet.isActive()) {
                    canvas.drawRect(bullet.getRectF(), new Paint(Color.argb(255, 0, 255, 0)));
                }
            }

            // draw the score and check if the player has lost
            canvas.drawText("Score: " + score, 10, 30, textPaint);
            if (lost) {
                textPaint.setTextSize(50);
                canvas.drawText("YOU LOST... with score: " + score, 10, screenY / 2, textPaint);
                pause();
            }

            // draw everything to the screen
            holder.unlockCanvasAndPost(canvas);
        }
    }


    public void resume() {
        scheduler.scheduleAtFixedRate(this, 0, 20, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(volumeMeter, 0, 20, TimeUnit.MILLISECONDS);
    }

    public void pause() {
        scheduler.shutdownNow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                if (motionEvent.getX() < screenX / 2) {
                    ship.setStatus(SpaceShip.Status.TO_LEFT);
                } else {
                    ship.setStatus(SpaceShip.Status.TO_RIGHT);
                }
                break;

            case MotionEvent.ACTION_UP:
                ship.setStatus(SpaceShip.Status.STOP);
                break;
        }
        return true;
    }
}
