package telkadjiite.hacktuesx.prilojenie;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event {
    public String title;
    public String description;
    public String location;
    public int neededPeople;
    public int currentNeededPeople;
    public Hobbies hobby;
    public User owner;
    public ArrayList<User> participants;

    public String id;



    public Event(String _title, String _description, String _location, int _neededPeople, Hobbies _hobby, User _owner)
    {
        id = UUID.randomUUID().toString();
        title = _title;
        description = _description;
        location = _location;
        hobby = _hobby;
        owner = _owner;
        neededPeople = _neededPeople;
        currentNeededPeople = _neededPeople;
        participants = new ArrayList<User>();
        participants.add(owner); // Adding the owner as participant
    }



}
