package telkadjiite.hacktuesx.prilojenie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Events extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    /*
    TODO:
    Get reference to realtime database and authorization database
    Create a list view with customized list items for the event boxes and make them clickable
    Search for events associated with current user's interests
    When clicked on a box redirect to another fragment/activity which shows google maps + detailed description of event
    Create a join and leave button for the event
    When joining an event decrement current needed people by 1
    Make joining impossible after reaching people needed 0
    When leaving/getting kicked out of an event increment current needed event
    Update properties in realtime database
     */
}