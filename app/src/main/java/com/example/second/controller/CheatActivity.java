
package com.example.second.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.second.R;

public class CheatActivity extends AppCompatActivity {
    public static final String EXTRA_ANSWER = "question_answer";
    private TextView mAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        Intent intent = getIntent();
        if(intent.getBooleanExtra(EXTRA_ANSWER,false)){
            mAnswer.setText(R.string.cheat_true);
        }else
            mAnswer.setText(R.string.cheat_false);
    }
}