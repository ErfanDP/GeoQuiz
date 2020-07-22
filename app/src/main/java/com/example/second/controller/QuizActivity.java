package com.example.second.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.second.R;
import com.example.second.model.Colors;
import com.example.second.model.Question;
import com.example.second.model.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class QuizActivity extends AppCompatActivity {
    private Setting setting = new Setting();
    private Button mButtonFalse;
    private Button mButtonTrue;
    private TextView mButtonTextNext;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonFirst;
    private ImageButton mButtonLast;
    private ImageButton mReset;
    private TextView mScore;
    private Button mButtonCheat;
    private Button mButtonSetting;
    private TextView mTimer;
    private int score = 0;
    private int questionIndex = 0;
    private int delay;
    private int timer;
    private CountDownTimer countDownTimer;
    private List<Question> questionList;
    public static final String EXTRA_SETTING_CHANGED = "com.example.second.settingChanged";
    public static final int REQUEST_SETTING = 0;
    public static final int REQUEST_CHEAT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        findAllView();
        IntentReceiver();
        mReset.setVisibility(View.INVISIBLE);
        if (savedInstanceState != null) {
            questionIndex = savedInstanceState.getInt("current_question");
            updateQuestion();
            timer = savedInstanceState.getInt("timer");
            timer().start();
            score = savedInstanceState.getInt("score");
            boolean[] answered = savedInstanceState.getBooleanArray("answered");
            for (int i = 0; i < answered.length; i++) {
                questionList.get(i).setmAnswered(answered[i]);
            }
            buttonView();
            if (checkAllAnswered()) {
                gameOver();
            }
        } else {
            timer().start();
        }
        mScore.setText(Integer.toString(score));
        setClickListener();
        buttonView();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        boolean[] answered = new boolean[questionList.size()];
        for (int i = 0; i < answered.length; i++) {
            answered[i] = questionList.get(i).ismAnswered();
        }
        outState.putBooleanArray("answered", answered);
        outState.putInt("score", score);
        outState.putInt("current_question", questionIndex);
        outState.putInt("timer", timer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }

        if (requestCode == REQUEST_SETTING) {
            setting = (Setting) data.getSerializableExtra(EXTRA_SETTING_CHANGED);
            timer().start();
            mDoSettings();
        }
    }

    private CountDownTimer timer() {
        mTimer.setText(Integer.toString(timer));
        countDownTimer = new CountDownTimer(timer * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTimer.setText(Integer.toString(timer--));
            }

            public void onFinish() {
                gameOver();
            }
        };
        return countDownTimer;
    }

    private void findAllView() {
        mButtonFalse = findViewById(R.id.button_false);
        mButtonTrue = findViewById(R.id.button_true);
        mButtonTextNext = findViewById(R.id.text_question_button);
        mButtonPrevious = findViewById(R.id.previous_button);
        mButtonFirst = findViewById(R.id.first_button);
        mButtonLast = findViewById(R.id.last_button);
        mScore = findViewById(R.id.score);
        mReset = findViewById(R.id.reset_button);
        mButtonCheat = findViewById(R.id.button_cheat);
        mButtonSetting = findViewById(R.id.button_setting);
        mTimer = findViewById(R.id.timer_number);
    }

    private void correctAnswer() {
        Toast.makeText(QuizActivity.this, R.string.toast_correct_answer, Toast.LENGTH_SHORT).show();
        score++;
        mScore.setText(new Integer(score).toString());
    }

    private void setClickListener() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!questionList.get(questionIndex).ismAnswered()) {
                    if (questionList.get(questionIndex).ismAnswerTrue()) {
                        correctAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, R.string.toast_incorrect_answer, Toast.LENGTH_SHORT).show();
                    }
                    questionList.get(questionIndex).setmAnswered(true);
                }
                buttonView();
                if (checkAllAnswered()) {
                    gameOver();
                }
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!questionList.get(questionIndex).ismAnswered()) {
                    if (questionList.get(questionIndex).ismAnswerTrue()) {
                        Toast.makeText(QuizActivity.this, R.string.toast_incorrect_answer, Toast.LENGTH_SHORT).show();
                    } else {
                        correctAnswer();
                    }
                    questionList.get(questionIndex).setmAnswered(true);
                    buttonView();
                }
                if (checkAllAnswered()) {
                    gameOver();
                }
            }
        });

        mButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex = questionList.size() - 1;
                updateQuestion();
                buttonView();
            }
        });

        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex = 0;
                updateQuestion();
                buttonView();
            }
        });

        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex = ((--questionIndex) + questionList.size()) % questionList.size();
                updateQuestion();
                buttonView();
            }
        });

        mButtonTextNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionIndex = (++questionIndex) % questionList.size();
                updateQuestion();
                buttonView();
            }
        });
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionList.get(questionIndex).ismIsCheatable()) {
                    Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                    intent.putExtra(CheatActivity.EXTRA_ANSWER,
                            questionList.get(questionIndex).ismAnswerTrue());
                    startActivityForResult(intent, 10);
                    questionList.get(questionIndex).setmAnswered(true);
                } else
                    Toast.makeText(QuizActivity.this, R.string.error_notCheatable,
                            Toast.LENGTH_SHORT).show();
            }
        });

        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, SettingActivity.class);
                intent.putExtra(SettingActivity.EXTRA_SETTING, setting);
                countDownTimer.cancel();
                startActivityForResult(intent, REQUEST_SETTING);

            }
        });


    }

    private void buttonView() {
        if (questionList.get(questionIndex).ismIsCheatable()) {
            mButtonCheat.setVisibility(View.VISIBLE);
        } else
            mButtonCheat.setVisibility(View.INVISIBLE);

        if (questionList.get(questionIndex).ismAnswered()) {
            mButtonTrue.setVisibility(View.INVISIBLE);
            mButtonFalse.setVisibility(View.INVISIBLE);
            mButtonCheat.setVisibility(View.INVISIBLE);
        } else {
            mButtonTrue.setVisibility(View.VISIBLE);
            mButtonFalse.setVisibility(View.VISIBLE);
        }
    }

    private void updateQuestion() {
        mButtonTextNext.setText(questionList.get(questionIndex).getmTextId());
        mButtonTextNext.setTextColor(questionList.get(questionIndex).getmTextColor());

    }

    private void gameOver() {
        mButtonTextNext.setVisibility(View.INVISIBLE);
        mButtonFalse.setVisibility(View.INVISIBLE);
        mButtonTrue.setVisibility(View.INVISIBLE);
        mButtonPrevious.setVisibility(View.INVISIBLE);
        mButtonFirst.setVisibility(View.INVISIBLE);
        mButtonLast.setVisibility(View.INVISIBLE);
        mButtonSetting.setVisibility(View.INVISIBLE);
        mButtonCheat.setVisibility(View.INVISIBLE);
        if (score < questionList.size() / 2) {
            mScore.setTextColor(Color.RED);
        } else
            mScore.setTextColor(Color.GREEN);
        mScore.setTextSize(25);
        mReset.setVisibility(View.VISIBLE);
        countDownTimer.cancel();
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonSetting.setVisibility(View.VISIBLE);
                mButtonCheat.setVisibility(View.VISIBLE);
                mButtonTextNext.setVisibility(View.VISIBLE);
                mButtonFalse.setVisibility(View.VISIBLE);
                mButtonTrue.setVisibility(View.VISIBLE);
                mButtonPrevious.setVisibility(View.VISIBLE);
                mButtonFirst.setVisibility(View.VISIBLE);
                mButtonLast.setVisibility(View.VISIBLE);
                mReset.setVisibility(View.INVISIBLE);
                score = 0;
                questionIndex = 0;
                timer = delay;
                timer().start();
                mScore.setText("0");
                mScore.setTextColor(Color.BLACK);
                for (Question q : questionList) {
                    q.setmAnswered(false);
                }
            }
        });

    }

    private boolean checkAllAnswered() {
        for (Question q : questionList) {
            if (!q.ismAnswered()) {
                return false;
            }
        }
        return true;
    }

    private void IntentReceiver() {
        Intent intent = getIntent();
        parsingAll(intent.getStringExtra(QuestionActivity.EXTRA_TEXT));
        timer = delay;
    }

    private void parsingAll(String str) {
        delay = Integer.parseInt(str.substring(str.lastIndexOf("{") + 1, str.length() - 1));
        String allQuestions = str.substring(1, str.lastIndexOf(",") - 1);
        String[] questionsEdited = allQuestions.split("[\\[]");
        String[] questionsInformation = new String[questionsEdited.length];
        for (int i = 0; i < questionsEdited.length; i++) {
            questionsInformation[i] = questionsEdited[i].split("\\]")[0];
        }
        List<Question> questionsList = new ArrayList<>();
        for (int i = 1; i < questionsInformation.length; i++) {
            String[] questionParts = questionsInformation[i].split("\\{");
            String questionText = questionParts[1].substring(0, questionParts[1].indexOf("}"));
            boolean questionAnswer;
            if (questionParts[2].contains("true")) {
                questionAnswer = true;
            } else
                questionAnswer = false;
            boolean isCheatable;
            if (questionParts[3].contains("true")) {
                isCheatable = true;
            } else
                isCheatable = false;
            Colors questionTextColor = null;
            if (questionParts[4].contains("black")) {
                questionTextColor = Colors.BLACK;
            } else if (questionParts[4].contains("green")) {
                questionTextColor = Colors.GREEN;
            } else if (questionParts[4].contains("red")) {
                questionTextColor = Colors.RED;
            } else if (questionParts[4].contains("blue")) {
                questionTextColor = Colors.BLUE;
            }
            questionsList.add(new Question(questionText, questionAnswer, isCheatable, questionTextColor));
        }
        this.questionList = questionsList;
        updateQuestion();
    }

    private void mDoSettings() {
        switch (setting.getmSettingBackGroundColor()) {
            case GREEN:
                findViewById(R.id.root).setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case BLUE:
                findViewById(R.id.root).setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case RED:
                findViewById(R.id.root).setBackgroundColor(getResources().getColor(R.color.red));
                break;
            default:
                findViewById(R.id.root).setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }
        switch (setting.getmSettingTextSize()) {
            case LARGE:
                mButtonTextNext.setTextSize(35);
                break;
            case SMALL:
                mButtonTextNext.setTextSize(14);
                break;
            default:
                mButtonTextNext.setTextSize(20);

        }
        buttonVisibilities();
    }

    private void buttonVisibilities() {
        HashMap<String, Boolean> buttonVisibilities = setting.getmButtonVisibilities();
        if (buttonVisibilities.get("true")) {
            if (!questionList.get(questionIndex).ismAnswered())
                mButtonTrue.setVisibility(View.VISIBLE);
        } else {
            mButtonTrue.setVisibility(View.INVISIBLE);
        }

        if (buttonVisibilities.get("false")) {
            if (!questionList.get(questionIndex).ismAnswered())
                mButtonFalse.setVisibility(View.VISIBLE);
        } else {
            mButtonFalse.setVisibility(View.INVISIBLE);
        }

//        if (buttonVisibilities.get("cheat")) {
//            if (!questionList.get(questionIndex).ismAnswered() && questionList.get(questionIndex).ismIsCheatable())
//                mButtonCheat.setVisibility(View.VISIBLE);
//        } else {
//            mButtonCheat.setVisibility(View.INVISIBLE);
//        }

        if (buttonVisibilities.get("next")) {
            mButtonTextNext.setVisibility(View.VISIBLE);
        } else {
            mButtonTextNext.setVisibility(View.INVISIBLE);
        }

        if (buttonVisibilities.get("previous")) {
            mButtonPrevious.setVisibility(View.VISIBLE);
        } else {
            mButtonPrevious.setVisibility(View.INVISIBLE);
        }

        if (buttonVisibilities.get("first")) {
            mButtonFirst.setVisibility(View.VISIBLE);
        } else {
            mButtonFirst.setVisibility(View.INVISIBLE);
        }

        if (buttonVisibilities.get("last")) {
            mButtonLast.setVisibility(View.VISIBLE);
        } else {
            mButtonLast.setVisibility(View.INVISIBLE);
        }
    }

}
