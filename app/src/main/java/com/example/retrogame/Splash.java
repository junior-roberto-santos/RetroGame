package com.example.retrogame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Splash {


        Bitmap splash[] = new Bitmap[3];
        int splashFrame = 0;
        int splashX, splashY, splashVelocity;
        Random random;

        public Splash(Context context){
            splash[0] =BitmapFactory.decodeResource(context.getResources(),R.drawable.splash3);
            splash[1] =BitmapFactory.decodeResource(context.getResources(),R.drawable.splash2);
            splash[2] =BitmapFactory.decodeResource(context.getResources(),R.drawable.splash1);
            random = new Random();
            resetPosition();
        }
        public Bitmap getSplashWidth 
        public void resetPosition(){

            splashX = random.nextInt(GameView.dWidth - getSplashWidth());
            splashY = -200 + random.nextInt(600) * -1;
            splashVelocity = 35 + random.nextInt(16);




        }

    }


