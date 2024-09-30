package com.example.retrogame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameView extends View {
    Bitmap background, ground, persona;
    Rect rectBackground, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int points = 0;
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    float personaX, pernosaY;
    float oldX;
    float oldXpersonaX;
    ArrayList<Fruit> fruits;
    ArrayList<Splash> splashes;

    public GameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.inicialscreen1);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.grown);
        persona = BitmapFactory.decodeResource(getResources(), R.drawable.personagem);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBackground = new Rect(0,0, dWidth,dHeight);
        rectGround = new Rect(0, dHeight - ground.getHeight(), dWidth, dHeight);
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        textPaint.setColor(Color.rgb(255, 165, 0));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.bricksans));
        healthPaint.setColor(Color.GREEN);
        random = new Random();
        personaX = dWidth / 2 - persona.getWidth() / 2;
        pernosaY = dHeight - ground.getHeight() - persona.getHeight();
        fruits = new ArrayList<>();
        splashes = new ArrayList<>();

        for (int i=0; i<3; i++){
            Fruit fruit = new Fruit(context);
            fruits.add(fruit);

        }
        handler = new Handler() {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };


    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(ground, null, rectGround, null);
        canvas.drawBitmap(persona, personaX, pernosaY, null);
        for (int i=0; i<fruits.size(); i++){
            canvas.drawBitmap(fruits.get(i).getFruit(fruits.get(i).fruitFrame), fruits.get(i).fruitX, fruits.get(i).fruitY, null);
            fruits.get(i).fruitFrame++;
            if (fruits.get(i).fruitFrame > 2){
                fruits.get(i).fruitFrame =0;

            }
            fruits.get(i).fruitY += fruits.get(i).fruitVelocity;
            if (fruits.get(i).fruitY + fruits.get(i).getFruitHeigth() >= dHeight - ground.getHeight()){
                points += 10;
                Splash splash = new Splash(context);
                splash.splashX = fruits.get(i).fruitX;
                splash.splashY = fruits.get(i).fruitY;
                splashes.add(splash);
                fruits.get(i).resetPosition();

            }

        }
        for (int i=0; i < fruits.size(); i++){
            if (fruits.get(i).fruitX + fruits.get(i).getFruitWidth() >= personaX
            && fruits.get(i).fruitX <= personaX + persona.getWidth()
            && fruits.get(i).fruitY + fruits.get(i).getFruitWidth() >= pernosaY
            && fruits.get(i).fruitY + fruits.get(i).getFruitWidth() <= pernosaY + persona.getHeight()){
                life --;
                fruits.get(i).resetPosition();
                if (life == 0){
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("Points", points);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }

        }

        for (int i=0; i < splashes.size(); i++){
            canvas.drawBitmap(splashes.get(i).getSplash(splashes.get(i).splashFrame), splashes.get(i).splashX,

                    splashes.get(i).splashY, null);
            splashes.get(i).splashFrame ++;
            if (splashes.get(i).splashFrame > 3){
                splashes.remove(i);


            }
        }
        if (life == 2){
            healthPaint.setColor(Color.YELLOW);
        } else if (life == 1 ){
            healthPaint.setColor(Color.RED);
        }
        canvas.drawRect(dWidth - 200, 30, dWidth-200+60*life, 80, healthPaint);
        canvas.drawText("" + points, 20,  TEXT_SIZE, textPaint);
        postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= pernosaY){
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldXpersonaX = personaX;

            }
            if (action == MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                float newPersonaX = oldXpersonaX - shift;
                if (newPersonaX <=0)
                    personaX = 0;
                else if (newPersonaX >= dWidth - persona.getWidth())
                    personaX = dWidth - persona.getWidth();
                else
                    personaX =newPersonaX;

            }
        }
        return true;
    }
}
