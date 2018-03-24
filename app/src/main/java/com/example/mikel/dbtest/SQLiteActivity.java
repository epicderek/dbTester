package com.example.mikel.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.mikel.dbtest.messageontap.database.Entities.*;
import com.example.mikel.dbtest.messageontap.database.Relationships.*;
import com.example.mikel.dbtest.messageontap.database.DBHandler;
import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;

import java.util.List;

/**
 * Created by mikel on 2018/2/24.
 */

public class SQLiteActivity extends Activity
{
    @Override
    /**
     * A tester method that verifies the executions of the database.
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //ssetContentView(R.layout.activity_main);
        Log.e("In SQL Activity", "On create");
        DBHandler dbh = new DBHandler(this);

        /**
         *Insertion into database.
         **/
        Log.e("Insert: ", "Inserting ..");
        dbh.addEvent(new Event(1,77777771, "Fanglin's Birthday Party","A",1111111111,1111111117));
        dbh.addEvent(new Event(2,77777772, "Adam's Moving Day","A",1112111111,1112111117));
        dbh.addEvent(new Event(3,77777773, "Mars's Meeting","M",1112151111,1112151117));

        dbh.addPerson(new Person(1,2222221,"Mingquan Liu","8572049278","mikelmq99@gmail.com","minquan@whatsapp","mingquan@facebook"));
        dbh.addPerson(new Person(2,2222222,"Fanglin Chen","7777777777","chentc@gmail.com","fanglin@whatsapp","fanglin@facebook"));

        Place pla = new Place(40.441472,-79.8998760,"Home");
        dbh.addPlace(new Place(40.4505480,-79.8998760,"Previous Abode"));

        Log.e("Updating: ", "Updating  events 3");
        dbh.updateEventNameByEId("Mike's Holiday",3);

        Log.e("Updating: ", "Updating  Fanglin's phone number");
        dbh.updatePersonPhoneNumberByPId("5555555555",2);
        Log.e("Updating: ", "Updating  Mingquan's email address");
        dbh.updatePersonEmailAddressByContactId("mliu2@wpi.edu",2222221);

        Log.e("Updating: ", "Updating  Preferred name of Place");
        dbh.updatePlaceName("148 Elm St",0);

        // Reading all events
        Log.e("Reading: ", "Reading all events..");
        List<Event> events = dbh.getAllEvents();

        for (Event event : events) {
            String log = "Id: "+event.getValueByField(KEY_EID) + " ,Calendar ID: " + event.getValueByField(KEY_CALENDAR_ID) +
                    " ,Name: " + event.getValueByField(KEY_EVENT_NAME) + " ,Type: " + event.getValueByField(KEY_EVENT_TYPE)
                    + " ,Start Time: " + event.getValueByField(KEY_START_TIME) + " ,End Time: " + event.getValueByField(KEY_END_TIME);
            // Writing Events to log
            Log.e("Event: ", log);
        }
        // Reading all Persons
        Log.e("Reading: ", "Reading all Persons..");
        List<Person> persons = dbh.getAllPersons();

        for (Person person : persons) {
            String log = "Id: "+person.getValueByField(KEY_PID) + " ,Contact ID: " + person.getValueByField(KEY_CONTACT_ID) +
                    " ,Name: " + person.getValueByField(KEY_PERSON_NAME) + " ,Phone Number: " + person.getValueByField(KEY_PHONE_NUMBER)
                    + " ,Email Address: " + person.getValueByField(KEY_EMAIL_ADDRESS);
            // Writing Persons to log
            Log.e("Person: ", log);
        }

        // Reading all Places
        Log.e("Reading: ", "Reading all Places..");
        List<Place> places = dbh.getAllPlaces();

        for (Place place : places) {
            String log = "Id: "+place.getValueByField(KEY_PLID) + " ,Place Name: " + place.getValueByField(KEY_PLACE_NAME) +
                    " ,Coordinate: " + place.getValueByField(KEY_LAT) + " ," + place.getValueByField(KEY_LNG) + " ,Street Address: " +
                    place.getValueByField(KEY_STREET_ADDRESS) + " ,Type: " + place.getValueByField(KEY_PLACE_TYPE);
            // Writing Places to log
            Log.e("Places: ", log);
        }
    }
}
