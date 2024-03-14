package telkadjiite.hacktuesx.prilojenie;

import java.util.List;

public class User {

    public String firstName;
    public String lastName;
    public String description;
    public List<Hobbies> hobbies;
    public List<Event> joinedEvents; // Includes your events however there will be a check if you're the owner


    // User is created with first name and last name the description and hobbies are null because they will be set in the profile page
    public User(String _firstName, String _lastName)
    {
        firstName = _firstName;
        lastName = _lastName;
        description = null;
        hobbies = null;
    }



}
