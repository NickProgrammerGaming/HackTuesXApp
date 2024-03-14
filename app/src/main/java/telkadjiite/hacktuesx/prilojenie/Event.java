package telkadjiite.hacktuesx.prilojenie;

import java.util.List;

public class Event {
    public String title;
    public String description;
    public String location;
    public int neededPeople;
    public int currentNeededPeople;
    public Hobbies hobby;
    public User owner;
    public List<User> participants;


    public Event(String _title, String _description, String _location, int _neededPeople, Hobbies _hobby, User _owner)
    {
        title = _title;
        description = _description;
        location = _location;
        hobby = _hobby;
        owner = _owner;
        neededPeople = _neededPeople;
        currentNeededPeople = _neededPeople;
        participants.clear();// After creating the event there are no participants
        participants.add(owner); // Adding the owner as participant
    }



}
