package com.example.second.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.second.R;


public class QuestionActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "text" ;
    private EditText text;
    private Button mButtonDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        findAllViews();
        text.setText("{[{“Tehran in iran”}, {true}, {false}, {green}]" +
                ",[{“iran language is english”}, {false} {true}, {red}]" +
                ", [{“England is in usa”}, {false}, {false}, {black}]} ," +
                " {30}");
        if(savedInstanceState != null){
            String text = savedInstanceState.getString("String");
            this.text.setText(text);
        }
        setOnClickListener();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("String",text.getText().toString());
    }

    private void setOnClickListener() {
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable questions = text.getText();
                String str= questions.toString();
                if(str . equals("")) {
                    Toast.makeText(QuestionActivity.this, R.string.error_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(QuestionActivity.this, QuizActivity.class);
                intent.putExtra(EXTRA_TEXT,text.getText().toString());
                startActivity(intent);
            }
        });
    }


    private void findAllViews() {
        mButtonDone = findViewById(R.id.button_done);
        text = findViewById(R.id.questions);
    }
}