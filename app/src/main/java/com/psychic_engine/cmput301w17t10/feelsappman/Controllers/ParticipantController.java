package com.psychic_engine.cmput301w17t10.feelsappman.Controllers;

import android.icu.text.MessagePattern;
import android.util.Log;

import com.psychic_engine.cmput301w17t10.feelsappman.Models.Participant;

import java.util.concurrent.ExecutionException;

/**
 * Created by adong on 3/20/17.
 */

public class ParticipantController {

    private static Participant loggedParticipant = null;

    private ParticipantController(){}

    public static boolean checkUniqueParticipant(String participantName) {
        Participant foundParticipant = null;
        ElasticSearchController.FindParticipantTask spt = new ElasticSearchController.FindParticipantTask();

        try {

            foundParticipant = spt.execute(participantName).get();
            if (foundParticipant == null) {
                System.out.println(foundParticipant.getLogin() + " | " + foundParticipant.getID());
            } else {
                System.out.println("Participant is null on search");
            }
        } catch (Exception e) {
            Log.i("CheckParticipantName", "Failed connection with the elastic server");
        }

        return foundParticipant == null;
    }
}
