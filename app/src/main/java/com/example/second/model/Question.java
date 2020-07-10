package com.example.second.model;

import android.graphics.Color;

import com.example.second.R;

public class Question {
    private String mText;
    private boolean mAnswerTrue;
    private boolean mAnswered = false;
    private boolean mIsCheatable;
    private int mTextColor;

    public Question(String mText, boolean mAnswerTrue, boolean mIsCheatable, Colors mTextColor) {
        this.mText = mText;
        this.mAnswerTrue = mAnswerTrue;
        this.mIsCheatable = mIsCheatable;
        switch (mTextColor){
            case RED:
                this.mTextColor = Color.RED;
                break;
            case BLUE:
                this.mTextColor = Color.BLUE;
                break;
            case BLACK:
                this.mTextColor = Color.BLACK;
                break;
            case GREEN:
                this.mTextColor = Color.GREEN;
                break;
            default:
                this.mTextColor = Color.YELLOW;
        }
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public boolean ismIsCheatable(){
        return mIsCheatable;
    }

    public String getmTextId() {
        return mText;
    }

    public void setmTextId(String  mText) {
        this.mText = mText;
    }

    public boolean ismAnswerTrue() {
        return mAnswerTrue;
    }

    public void setmAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }

    public boolean ismAnswered() {
        return mAnswered;
    }

    public void setmAnswered(boolean mAnswered) {
        this.mAnswered = mAnswered;
    }

    @Override
    public String toString() {
        return "Question{" +
                "mText='" + mText + '\'' +
                ", mAnswerTrue=" + mAnswerTrue +
                ", mIsCheatable=" + mIsCheatable +
                ", mTextColor=" + mTextColor +
                '}';
    }
}
