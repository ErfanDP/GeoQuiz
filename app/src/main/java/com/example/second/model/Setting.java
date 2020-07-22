package com.example.second.model;

import java.io.Serializable;
import java.util.HashMap;

public class Setting implements Serializable {
    private SettingTextSize mSettingTextSize = SettingTextSize.MEDIUM;
    private SettingBackGroundColor mSettingBackGroundColor = SettingBackGroundColor.WHITE;
    private HashMap<String, Boolean> mButtonVisibilities = new HashMap<>();


    public Setting() {
        mButtonVisibilities.put("first", true);
        mButtonVisibilities.put("last", true);
        mButtonVisibilities.put("next", true);
        mButtonVisibilities.put("previous", true);
        mButtonVisibilities.put("true", true);
        mButtonVisibilities.put("false", true);
        mButtonVisibilities.put("cheat", true);
    }

    public Setting(SettingTextSize settingTextSize, SettingBackGroundColor settingBackGroundColor,
                   HashMap<String, Boolean> buttonVisibilities) {
        this.mSettingTextSize = settingTextSize;
        this.mSettingBackGroundColor = settingBackGroundColor;
        this.mButtonVisibilities = buttonVisibilities;

    }

    public HashMap<String, Boolean> getmButtonVisibilities() {
        return mButtonVisibilities;
    }

    public void setmNextButtonVisibilities(boolean state) {
        this.mButtonVisibilities.put("next", state);
    }

    public void setmPreviousButtonVisibilities(boolean state) {
        this.mButtonVisibilities.put("previous", state);
    }

    public void setmTrueButtonVisibilities(boolean state) {
        this.mButtonVisibilities.put("true", state);
    }

    public void setmFalseButtonVisibilities(boolean state) {
        this.mButtonVisibilities.put("false", state);
    }

    public void setmFirstButtonVisibilities(boolean state) {
        this.mButtonVisibilities.put("first", state);
    }

    public void setmLastButtonVisibilities(boolean state) {
        this.mButtonVisibilities.put("last", state);
    }

    public void setmCheatButtonVisibilities(boolean state) {
        this.mButtonVisibilities.put("cheat", state);
    }

    public SettingBackGroundColor getmSettingBackGroundColor() {
        return mSettingBackGroundColor;
    }

    public SettingTextSize getmSettingTextSize() {
        return mSettingTextSize;
    }

    public void setmSettingBackGroundColor(SettingBackGroundColor mSettingBackGroundColor) {
        this.mSettingBackGroundColor = mSettingBackGroundColor;
    }

    public void setmSettingTextSize(SettingTextSize mSettingTextSize) {
        this.mSettingTextSize = mSettingTextSize;
    }
}
