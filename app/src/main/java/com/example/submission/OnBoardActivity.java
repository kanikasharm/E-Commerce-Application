package com.example.submission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.submission.adapters.SliderAdapter;

public class OnBoardActivity extends AppCompatActivity {
 ViewPager viewPager;
 LinearLayout dotsLayout;
 SliderAdapter sliderAdapter;
 TextView[] dots;
 Button btn;
 Animation animation;
 SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        btn = findViewById(R.id.get_started_button);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);


        addDots(0);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardActivity.this, sign_up.class));
                finish();
            }
        });

    }

    public void addDots(int pos) {
      dots = new TextView[3];
      dotsLayout.removeAllViews();
      for(int i = 0; i<dots.length; i++) {
          dots[i] = new TextView(this);
          dots[i].setText(Html.fromHtml("&#8226;"));
          dots[i].setTextSize(35);
          dotsLayout.addView(dots[i]);
      }
      if(dots.length>0) {
          dots[pos].setTextColor(getResources().getColor(R.color.pink));
      }
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
          addDots(position);

          if(position <=1 ) {
              btn.setVisibility(View.INVISIBLE);
          }else{
              animation = AnimationUtils.loadAnimation(OnBoardActivity.this, R.anim.animation);
              btn.setAnimation(animation);
              btn.setVisibility(View.VISIBLE);
          }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        // Save state to a Bundle
        Bundle state = new Bundle();
        state.putString("key", "value");
        onSaveInstanceState(state);
    }
}