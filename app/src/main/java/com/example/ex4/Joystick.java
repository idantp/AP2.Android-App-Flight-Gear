package com.example.ex4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Joystick extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private JoystickListener joystickListener;
    //small circle radius.
    private float smallRadius;
    //bigger radius.
    private float bigRadius;
    //center point.
    private float xAxisCenter;
    private float yAxisCenter;

    //CCTOR
    public Joystick(Context context) {
        super(context);
        // surface will notify this joystick once is created/changed/destroyed
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener) {
            joystickListener = (JoystickListener) context;
        }
    }

    //CCTOR
    public Joystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener) {
            joystickListener = (JoystickListener) context;
        }
    }

    //CCTOR
    public Joystick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if (context instanceof JoystickListener) {
            joystickListener = (JoystickListener) context;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //once created, set the dimensions and draw the joystick.
        initializeDimensions();
        draw(this.xAxisCenter, this.yAxisCenter);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    //Initializes the dimensions of the joystick.
    private void initializeDimensions() {
        this.bigRadius = (float) Math.min(getWidth(), getHeight()) / 3;
        this.smallRadius = (float) Math.min(getWidth(), getHeight()) / 7;
        this.xAxisCenter = (float) getWidth() / 2;
        this.yAxisCenter = (float) getHeight() / 2;
    }

    /**
     * Draws the joystick to the screen.
     * @param x - Where the center x axis is
     * @param y - where the center y axis is.
     */
    private void draw(float x, float y) {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = this.getHolder().lockCanvas();
            Paint color = new Paint();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            color.setColor(Color.BLUE);
            canvas.drawCircle(this.xAxisCenter, this.yAxisCenter, this.bigRadius, color);
            color.setColor(Color.BLACK);
            canvas.drawCircle(this.xAxisCenter, this.yAxisCenter, this.smallRadius, color);
            color.setColor(Color.WHITE);
            canvas.drawCircle(x, y, this.smallRadius, color);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //Make sure that the touch listener accepts touches done on this surface.
        float joystickPlacement;
        if (view.equals(this)) {
            //any action except lifting the finger off of the screen.
            if (motionEvent.getAction() != motionEvent.ACTION_UP) {
                joystickPlacement = (float) Math.sqrt((Math.pow(motionEvent.getX() -
                        this.xAxisCenter, 2)) + Math.pow(motionEvent.getY() - this.yAxisCenter, 2));
                //it is inside of the big circle or on it's perimeter.
                if (joystickPlacement < this.bigRadius) {
                    draw(motionEvent.getX(), motionEvent.getY());
                    joystickListener.onJoystickMoved(
                            (motionEvent.getX() - xAxisCenter) / bigRadius,
                            (motionEvent.getY() - yAxisCenter) / bigRadius);
                //attempted to exit the perimeter of the big circle.
                } else {
                    float distanceRatio = bigRadius / joystickPlacement;
                    float fixedX = this.xAxisCenter +
                            (distanceRatio * (motionEvent.getX() - this.xAxisCenter));
                    float fixedY = this.yAxisCenter +
                            (distanceRatio * (motionEvent.getY() - this.yAxisCenter));
                    draw(fixedX, fixedY);
                    joystickListener.onJoystickMoved(
                            (fixedX - xAxisCenter) / bigRadius,
                            (fixedY - yAxisCenter) / bigRadius);
                }
                //finger is lifted, return to the center position.
            } else {

                draw(this.xAxisCenter, this.yAxisCenter);
                joystickListener.onJoystickMoved(0, 0);
            }

        }
        return true;
    }

    //Listener interface.
    public interface JoystickListener {
        void onJoystickMoved(float xPercent, float yPercent);
    }
}
