package com.example.second.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.second.R;
import com.example.second.model.Setting;
import com.example.second.model.SettingBackGroundColor;
import com.example.second.model.SettingTextSize;

import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {
    private Setting setting;
    private RadioGroup mTextSizeRadioGroup;
    private RadioGroup mBackGroundColorRadioGroup;
    private Button mSaveButton;
    private Button mBackButton;
    private Switch mVisibilityFalseSwitch;
    private Switch mVisibilityNextSwitch;
    private Switch mVisibilityCheatSwitch;
    private Switch mVisibilityTrueSwitch;
    private Switch mVisibilityPreviousSwitch;
    private Switch mVisibilityFirstSwitch;
    private Switch mVisibilityLastSwitch;
    public static final String EXTRA_SETTING = "com.example.second.setting";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findAllViews();
        mGetSetting();
        mDoSettings();
        Listeners();
    }

    private void mGetSetting() {
        Intent intent = getIntent();
        setting = (Setting) intent.getSerializableExtra(EXTRA_SETTING);
    }

    private void mDoSettings() {
        switch (setting.getmSettingBackGroundColor()) {
            case RED:
                mBackGroundColorRadioGroup.check(R.id.radioButton_setting_background_color_red);
                break;
            case WHITE:
                mBackGroundColorRadioGroup.check(R.id.radioButton_setting_background_color_white);
                break;
            case GREEN:
                mBackGroundColorRadioGroup.check(R.id.radioButton_setting_background_color_green);
                break;
            case BLUE:
                mBackGroundColorRadioGroup.check(R.id.radioButton_setting_background_color_blue);
                break;
        }
        switch (setting.getmSettingTextSize()) {
            case SMALL:
                mTextSizeRadioGroup.check(R.id.radioButton_setting_textsize_small);
                break;
            case MEDIUM:
                mTextSizeRadioGroup.check(R.id.radioButton_setting_textsize_medium);
                break;
            case LARGE:
                mTextSizeRadioGroup.check(R.id.radioButton_setting_textsize_large);
                break;
        }
        HashMap<String, Boolean> buttonVisibilities = setting.getmButtonVisibilities();
        mVisibilityFirstSwitch.setChecked(buttonVisibilities.get("first"));
        mVisibilityLastSwitch.setChecked(buttonVisibilities.get("last"));
        mVisibilityCheatSwitch.setChecked(buttonVisibilities.get("cheat"));
        mVisibilityTrueSwitch.setChecked(buttonVisibilities.get("true"));
        mVisibilityFalseSwitch.setChecked(buttonVisibilities.get("false"));
        mVisibilityNextSwitch.setChecked(buttonVisibilities.get("next"));
        mVisibilityPreviousSwitch.setChecked(buttonVisibilities.get("previous"));
    }

    private void Listeners() {
        mTextSizeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_setting_textsize_large:
                        setting.setmSettingTextSize(SettingTextSize.LARGE);
                        break;
                    case R.id.radioButton_setting_textsize_medium:
                        setting.setmSettingTextSize(SettingTextSize.MEDIUM);
                        break;
                    case R.id.radioButton_setting_textsize_small:
                        setting.setmSettingTextSize(SettingTextSize.SMALL);
                        break;
                }
            }
        });
        mTextSizeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_setting_background_color_blue:
                        setting.setmSettingBackGroundColor(SettingBackGroundColor.BLUE);
                        break;
                    case R.id.radioButton_setting_background_color_white:
                        setting.setmSettingBackGroundColor(SettingBackGroundColor.WHITE);
                        break;
                    case R.id.radioButton_setting_background_color_red:
                        setting.setmSettingBackGroundColor(SettingBackGroundColor.RED);
                        break;
                    case R.id.radioButton_setting_background_color_green:
                        setting.setmSettingBackGroundColor(SettingBackGroundColor.GREEN);
                        break;
                }
            }
        });
        mVisibilityPreviousSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setmPreviousButtonVisibilities(isChecked);
            }
        });

        mVisibilityNextSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setmNextButtonVisibilities(isChecked);
            }
        });

        mVisibilityTrueSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setmTrueButtonVisibilities(isChecked);
            }
        });

        mVisibilityFalseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setmFalseButtonVisibilities(isChecked);
            }
        });

        mVisibilityFirstSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setmFirstButtonVisibilities(isChecked);
            }
        });

        mVisibilityLastSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setmLastButtonVisibilities(isChecked);
            }
        });

        mVisibilityCheatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setmCheatButtonVisibilities(isChecked);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(QuizActivity.EXTRA_SETTING_CHANGED, setting);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void findAllViews() {
        mBackGroundColorRadioGroup = findViewById(R.id.setting_background_color_radiogroup);
        mTextSizeRadioGroup = findViewById(R.id.setting_question_text_size_raidogroup);
        mBackButton = findViewById(R.id.button_setting_back);
        mSaveButton = findViewById(R.id.button_setting_save);
        mVisibilityCheatSwitch = findViewById(R.id.setting_hide_switch_cheat);
        mVisibilityFirstSwitch = findViewById(R.id.setting_hide_switch_first);
        mVisibilityLastSwitch = findViewById(R.id.setting_hide_switch_last);
        mVisibilityTrueSwitch = findViewById(R.id.setting_hide_switch_true);
        mVisibilityFalseSwitch = findViewById(R.id.setting_hide_switch_false);
        mVisibilityNextSwitch = findViewById(R.id.setting_hide_switch_next);
        mVisibilityPreviousSwitch = findViewById(R.id.setting_hide_switch_previous);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(SettingActivity.this, R.string.toast_back_button_disable, Toast.LENGTH_SHORT).show();
    }
}