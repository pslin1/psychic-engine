package com.psychic_engine.cmput301w17t10.feelsappman.Fragments;

/**
 * Created by jordi on 2017-03-09.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychic_engine.cmput301w17t10.feelsappman.Activities.EditMoodActivity;
import com.psychic_engine.cmput301w17t10.feelsappman.Activities.ViewMoodEventActivity;
import com.psychic_engine.cmput301w17t10.feelsappman.Models.MoodEvent;
import com.psychic_engine.cmput301w17t10.feelsappman.Models.ParticipantSingleton;
import com.psychic_engine.cmput301w17t10.feelsappman.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class RecentTabFragment extends Fragment {

    private static final String FILENAME = "file.sav";
    public ArrayList<MoodEvent> moodEventsRecent;
    private MoodEvent moodEvent;
    private TextView date;
    private TextView viewmood;
    private ParticipantSingleton instance;
    private TextView location;
    private ImageView imageView;
    private Button delete;
    private Button edit;
    private int mostRecentIndex;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recent, container, false);
        delete = (Button)rootView.findViewById(R.id.deletebutton);
        edit = (Button)rootView.findViewById(R.id.editbutton);
        date = (TextView) rootView.findViewById(R.id.date);
        viewmood = (TextView) rootView.findViewById(R.id.mood);
        location = (TextView) rootView.findViewById(R.id.location);
        imageView = (ImageView) rootView.findViewById(R.id.picture);
        moodEventsRecent = ParticipantSingleton.getInstance().getSelfParticipant().getMoodList();
        mostRecentIndex = ParticipantSingleton.getInstance().getSelfParticipant().getMostRecentMoodEventIndex();


        if (moodEventsRecent.size()>0){
            moodEvent = moodEventsRecent.get(mostRecentIndex);


            viewmood.setText(moodEvent.getMood().toString());
            date.setText(moodEvent.getDate().toString());
            if (moodEvent.getPicture() != null) {
                imageView.setImageBitmap(moodEvent.getPicture().getImage());
            }
            //location.setText(moodEvent.getLocation().toString());
            //saveInFile();

        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (moodEventsRecent.size()>0) {
                    moodEventsRecent.remove(moodEventsRecent.size() - 1);
                    ParticipantSingleton.getInstance().getSelfParticipant().setMoodList(moodEventsRecent);


                    if (moodEventsRecent.size() > 0) {


                        moodEvent = moodEventsRecent.get(moodEventsRecent.size() - 1);


                        viewmood.setText(moodEvent.getMood().toString());
                        date.setText(moodEvent.getDate().toString());
                        if (moodEvent.getPicture() != null) {
                            imageView.setImageBitmap(moodEvent.getPicture().getImage());
                            //location.setText(moodEvent.getLocation().toString());

                            saveInFile();
                        }
                    } else {
                        viewmood.setText("");
                        date.setText("There's No Mood Event Yet! Why Don't you add one!");
                        location.setText("");
                        imageView.setImageBitmap(null);
                        saveInFile();
                    }
                }
                 else {
                        viewmood.setText("");
                        date.setText("There's No Mood Event Yet! Why Don't you add one!");
                        location.setText("");
                        saveInFile();

                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moodEventsRecent.size() > 0) {
                    Intent intent = new Intent(getActivity(), EditMoodActivity.class);
                    intent.putExtra("moodEventPosition", mostRecentIndex);
                    startActivity(intent);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moodEventsRecent.size() > 0) {
                    Intent intent = new Intent(getActivity(), ViewMoodEventActivity.class);
                    intent.putExtra("moodEventPosition", mostRecentIndex);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    /**
     * Method to save the instance for future use upon destruction of the activity. The singleton
     * instance will contain all of the participants activity (moods, signing up, etc.)
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(ParticipantSingleton.getInstance(), out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        saveInFile();
    }
}
