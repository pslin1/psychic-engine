package com.psychic_engine.cmput301w17t10.feelsappman;

import android.content.Context;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/**
 * Created by jyuen1 on 3/7/17.
 */

public class CreateMoodController {

    public boolean updateMoodEventList(String moodString, String socialSettingString, String trigger, Photograph photo, String location) {
        Log.d("TAG","-----------------------------------------------------");
        Mood mood;
        SocialSetting socialSetting;
        Log.d("Mood String", moodString);
        switch(moodString) {        // TODO refactor this - inside MoodState enum class?
            case "Sad": mood = new Mood(MoodState.SAD); break;
            case "Happy": mood = new Mood(MoodState.HAPPY); break;
            case "Shame": mood = new Mood(MoodState.SHAME); break;
            case "Fear": mood = new Mood(MoodState.FEAR); break;
            case "Anger": mood = new Mood(MoodState.ANGER); break;
            case "Surprised": mood = new Mood(MoodState.SURPRISED); break;
            case "Disgust": mood = new Mood(MoodState.DISGUST); break;
            case "Confused": mood = new Mood(MoodState.CONFUSED); break;
            default:
                return false;
        }

        switch (socialSettingString) {
            case "Alone":
                socialSetting = SocialSetting.ALONE;
                break;
            case "One Other":
                socialSetting = SocialSetting.ONEOTHER;
                break;
            case "Two To Several":
                socialSetting = SocialSetting.TWOTOSEVERAL;
                break;
            case "Crowd":
                socialSetting = SocialSetting.CROWD;
                break;
            default:
                socialSetting = null;
        }

        MoodEvent moodEvent = new MoodEvent(mood, socialSetting, trigger, photo, location);
        ParticipantSingleton.getInstance().getSelfParticipant().addMoodEvent(moodEvent);

        return true;
    }
}
