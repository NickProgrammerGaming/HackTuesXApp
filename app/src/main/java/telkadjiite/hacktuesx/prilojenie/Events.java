package telkadjiite.hacktuesx.prilojenie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Events extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://hacktuesxprilojenie-10166-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    ArrayList<String> ids;
    ArrayList<String> eventTitles;

    ListView lv;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_events, container, false);

        DatabaseReference idRefs = databaseReference.child("events");
        ids = new ArrayList<String>();
        lv = rootView.findViewById(R.id.eventsView);


        idRefs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    String id = ds.getKey();
                    ids.add(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(String s : ids)
                {
                    Log.println(Log.INFO, "Id of event", s);
                    eventTitles.add(snapshot.child("events").child(s).child("title").getValue(String.class));
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, eventTitles);
        lv.setAdapter(adapter);



        // Inflate the layout for this fragment
        return rootView;
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