package com.example.retrogame;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;


public class Fruit{
    Bitmap fruit[] = new Bitmap[3];
    int fruitFrame = 0;
    int fruitX, fruitY, fruitVelocity;
    Random random;


    public Fruit(Context context) {
        fruit[0] =BitmapFactory.decodeResource(context.getResources(),R.drawable.fruit);
        fruit[1] =BitmapFactory.decodeResource(context.getResources(),R.drawable.fruit);
        fruit[2] =BitmapFactory.decodeResource(context.getResources(),R.drawable.fruit );
        random = new Random();
        resetPosition();
    }

    public Bitmap  getFruit(int fruitFrame){
        return fruit[fruitFrame];

    }

    public int getFruitWidth(){
        return fruit[0].getWidth();
    }

    public int getFruitHeigth(){
        return fruit[0].getHeight();
    }
    public void resetPosition(){
        fruitX = random.nextInt(GameView.dWidth - getFruitWidth());
        fruitY = -200 + random.nextInt(600) * -1;
        fruitVelocity = 35 + random.nextInt(16);
    }
}




