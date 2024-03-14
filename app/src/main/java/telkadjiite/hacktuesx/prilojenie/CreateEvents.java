package telkadjiite.hacktuesx.prilojenie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateEvents extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_events, container, false);
    }

    /*
    TODO:
    Get reference to realtime database and authorization database
    Create edit texts for all the fields
    Create a dropdown for all the hobbies
    For the location either make it a text field or get location info from google maps view using markers
    Create a create event button
    Associate the event owner with the current user
    Update properties in realtime database
     */
}