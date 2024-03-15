package telkadjiite.hacktuesx.prilojenie;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends Fragment
{
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference database = FirebaseDatabase.getInstance("https://hacktuesxprilojenie-10166-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    TextView firstNameText;
    TextView lastNameText;
    EditText descriptionProfile;
    Spinner hobbiesAddSpinner;
    LinearLayout hobbiesLayout;
    Button saveChangesButton;

    View rootView;

    User user = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        firstNameText = rootView.findViewById(R.id.first_name);
        lastNameText = rootView.findViewById(R.id.last_name);
        descriptionProfile = rootView.findViewById(R.id.profile_description);
        hobbiesAddSpinner = rootView.findViewById(R.id.spinner_prof_hobby);
        hobbiesLayout = rootView.findViewById(R.id.hobbiesLayout);
        saveChangesButton = rootView.findViewById(R.id.saveChangesButton);

        mUser = mAuth.getCurrentUser();
        searchForUserDatabase(mUser.getUid());
        ArrayList<String> hobbiesArray = new ArrayList<String>();
        for (Hobbies h : Hobbies.values()) {
            hobbiesArray.add(h.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, hobbiesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        hobbiesAddSpinner.setAdapter(adapter);

        if (user != null && user.hobbies != null) {
            setHobbiesSpinner();
        }

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveChanges(user);
                user.description = descriptionProfile.getText().toString();
            }
        });
         /*
        TODO:
        Get reference to realtime database and authorization database
        Fill texts and hobbies with the correct database values using current user uid
        Create a button to add hobbies
        Update properties in realtime database
         */


        // Inflate the layout for this fragment
        return rootView;

    }

    void setHobbiesSpinner() {
        ArrayList<String> hobbiesArray = new ArrayList<String>();
        for (Hobbies h : Hobbies.values()) {
            hobbiesArray.add(h.name());
        }
        hobbiesAddSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(user.hobbies != null && !user.hobbies.contains(Hobbies.valueOf(hobbiesArray.get(i))))
                {
                    Hobbies hobbies = Hobbies.valueOf(hobbiesArray.get(i));
                    user.hobbies.add(hobbies);
                    addHobby(hobbies);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void searchForUserDatabase(String uid) {
        Log.println(Log.INFO, "Uid of current user", uid);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Hobbies> hobbies = new ArrayList<Hobbies>();
                ArrayList<Event> joinedEvents = new ArrayList<Event>();
                String firstName = snapshot.child("users").child(uid).child("firstName").getValue(String.class);
                String lastName = snapshot.child("users").child(uid).child("lastName").getValue(String.class);
                String description = snapshot.child("users").child(uid).child("description").getValue(String.class);
                ArrayList<String> hb = (ArrayList<String>) snapshot.child("users").child(uid).child("hobbies").getValue();

                if (hb != null) {
                    for (String h: hb) {
                        Hobbies hobby = Hobbies.valueOf(h);
                        hobbies.add(hobby);
                    }
                }


//                if (snapshot.child("users").child(uid).child("events").getValue(Hobbies.class) != null) {
//                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                        Event e = postSnapshot.getValue(Event.class);
//                        joinedEvents.add(e);
//                    }
//                }

                // Initialize UI components after data retrieval
                user = new User(firstName, lastName, description, hobbies, joinedEvents);
                firstNameText.setText("First Name: " + user.firstName);
                lastNameText.setText("Last Name: " + user.lastName);
                descriptionProfile.setText(user.description);
                setHobbiesSpinner();
                loadHobbies(user.hobbies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

    }

    void loadHobbies(ArrayList<Hobbies> _hobbies)
    {
        for(Hobbies h : _hobbies)
        {
            TextView hobbyText = new TextView(hobbiesLayout.getContext());
            hobbyText.setText(h.name());
            hobbiesLayout.addView(hobbyText);
        }
    }

    void addHobby(Hobbies h) {
        TextView hobbyText = new TextView(hobbiesLayout.getContext());
        hobbyText.setText(h.name());
        hobbiesLayout.addView(hobbyText);
    }


    void SaveChanges(User u)
    {
        database.child("users").child(mUser.getUid()).child("description").setValue(descriptionProfile.getText().toString());
        database.child("users").child(mUser.getUid()).child("hobbies").setValue(u.hobbies);
        database.child("users").child(mUser.getUid()).child("joinedEvents").setValue(u.joinedEvents);
        Toast.makeText(rootView.getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
    }



}