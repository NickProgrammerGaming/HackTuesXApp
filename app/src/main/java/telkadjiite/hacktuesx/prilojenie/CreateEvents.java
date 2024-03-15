package telkadjiite.hacktuesx.prilojenie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateEvents extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference database = FirebaseDatabase.getInstance("https://hacktuesxprilojenie-10166-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    EditText titleInput;
    EditText descriptionInput;
    EditText locationInput;
    EditText neededPeopleInput;
    Spinner hobbiesSpinner;

    Button createEventButton;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_events, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        titleInput = rootView.findViewById(R.id.titleCreateInput);
        descriptionInput = rootView.findViewById(R.id.descriptionInput);
        locationInput = rootView.findViewById(R.id.locationInput);
        neededPeopleInput = rootView.findViewById(R.id.neededPeopleInput);
        hobbiesSpinner = rootView.findViewById(R.id.spinnerHobbies);
        createEventButton = rootView.findViewById(R.id.createEventButton);

        ArrayList<String> hobbiesArray = new ArrayList<String>();
        for(Hobbies h : Hobbies.values())
        {
            hobbiesArray.add(h.name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, hobbiesArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        hobbiesSpinner.setAdapter(adapter);



        createEventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(titleInput.getText().toString().isEmpty())
                {
                    titleInput.setError("Enter a title");

                }
                else if(descriptionInput.getText().toString().isEmpty())
                {
                    descriptionInput.setError("Enter a description");

                }
                else if(locationInput.getText().toString().isEmpty())
                {
                    locationInput.setError("Enter a location");

                }
                else if(Integer.parseInt(neededPeopleInput.getText().toString()) < 1)
                {
                    neededPeopleInput.setError("Enter a valid number");

                }
                else
                {
                    CreateEvent(titleInput.getText().toString(), descriptionInput.getText().toString(),
                            locationInput.getText().toString(), Integer.parseInt(neededPeopleInput.getText().toString()),
                            Hobbies.valueOf(hobbiesSpinner.getSelectedItem().toString()));
                }


            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    void CreateEvent(String title, String description, String location, int neededPeople, Hobbies hobby)
    {


        Event newEvent = new Event(title, description, location, neededPeople, hobby, searchForUserDatabase(mUser.getUid()));
        database.child("events").child(newEvent.id).child("title").setValue(title);
        database.child("events").child(newEvent.id).child("description").setValue(description);
        database.child("events").child(newEvent.id).child("location").setValue(location);
        database.child("events").child(newEvent.id).child("neededPeople").setValue(neededPeople);
        database.child("events").child(newEvent.id).child("currentNeededPeople").setValue(neededPeople);
        database.child("events").child(newEvent.id).child("hobby").setValue(hobby);
        database.child("events").child(newEvent.id).child("owner").setValue(newEvent.owner);
        database.child("events").child(newEvent.id).child("participants").setValue(newEvent.participants);
        Toast.makeText(rootView.getContext(), "Successfully created an event", Toast.LENGTH_SHORT).show();

    }

    User searchForUserDatabase(String uid)
    {
        Log.println(Log.INFO, "Uid of current user", uid);
        final User[] user = new User[1];

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName = snapshot.child("users").child(uid).child("firstName").getValue(String.class);
                String lastName = snapshot.child("users").child(uid).child("lastName").getValue(String.class);
                user[0] = new User(firstName, lastName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        titleInput.setText("");
        descriptionInput.setText("");
        locationInput.setText("");
        neededPeopleInput.setText("");

        return  user[0];
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