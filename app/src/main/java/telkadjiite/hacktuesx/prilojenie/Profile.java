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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        firstNameText = rootView.findViewById(R.id.first_name);
        lastNameText = rootView.findViewById(R.id.last_name);
        descriptionProfile = rootView.findViewById(R.id.profile_description);
        hobbiesAddSpinner = rootView.findViewById(R.id.spinner_prof_hobby);
        hobbiesLayout = rootView.findViewById(R.id.hobbiesLayout);
        saveChangesButton = rootView.findViewById(R.id.saveChangesButton);

        mUser = mAuth.getCurrentUser();
        User user = searchForUserDatabase(mUser.getUid());

        firstNameText.setText("First Name: " + user.firstName);
        lastNameText.setText("Last Name: " + user.lastName);
        descriptionProfile.setText(user.description);

        loadHobbies(user.hobbies);

        ArrayList<String> hobbiesArray = new ArrayList<String>();
        for(Hobbies h : Hobbies.values())
        {
            hobbiesArray.add(h.name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, hobbiesArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        hobbiesAddSpinner.setAdapter(adapter);

        hobbiesAddSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!user.hobbies.contains(Hobbies.valueOf(hobbiesArray.get(i))))
                {
                    user.hobbies.add(Hobbies.valueOf(hobbiesArray.get(i)));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    User searchForUserDatabase(String uid)
    {
        Log.println(Log.INFO, "Uid of current user", uid);
        final User[] user = new User[1];

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Hobbies> hobbies = new ArrayList<Hobbies>();
                ArrayList<Event> joinedEvents = new ArrayList<Event>();
                String firstName = snapshot.child("users").child(uid).child("firstName").getValue(String.class);
                String lastName = snapshot.child("users").child(uid).child("lastName").getValue(String.class);
                String description = snapshot.child("users").child(uid).child("description").getValue(String.class);
                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Hobbies h = postSnapshot.getValue(Hobbies.class);
                    hobbies.add(h);
                }
                for(DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Event e = postSnapshot.getValue(Event.class);
                    joinedEvents.add(e);
                }
                user[0] = new User(firstName, lastName, description, hobbies, joinedEvents);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  user[0];
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


    void SaveChanges(User u)
    {
        database.child("users").child(mUser.getUid()).child("description").setValue(descriptionProfile.getText().toString());
        database.child("users").child(mUser.getUid()).child("hobbies").setValue(u.hobbies);
        database.child("users").child(mUser.getUid()).child("joinedEvents").setValue(u.joinedEvents);
    }



}