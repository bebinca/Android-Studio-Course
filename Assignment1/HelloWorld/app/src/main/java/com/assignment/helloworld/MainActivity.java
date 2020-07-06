package com.assignment.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    int [] image = new int []{
            R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4,
            R.drawable.c5,
            R.drawable.c6,
            R.drawable.c7,
            R.drawable.c8,
            R.drawable.c9,
            R.drawable.c10,
            R.drawable.c11,
            R.drawable.c12,
            R.drawable.c13,
            R.drawable.c14,
            R.drawable.c15,
            R.drawable.c16,
            R.drawable.c17,
            R.drawable.c18,
            R.drawable.c19,
            R.drawable.c20,
            R.drawable.c21,
            R.drawable.c22,
            R.drawable.c23,
            R.drawable.c24,
            R.drawable.c25,
            R.drawable.c26,
            R.drawable.c27,
            R.drawable.c28,
            R.drawable.c29,
            R.drawable.c30,
            R.drawable.c31,
            R.drawable.c32,
            R.drawable.c33,
            R.drawable.c34
    };
    String [] text = new String[] {
            "Revenge",
            "Creeper!?",
            "Awwwww Man!!",
            "So we back in the mine",
            "got our pick axe swinging from",
            "Side",
            "Side to",
            "Side to side",
            "Side",
            "Side side ",
            "Side side to side",
            "This task a grueling one",
            "hope to find some diamonds to",
            "night, night, night",
            "Diamond",
            "Diamond to night",
            "Heads up",
            "You hear a sound",
            "turn around and look up",
            "total shock fills your body",
            "Oh no it's you again",
            "I could never forget those",
            "Eyes",
            "Eyes, eyes, eyes",
            "Cause baby tonight",
            "the creeper's trying to steal all our stuff again",
            "Cause baby tonight",
            "you grab your pick shovel and bolt again",
            "And run run..",
            "Until it's done done..",
            "until the sun comes up in the morn",
            "Cause baby tonight",
            "the creeper's trying to steal all our stuff again",
            "stuff again.."
    };

    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn1 = findViewById(R.id.btn1);
        final TextView tv1 = findViewById(R.id.tv1);
        final ImageView iv1 = findViewById(R.id.im1);
        final ToggleButton toggleButton = findViewById(R.id.toggleButton);
        final Switch switch1 = findViewById(R.id.switch1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                tv1.setText(text[i]);
                iv1.setImageResource(image[i]);
                if (i == 33) i = -1;
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    i = 0;
                    iv1.setImageResource(image[i]);
                    tv1.setText(text[i]);
                } else {
                    i = 33;
                    iv1.setImageResource(image[i]);
                    tv1.setText(text[i]);
                    i = -1;
                }
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    iv1.setVisibility(View.GONE);
                    toggleButton.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    tv1.setVisibility(View.GONE);

                } else {
                    iv1.setVisibility(View.VISIBLE);
                    toggleButton.setVisibility(View.VISIBLE);
                    btn1.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
