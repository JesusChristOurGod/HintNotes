package com.jesus.hintnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jesus.hintnotes.dao.hintDao;
import com.jesus.hintnotes.models.Hint;


public class MainActivity extends AppCompatActivity {

    public SurfaceView surf;
    public LinearLayout piece;
    public LinearLayout bpiece;
    public LinearLayout fpiece;
    public LinearLayout helper;
    public TextView nmm, nmb, nmf, nmh, txtm, txtf, txtb, txth, tmrm, tmrf, tmrb, tmrh;

    public int mid;
    public int bid;
    public int fid;
    public int namem, namef, nameb, nameh, textm, textf,textb, texth, timerm,timerf,timerb, timerh;


    public hintDao hintsDao = hintDao.getInstance();
    public Button nextTimer;
    public MainActivity.Timer timer;
    public boolean isActive=false;
    public Long workingtime=0L;
    public TextView timerText;
    public int activeTimer = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        surf = findViewById(R.id.surfaceView);
        piece = findViewById(R.id.piece);
        bpiece = findViewById(R.id.backpiece);
        fpiece = findViewById(R.id.frontpiece);
        helper = findViewById(R.id.helper);
        nmb=findViewById(R.id.name);
        nmm=findViewById(R.id.name1);
        nmf=findViewById(R.id.name2);
        nmh=findViewById(R.id.name3);
        txtb=findViewById(R.id.textDisplay);
        txtm=findViewById(R.id.textDisplay1);
        txtf=findViewById(R.id.textDisplay2);
        txth=findViewById(R.id.textDisplay3);
        tmrm=findViewById(R.id.timerDisplay1);
        tmrb=findViewById(R.id.timerDisplay);
        tmrf=findViewById(R.id.timerDisplay2);
        tmrh=findViewById(R.id.timerDisplay3);

        namem =nmm.getId();
        nameb=nmb.getId();
        nameh=nmh.getId();
        namef=nmf.getId();

        textb=txtb.getId();
        textm=txtm.getId();
        textf=txtf.getId();
        texth=txth.getId();

        timerm=tmrm.getId();
        timerh=tmrh.getId();
        timerf=tmrf.getId();
        timerb=tmrb.getId();








        mid = piece.getId();
        bid = bpiece.getId();
        fid = fpiece.getId();


        timerText=findViewById(R.id.timerDisplay1);

        timer = new MainActivity.Timer(hintsDao.getHint(activeTimer));
        timer.start();





}
    public class Timer extends CountDownTimer {
        Hint hint;

        public Timer( Hint hint) {
            super(hint.getMillisInFuture(), 1000);
            this.hint=hint;
        }

        @Override
        public void onTick(long l) {
            int sec = Long.valueOf(l / 1000).intValue();
            int mm = sec / 60;
            int ss = sec % 60;
            String text = String.format("%02d:%02d", mm, ss);
            Log.e("Я выполняю " + hint.getTitle(), "мне его осталось выполнять "+ l);
            timerText.setText(text);
        }

        @Override
        public void onFinish() {
            Log.e("Я закончил выполнять " + hint.getTitle(), "таймер остановлен!");
            Toast.makeText(getApplicationContext(),"Время первого слайда окончено", Toast.LENGTH_SHORT).show();
            activeTimer++;
            isActive=false;
        }



    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float sensitvity = 10000;
            if ((e2.getX() - e1.getX()) <sensitvity ){
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animat_main);
                piece.startAnimation(animation);


                Animation animationback = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animat_back);
                bpiece.startAnimation(animationback);

                Animation animationfront = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animat_front);
                fpiece.startAnimation(animationfront);


                Animation animationhelp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animat_back);
                helper.startAnimation(animationhelp);
                new CountDownTimer(3000, 1000) {
                    public void onFinish() {
                        // When timer is finished
                        // Execute your code here
                        helper.setAlpha((float) 0.0);
                        bpiece.setX(helper.getX());
                        piece.setId(bid);
                        bpiece.setId(fid);
                        fpiece.setId(mid);
                        txtm.setId(textb);
                        txtb.setId(textf);
                        txtf.setId(textm);
                        tmrm.setId(timerb);
                        tmrb.setId(timerf);
                        tmrf.setId(timerm);
                        nmm.setId(nameb);
                        nmb.setId(namef);
                        nmf.setId(namem);

                    }

                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();


            }
            return true;
        }
    };

    GestureDetector gestureDetector = new GestureDetector(getBaseContext(),
            simpleOnGestureListener);


}




