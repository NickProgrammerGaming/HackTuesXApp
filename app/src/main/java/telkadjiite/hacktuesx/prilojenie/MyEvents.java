package telkadjiite.hacktuesx.prilojenie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyEvents extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_events, container, false);
    }

    /*
    TODO:
    Get reference to realtime database and authorization database
    Get the created and joined events of the current user from the database
    Display them using list view with customized list elements
    For other users' events: Make them clickable and display the map + description of event as in
    events activity(maybe add possibility to view other participants and their profiles)
    For your events: Same as the other users' events but you can kick people and delete the events
    Update properties in realtime database
     */
}