package com.example.mikel.dbtest.messageontap.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;
import com.example.mikel.dbtest.messageontap.database.Relationships.*;
import com.example.mikel.dbtest.messageontap.database.Entities.*;

/**
 * This is the DB for person and event and person event relationship
 *
 */

public class DBHandler extends SQLiteOpenHelper
{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBHandler";
    // Database Name
    private static final String DATABASE_NAME = "PG";

    //Constructor
    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_PERSONS_TABLE = "CREATE TABLE " + TABLE_PERSONS + "("
                + KEY_PID + " INTEGER PRIMARY KEY," + KEY_CONTACT_ID + " INTEGER,"
                + KEY_PERSON_NAME + " TEXT,"+ KEY_PHONE_NUMBER + " TEXT,"
                + KEY_EMAIL_ADDRESS + " TEXT," + KEY_WHATSAPP_USER + " TEXT," + KEY_FACEBOOK_USER + " TEXT"
                + ")";

        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_EID + " INTEGER PRIMARY KEY," + KEY_CALENDAR_ID + " INTEGER,"
                + KEY_EVENT_NAME + " TEXT,"+ KEY_EVENT_TYPE + " TEXT,"
                + KEY_START_TIME + " INTEGER,"+ KEY_END_TIME + " INTEGER"
                + ")";
        String CREATE_PLACES_TABLE = String.format("create table %s ( %s real unsigned, %s real unsigned on conflict replace, %s text, %s text, %s integer, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s integer, primary key (%s, %s)",TABLE_PLACES,KEY_LAT,KEY_LNG,KEY_PLACE_NAME,KEY_PLACE_TYPE,KEY_STREET_NUM,KEY_ROUTE,KEY_NEIGHBORHOOD,KEY_LOCALITY,KEY_ADMINISTRATIVE2,KEY_ADMINISTRATIVE1,KEY_COUNTRY,KEY_ZIP,KEY_STREET_ADDRESS,KEY_PLID,KEY_LAT,KEY_LNG);

        String CREATE_EVENT_PERSON_TABLE = "CREATE TABLE " + TABLE_EVENT_PERSON + "("
                + KEY_EP_RID + " INTEGER PRIMARY KEY, " +  KEY_EP_EVENT_ID+ " INTEGER,"
                //REFERENCES "+ PersonDBHandler.TABLE_PERSONS+"("+ KEY_PID + "), "
                + KEY_EP_PERSON_ID + " INTEGER, "+ KEY_EVENT_PERSON_RELATIONSHIP_TYPE + " TEXT, "
                + "FOREIGN KEY ("+ KEY_EP_PERSON_ID
                + ") REFERENCES "+ TABLE_PERSONS+"("+KEY_PID + "), "
                + "FOREIGN KEY ("+ KEY_EP_EVENT_ID + ") REFERENCES "+TABLE_EVENTS
                +"("+KEY_EID + ")"
                + ");";

        String CREATE_PERSON_PLACE_TABLE = "CREATE TABLE " + TABLE_PERSON_PLACE + "("
                + KEY_PP_RID + " INTEGER PRIMARY KEY, " + KEY_PP_PERSON_ID + " INTEGER,"
                //REFERENCES "+ PersonDBHandler.TABLE_PERSONS+"("+ KEY_PID + "), "
                + KEY_PP_PLACE_ID + " INTEGER, "+ KEY_PERSON_PLACE_RELATIONSHIP_TYPE + " TEXT, "
                + "FOREIGN KEY ("+ KEY_PP_PERSON_ID
                + ") REFERENCES "+ TABLE_PERSONS+"("+KEY_PID + "), "
                + "FOREIGN KEY ("+ KEY_PP_PLACE_ID + ") REFERENCES "+ TABLE_PLACES
                +"("+KEY_PLID + ")"
                + ");";
        String CREATE_PLACE_EVENT_TABLE = "CREATE TABLE " + TABLE_PLACE_EVENT + "("
                + KEY_PE_RID + " INTEGER PRIMARY KEY, " + KEY_PE_PLACE_ID + " INTEGER,"
                //REFERENCES "+ PersonDBHandler.TABLE_PERSONS+"("+ KEY_PID + "), "
                + KEY_PE_EVENT_ID + " INTEGER, "+ KEY_PLACE_EVENT_RELATIONSHIP_TYPE + " TEXT, "
                + "FOREIGN KEY ("+ KEY_PE_PLACE_ID
                + ") REFERENCES "+ TABLE_PLACES+"("+KEY_PLID + "), "
                + "FOREIGN KEY ("+ KEY_PE_EVENT_ID + ") REFERENCES "+ TABLE_EVENTS
                +"("+KEY_EID + ")"
                + ");";
        db.execSQL(CREATE_PLACES_TABLE);
        db.execSQL(CREATE_PERSONS_TABLE);
        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_EVENT_PERSON_TABLE);
        db.execSQL(CREATE_PERSON_PLACE_TABLE);
        db.execSQL(CREATE_PLACE_EVENT_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=1;");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV )
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        // Create tables again
        onCreate(db);
    }


    // Add Person
    public void addPerson(Person person)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_PID,(long)person.getValueByField(KEY_PID));
        values.put(KEY_CONTACT_ID,(long)person.getValueByField(KEY_CONTACT_ID));
        values.put(KEY_PERSON_NAME,(String)person.getValueByField(KEY_PERSON_NAME));
        values.put(KEY_PHONE_NUMBER,(String)person.getValueByField(KEY_PHONE_NUMBER));
        values.put(KEY_EMAIL_ADDRESS,(String)person.getValueByField(KEY_EMAIL_ADDRESS));
        values.put(KEY_WHATSAPP_USER,(String)person.getValueByField(KEY_WHATSAPP_USER));
        values.put(KEY_FACEBOOK_USER,(String)person.getValueByField(KEY_FACEBOOK_USER));
        db.insert(TABLE_PERSONS, null, values);
        db.close();
    }

    public Person getPersonByPId(long pid)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSONS, null, KEY_PID + "=?",
                new String[] { String.valueOf(pid) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Person person = new Person(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), cursor.getString(2),cursor.getString(3),
                cursor.getString(4),cursor.getString(5),cursor.getString(6));
        cursor.close();
        db.close();
        // return Person
        return person;
    }

    // Get Person by Contact ID
    public Person getPersonByCalendarId(long contactId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSONS, null, KEY_CONTACT_ID + "=?",
                new String[] { String.valueOf(contactId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Person person = new Person(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), cursor.getString(2),cursor.getString(3),
                cursor.getString(4),cursor.getString(5),cursor.getString(6));
        cursor.close();
        db.close();
        // return CalendarEvent
        return person;
    }

    /**
     * Retrieve a person from the database by his/her phone number. If no such person exsits, null is returned.
     * @param dial The phone number of such person in search of.
     * @return The reconstruction of the person as a native object.
     */
    public Person getPersonByDial(String dial)
    {
        SQLiteDatabase db = getReadableDatabase();
        String where = String.format("%s = ?",KEY_PHONE_NUMBER);
        String[] vals = {dial};
        Cursor cur = db.query(TABLE_PERSONS,null,where,vals,null,null,null);
       if(cur.moveToNext())
           return new Person(cur.getLong(0),cur.getLong(1),cur.getString(2),cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6));
       return null;
    }

    /**
     * Retrieve a person from the database by his/her whatsapp username. If no such person exsits, null is returned.
     * @param user The whatsapp username of such person in search of.
     * @return The reconstruction of the person as a native object.
     */
    public Person getPersonByWhatsappUser(String user)
    {
        SQLiteDatabase db = getReadableDatabase();
        String where = String.format("%s = ?",KEY_WHATSAPP_USER);
        String[] vals = {user};
        Cursor cur = db.query(TABLE_PERSONS,null,where,vals,null,null,null);
        if(cur.moveToNext())
            return new Person(cur.getLong(0),cur.getLong(1),cur.getString(2),cur.getString(3),cur.getString(4),cur.getString(5),cur.getString(6));
        return null;
    }

    /**
     * Retrieve a person from the database by his/her facebook username. If no such person exsits, null is returned.
     * @param user The facebook username of such person in search of.
     * @return The reconstruction of the person as a native object.
     */
    public Person getPersonByFacebookUser(String user) {
        SQLiteDatabase db = getReadableDatabase();
        String where = String.format("%s = ?", KEY_FACEBOOK_USER);
        String[] vals = {user};
        Cursor cur = db.query(TABLE_PERSONS, null, where, vals, null, null, null);
        if (cur.moveToNext())
            return new Person(cur.getLong(0), cur.getLong(1), cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5), cur.getString(6));
        return null;
    }

    // Getting All Persons
    public List<Person> getAllPersons()
    {
        List<Person> personList = new ArrayList<Person>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERSONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(Long.parseLong(cursor.getString(0)),Long.parseLong(cursor.getString(1)),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
                // Adding person to list
                personList.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return Person list
        return personList;
    }

    // Updating single Person
    public int updatePerson(Person person)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTACT_ID, (long)person.getValueByField(KEY_CONTACT_ID));
        values.put(KEY_PERSON_NAME, (String)person.getValueByField(KEY_PERSON_NAME));
        values.put(KEY_PHONE_NUMBER, (String)person.getValueByField(KEY_PHONE_NUMBER));
        values.put(KEY_EMAIL_ADDRESS, (String)person.getValueByField(KEY_EMAIL_ADDRESS));
        values.put(KEY_WHATSAPP_USER,(String)person.getValueByField(KEY_WHATSAPP_USER));
        values.put(KEY_FACEBOOK_USER,(String)person.getValueByField(KEY_FACEBOOK_USER));
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_PID + " = ?",
                new String[] { String.valueOf(person.getValueByField(KEY_PID)) });
    }

    // Updating Person Name by Contact ID
    public int updatePersonNameByContactId(String personName, long contactId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_NAME, personName);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_CONTACT_ID + " = ?",
                new String[] { String.valueOf(contactId) });
    }
    // Updating Person Phone Number by Contact ID
    public int updatePersonPhoneNumberByContactId(String phoneNumber, long contactId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHONE_NUMBER, phoneNumber);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_CONTACT_ID + " = ?",
                new String[] { String.valueOf(contactId) });
    }
    // Updating Person Email Address by Contact ID
    public int updatePersonEmailAddressByContactId(String emailAddress, long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL_ADDRESS, emailAddress);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_CONTACT_ID + " = ?",
                new String[] { String.valueOf(contactId) });
    }

    // Updating Person Name by pid
    public int updatePersonNameByPId(String personName, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_NAME, personName);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    // Updating Person Phone Number by pid
    public int updatePersonPhoneNumberByPId(String phoneNumber, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHONE_NUMBER, phoneNumber);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }
    // Updating Person Email Address by pid
    public int updatePersonEmailAddressByPId(String emailAddress, long pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL_ADDRESS, emailAddress);
        // updating row
        return db.update(TABLE_PERSONS, values, KEY_PID + " = ?",
                new String[] { String.valueOf(pid) });
    }

    //-*******************************Events*****************************************
    // Add Event
    public void addEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_EID,(long)event.getValueByField(KEY_EID));
        values.put(KEY_CALENDAR_ID,(long)event.getValueByField(KEY_CALENDAR_ID));
        values.put(KEY_EVENT_NAME,(String)event.getValueByField(KEY_EVENT_NAME));
        values.put(KEY_EVENT_TYPE,(String)event.getValueByField(KEY_EVENT_TYPE));
        values.put(KEY_START_TIME,(long)event.getValueByField(KEY_START_TIME));
        values.put(KEY_END_TIME,(long)event.getValueByField(KEY_END_TIME));
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    public Event getEventByEId(long eid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_EID,
                        KEY_CALENDAR_ID, KEY_EVENT_NAME, KEY_EVENT_TYPE, KEY_START_TIME, KEY_END_TIME }, KEY_EID + "=?",
                new String[] { String.valueOf(eid) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Event event = new Event(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), cursor.getString(2),cursor.getString(3),
                Long.parseLong(cursor.getString(4)), Long.parseLong(cursor.getString(5)));
        cursor.close();
        db.close();
        // return Event
        return event;
    }
    // Get Event by Calendar ID
    public Event getEventByCalendarId(long calendarId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_EID,
                        KEY_CALENDAR_ID, KEY_EVENT_NAME, KEY_EVENT_TYPE, KEY_START_TIME, KEY_END_TIME }, KEY_CALENDAR_ID + "=?",
                new String[] { String.valueOf(calendarId) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Event event = new Event(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), cursor.getString(2),cursor.getString(3),
                Long.parseLong(cursor.getString(4)), Long.parseLong(cursor.getString(5)));
        cursor.close();
        db.close();
        // return CalendarEvent
        return event;
    }

    /**
     * Filter a series of events within the range of this time interval.
     * @param start The start time of the interval.
     * @param end The end time of the interval.
     * @return A list of events within the given lapse of time.
     */
    public List<Event> getEventsByTime(long start, long end)
    {
        SQLiteDatabase db = getReadableDatabase();
        String where = String.format("%s > ? and %s < ?");
        String[] vals = {String.valueOf(start),String.valueOf(end)};
        Cursor cur = db.query(TABLE_EVENTS,null,where,vals,null,null,null);
        List<Event> eves = new LinkedList<>();
        while(cur.moveToNext())
            eves.add(new Event(cur.getLong(0),cur.getLong(1),cur.getString(2),cur.getString(3),cur.getLong(4),cur.getLong(5)));
        return eves;
    }

    /**
     * Filter a series of events under this event name.
     * @param name The name of the event.
     * @return The list of events described by the given name.
     */
    public List<Event> getEventsByName(String name)
    {
        SQLiteDatabase db = getReadableDatabase();
        String where = String.format("%s = ?");
        String[] vals = {name};
        Cursor cur = db.query(TABLE_EVENTS,null,where,vals,null,null,null);
        List<Event> eves = new LinkedList<>();
        while(cur.moveToNext())
            eves.add(new Event(cur.getLong(0),cur.getLong(1),cur.getString(2),cur.getString(3),cur.getLong(4),cur.getLong(5)));
        return eves;
    }

    /**
     * Filter a series of events under this event type.
     * @param type The type of the event.
     * @return The list of events with the given name.
     */
    public List<Event> getEventsByType(String type)
    {
        SQLiteDatabase db = getReadableDatabase();
        String where = String.format("%s = ?");
        String[] vals = {type};
        Cursor cur = db.query(TABLE_EVENTS,null,where,vals,null,null,null);
        List<Event> eves = new LinkedList<>();
        while(cur.moveToNext())
            eves.add(new Event(cur.getLong(0),cur.getLong(1),cur.getString(2),cur.getString(3),cur.getLong(4),cur.getLong(5)));
        return eves;
    }

    // Getting All Events
    public List<Event> getAllEvents()
    {
        List<Event> EventList = new ArrayList<Event>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event(Long.parseLong(cursor.getString(0)),Long.parseLong(cursor.getString(1)),cursor.getString(2),cursor.getString(3),Long.parseLong(cursor.getString(4)),Long.parseLong(cursor.getString(5)));
                // Adding Event to list
                EventList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return Event list
        return EventList;
    }

    // Updating single Event
    public int updateEvent(Event event)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CALENDAR_ID,(long)event.getValueByField(KEY_CALENDAR_ID));
        values.put(KEY_EVENT_NAME,(String)event.getValueByField(KEY_EVENT_NAME));
        values.put(KEY_EVENT_TYPE,(String)event.getValueByField(KEY_EVENT_TYPE));
        values.put(KEY_START_TIME,(long)event.getValueByField(KEY_START_TIME));
        values.put(KEY_END_TIME,(long)event.getValueByField(KEY_END_TIME));
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(event.getValueByField(KEY_EID)) });
    }

    // Updating event's name by its event id
    public int updateEventNameByEId(String eventName, long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_NAME, eventName);
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
    }

    // Updating event's type by its event id
    public int updateTypeByEId(String type, long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_TYPE, type);
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
    }

    // Updating event's start time by its event id
    public int updateStartTimeByEId(long startTime, long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_START_TIME, startTime);
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
    }
    // Updating event's end time by its event id
    public int updateEndTimeByEId(long endTime, long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_END_TIME, endTime);
        // updating row
        return db.update(TABLE_EVENTS, values, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
    }

    // Deleting single Event
    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_EID + " = ?",
                new String[] { String.valueOf(event.getValueByField(KEY_EID)) });
        db.close();
    }
    // Deleting single Event
    public void deleteEventByEId(long eid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_EID + " = ?",
                new String[] { String.valueOf(eid) });
        db.close();
    }
    // Getting CalendarEvents Count
    public int getEventsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    //*********************************Places*************************************************

    /**
     * Browse the database to check for any existent Place identical to this Place within the tolerance of 0.0001 in both latitude and longitude.
     * @param lat The latitude of the Place.
     * @param lon The longitude of the Place.
     * @return A cursor that might contain the Place queried.
     */
    public Cursor dupSearch(double lat, double lon)
    {
        SQLiteDatabase db = getReadableDatabase();
        //The where statement in which the allowance of the difference in position is specified.
        String where = String.format("%s-%s <= ? and %s-%s <= ?",KEY_LAT,lat,KEY_LNG,lon);
        String[] allow = {String.valueOf(0.0001),String.valueOf(0.0001)};
        Cursor cur = db.query(TABLE_PLACES,null,where,allow,null,null,String.format("LAT-%s+LNG-%s",lat,lon));
        return cur;
    }

    /**
     * Record this Place information in the sql database and return the loc_id of this Place. If the Place already exists in the database, the id of that Place is returned.
     * @param loc The Place to be recorded in the database.
     *@return The assigned place id of this place.
     */
    public long addPlace(Place loc)
    {
        Cursor cur = dupSearch((double)loc.getValueByField(KEY_LAT),(double)loc.getValueByField(KEY_LNG));
        //If the Place already exists in the database.
        if(cur.moveToNext())
            return cur.getLong(13);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LAT,(long)loc.getValueByField(KEY_LAT));
        values.put(KEY_LNG,(long)loc.getValueByField(KEY_LNG));
        values.put(KEY_PLACE_NAME,(String)loc.getValueByField(KEY_PLACE_NAME));
        values.put(KEY_PLACE_TYPE,(String)loc.getValueByField(KEY_PLACE_TYPE));
        values.put(KEY_STREET_NUM,(int)loc.getValueByField(KEY_STREET_NUM));
        values.put(KEY_ROUTE,(String)loc.getValueByField(KEY_ROUTE));
        values.put(KEY_NEIGHBORHOOD,(String)loc.getValueByField(KEY_NEIGHBORHOOD));
        values.put(KEY_LOCALITY,(String)loc.getValueByField(KEY_LOCALITY));
        values.put(KEY_ADMINISTRATIVE2,(String)loc.getValueByField(KEY_ADMINISTRATIVE2));
        values.put(KEY_ADMINISTRATIVE1,(String)loc.getValueByField(KEY_ADMINISTRATIVE1));
        values.put(KEY_COUNTRY,(String)loc.getValueByField(KEY_COUNTRY));
        values.put(KEY_ZIP,(String)loc.getValueByField(KEY_ZIP));
        values.put(KEY_STREET_ADDRESS,(String)loc.getValueByField(KEY_STREET_ADDRESS));
        loc.setFieldValue(KEY_PLID,db.insert(TABLE_PLACES,null,values));
        return loc.getValueByField(KEY_PID);
    }

    /**
     * Retrieve a Place from the database by the locational id.
     * @param id The id of the location to be retrieved.
     * @return The place correlated with this locational id. Null if no profile of such person with such id exists.
     */
    public Place getPlaceById(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String where = String.format("%s = ?",KEY_PLID);
        String[] cons = {String.valueOf(id)};
        Cursor cur = db.query(TABLE_PLACES,null,where,cons,null,null,null);
        Object[] que = new Object[14];
        if(cur.moveToNext())
        {
            //The longitude and latitude.
            que[0] = cur.getDouble(0);
            que[1] = cur.getDouble(1);
            //The rest of the information in string format.
            for(int i=2; i<13; i++)
                que[i] = cur.getString(i);
            que[13] = cur.getLong(13);
            return processPlace(que);
        }
        return null;
    }

    /**
     * Process a Place retrieved from the database.
     * @param vals The values returned by the query. All information must be present. The sequence must be as it is stored in the database.
     * @return The Place equivalent to the information extracted from the database.
     */
    private static Place processPlace(Object[] vals)
    {
        Map<String,Object> fvals = new HashMap<String,Object>();
        fvals.put(KEY_LAT,vals[0]);
        fvals.put(KEY_LNG,vals[1]);
        fvals.put(KEY_PLACE_NAME,vals[2]);
        fvals.put(KEY_PLACE_TYPE,vals[3]);
        fvals.put(KEY_STREET_NUM,vals[4]);
        fvals.put(KEY_ROUTE,vals[5]);
        fvals.put(KEY_NEIGHBORHOOD,vals[6]);
        fvals.put(KEY_LOCALITY,vals[7]);
        fvals.put(KEY_ADMINISTRATIVE2,vals[8]);
        fvals.put(KEY_ADMINISTRATIVE1,vals[9]);
        fvals.put(KEY_COUNTRY,vals[10]);
        fvals.put(KEY_ZIP,vals[11]);
        fvals.put(KEY_STREET_ADDRESS,vals[12]);
        fvals.put(KEY_PLID,vals[13]);
        return new Place(fvals);
    }

    /**
     * Retrieve and reconstruct all places under the given place name.
     * @param name The place name to be inquired.
     * @return The list of places under this place name.
     */
    public List<Place> getPlacesByName(String name)
    {
        SQLiteDatabase db = getReadableDatabase();
        List<Place> plcs = new LinkedList<>();
        String where = String.format("%s = ?",KEY_PLACE_NAME);
        String[] vals = {name};
        Cursor cur = db.query(TABLE_PLACES,null,where,vals,null,null,null);
        Object[] que = new Object[14];
        while(cur.moveToNext())
        {
            //The longitude and latitude.
            que[0] = cur.getDouble(0);
            que[1] = cur.getDouble(1);
            //The rest of the information in string format.
            for(int i=2; i<13; i++)
                que[i] = cur.getString(i);
            que[13] = cur.getLong(13);
            plcs.add(processPlace(que));
        }
        return plcs;
    }

    // Getting All Places
    public List<Place> getAllPlaces()
    {
        List<Place> placeList = new ArrayList<Place>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLACES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setFieldValue(KEY_LAT,cursor.getDouble(0));
                place.setFieldValue(KEY_LNG,cursor.getDouble(1));
                place.setFieldValue(KEY_PLACE_NAME,cursor.getString(2));
                place.setFieldValue(KEY_PLACE_TYPE,cursor.getString(3));
                place.setFieldValue(KEY_STREET_NUM,cursor.getString(4));
                place.setFieldValue(KEY_ROUTE,cursor.getString(5));
                place.setFieldValue(KEY_NEIGHBORHOOD,cursor.getString(6));
                place.setFieldValue(KEY_LOCALITY,cursor.getString(7));
                place.setFieldValue(KEY_ADMINISTRATIVE2,cursor.getString(8));
                place.setFieldValue(KEY_ADMINISTRATIVE1,cursor.getString(9));
                place.setFieldValue(KEY_COUNTRY,cursor.getString(10));
                place.setFieldValue(KEY_ZIP,cursor.getString(11));
                place.setFieldValue(KEY_STREET_ADDRESS,cursor.getString(12));
                place.setFieldValue(KEY_PLID,cursor.getLong(13));
                // Adding person to list
                placeList.add(place);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return Place list
        return placeList;
    }

    /**
     * Update the primary name, the name directly stored in the Place object, of a Place by replacing the name.
     * @param name The new primary name to replace the previous preferred name.
     * @param id The id of this Place, whose preferred name is to be modified.
     */
    public void updatePlaceName(String name, long id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(KEY_PLACE_NAME,name);
        String where = String.format("%s = ?",KEY_PID);
        String[] cons = {String.valueOf(id)};
        db.update(TABLE_PLACES,vals,where,cons);
        db.close();
    }



    //*********************************EP Relationship*************************************************
    public void addEPRelationship(EventPersonRelationship relationship)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_EP_RID, (long)relationship.getValueByField(KEY_EP_RID));
        values.put(KEY_EP_PERSON_ID, (long)relationship.getValueByField(KEY_EP_PERSON_ID));
        values.put(KEY_EP_EVENT_ID, (long)relationship.getValueByField(KEY_EP_EVENT_ID));
        values.put(KEY_EVENT_PERSON_RELATIONSHIP_TYPE, (String)relationship.getValueByField(KEY_EVENT_PERSON_RELATIONSHIP_TYPE));
        db.insert(TABLE_EVENT_PERSON, null, values);
        db.close();
    }

    public EventPersonRelationship getEPRByRId(long rid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_PERSON, new String[] {KEY_EP_RID,
                         KEY_EP_EVENT_ID,KEY_EP_PERSON_ID, KEY_EVENT_PERSON_RELATIONSHIP_TYPE},
                KEY_EP_RID + "=?",
                new String[] { String.valueOf(rid) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        EventPersonRelationship epr = new EventPersonRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return epr;
    }

    public EventPersonRelationship getEPRByEPId(long pid, long eid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_PERSON, new String[] {KEY_EP_RID,
                        KEY_EP_EVENT_ID,KEY_EP_PERSON_ID, KEY_EVENT_PERSON_RELATIONSHIP_TYPE},
                KEY_EP_PERSON_ID + "=? AND "+ KEY_EP_EVENT_ID +"=?",
                new String[] { String.valueOf(pid) }, String.valueOf(eid), null, null);
        if (cursor != null)
            cursor.moveToFirst();
        EventPersonRelationship epr = new EventPersonRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return epr;
    }


    public List<EventPersonRelationship> getAllEPR(){
        List<EventPersonRelationship> EPRList = new ArrayList<EventPersonRelationship>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENT_PERSON;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding person to list
                EPRList.add(new EventPersonRelationship(Long.parseLong(cursor.getString(0)),Long.parseLong(cursor.getString(2)),Long.parseLong(cursor.getString(1)),cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return EPR list
        return EPRList;
    }

    public List<EventPersonRelationship> getEPRByPid(long pid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_PERSON, new String[] {KEY_EP_RID,
                        KEY_EP_EVENT_ID,KEY_EP_PERSON_ID, KEY_EVENT_PERSON_RELATIONSHIP_TYPE},
                KEY_EP_PERSON_ID + "=?",
                new String[] { String.valueOf(pid) }, null, null, null);
        List<EventPersonRelationship> EPRList = new ArrayList<EventPersonRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    EventPersonRelationship epr = new EventPersonRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    EPRList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return EPRList
        return EPRList;
    }

    public List<EventPersonRelationship> getEPRByEid(long eid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT_PERSON, new String[] {KEY_EP_RID,
                        KEY_EP_EVENT_ID,KEY_EP_PERSON_ID, KEY_EVENT_PERSON_RELATIONSHIP_TYPE},
                KEY_EP_EVENT_ID + "=?",
                new String[] { String.valueOf(eid) }, null, null, null);
        List<EventPersonRelationship> EPRList = new ArrayList<EventPersonRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    EventPersonRelationship epr = new EventPersonRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    EPRList.add(epr);
                }while(cursor.moveToNext());
            }
        cursor.close();
        db.close();
        // return EPRList
        return EPRList;
    }

    // Updating EPR type by rid
    public int updateEPRTypeByRid(String type, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_PERSON_RELATIONSHIP_TYPE, type);
        // updating row
        return db.update(TABLE_EVENT_PERSON, values, KEY_EP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating EPR PersonID by rid
    public int updateEPRPersonIDByRid(long pid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EP_PERSON_ID, pid);
        // updating row
        return db.update(TABLE_EVENT_PERSON, values, KEY_EP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating EPR EventID by rid
    public int updateEPREventIDByRid(long eid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EP_EVENT_ID, eid);
        // updating row
        return db.update(TABLE_EVENT_PERSON, values, KEY_EP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    //*********************************PP Relationship*************************************************

    public void addPPRelationship(PersonPlaceRelationship relationship){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_PP_RID, (long)relationship.getValueByField(KEY_PP_RID));
        values.put(KEY_PP_PERSON_ID, (long)relationship.getValueByField(KEY_PP_PERSON_ID));
        values.put(KEY_PP_PLACE_ID, (long)relationship.getValueByField(KEY_PP_PLACE_ID));
        values.put(KEY_PERSON_PLACE_RELATIONSHIP_TYPE, (String)relationship.getValueByField(KEY_PERSON_PLACE_RELATIONSHIP_TYPE));
        db.insert(TABLE_PERSON_PLACE, null, values);
        db.close();
    }

    public PersonPlaceRelationship getPPRByRId(long rid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSON_PLACE, new String[] { KEY_PP_RID,
                        KEY_PP_PERSON_ID, KEY_PP_PLACE_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PP_RID + "=?",
                new String[] { String.valueOf(rid) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        PersonPlaceRelationship epr = new PersonPlaceRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return epr;
    }

    public PersonPlaceRelationship getPPRByEPId(long pid, long plid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSON_PLACE, new String[] { KEY_PP_RID,
                        KEY_PP_PERSON_ID, KEY_PP_PLACE_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PP_PERSON_ID + "=? AND "+ KEY_PP_PLACE_ID+"=?",
                new String[] { String.valueOf(pid) }, String.valueOf(plid), null, null);
        if (cursor != null)
            cursor.moveToFirst();
        PersonPlaceRelationship ppr = new PersonPlaceRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return ppr;
    }


    public List<PersonPlaceRelationship> getAllPPR(){
        List<PersonPlaceRelationship> PPRList = new ArrayList<PersonPlaceRelationship>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PERSON_PLACE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PPRList.add(new PersonPlaceRelationship(Long.parseLong(cursor.getString(0)),Long.parseLong(cursor.getString(1)),Long.parseLong(cursor.getString(2)),cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return EPR list
        return PPRList;
    }
    public List<PersonPlaceRelationship> getPPRByPid(long pid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSON_PLACE, new String[] { KEY_PP_RID,
                        KEY_PP_PERSON_ID, KEY_PP_PLACE_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PP_PERSON_ID + "=?",
                new String[] { String.valueOf(pid) }, null, null, null);
        List<PersonPlaceRelationship> EPRList = new ArrayList<PersonPlaceRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    PersonPlaceRelationship epr = new PersonPlaceRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    EPRList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return EPRList
        return EPRList;
    }

    public List<PersonPlaceRelationship> getPPRByPLid(long plid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSON_PLACE, new String[] { KEY_PP_RID,
                        KEY_PP_PERSON_ID, KEY_PP_PLACE_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PP_PLACE_ID + "=?",
                new String[] { String.valueOf(plid) }, null, null, null);
        List<PersonPlaceRelationship> EPRList = new ArrayList<PersonPlaceRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    PersonPlaceRelationship epr = new PersonPlaceRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    EPRList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return EPRList
        return EPRList;
    }

    // Updating PPR type by rid
    public int updatePPRTypeByRid(String type, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_PLACE_RELATIONSHIP_TYPE, type);
        // updating row
        return db.update(TABLE_PERSON_PLACE, values, KEY_PP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating PPR PersonID by rid
    public int updatePPRPersonIDByRid(long pid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PP_PERSON_ID, pid);
        // updating row
        return db.update(TABLE_PERSON_PLACE, values, KEY_PP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating PPR EventID by rid
    public int updatePPRPlaceIDByRid(long plid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PP_PLACE_ID, plid);
        // updating row
        return db.update(TABLE_PERSON_PLACE, values, KEY_PP_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    //*********************************PE Relationship*************************************************

    public void addPERelationship(PlaceEventRelationship relationship){
        SQLiteDatabase db = this.getWritableDatabase();
        // Insert into the database by each row of the table
        ContentValues values = new ContentValues();
        values.put(KEY_PE_RID, (long)relationship.getValueByField(KEY_PE_RID));
        values.put(KEY_PE_PLACE_ID, (long)relationship.getValueByField(KEY_PE_PLACE_ID));
        values.put(KEY_PE_EVENT_ID, (long)relationship.getValueByField(KEY_PE_EVENT_ID));
        values.put(KEY_PLACE_EVENT_RELATIONSHIP_TYPE, (String)relationship.getValueByField(KEY_PLACE_EVENT_RELATIONSHIP_TYPE));
        db.insert(TABLE_PLACE_EVENT, null, values);
        db.close();
    }

    public PlaceEventRelationship getPERByRId(long rid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_EVENT, new String[] { KEY_PE_RID,
                         KEY_PE_PLACE_ID, KEY_PE_EVENT_ID, KEY_PERSON_PLACE_RELATIONSHIP_TYPE},
                KEY_PE_RID + "=?",
                new String[] { String.valueOf(rid) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        PlaceEventRelationship epr = new PlaceEventRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return epr;
    }

    public PlaceEventRelationship getPERByPEId(long plid, long eid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_EVENT, new String[] { KEY_PE_RID,
                        KEY_PE_PLACE_ID,KEY_PE_EVENT_ID, KEY_PLACE_EVENT_RELATIONSHIP_TYPE},
                KEY_PE_EVENT_ID + "=? AND "+ KEY_PE_PLACE_ID+"=?",
                new String[] { String.valueOf(eid) }, String.valueOf(plid), null, null);
        if (cursor != null)
            cursor.moveToFirst();
        PlaceEventRelationship ppr = new PlaceEventRelationship(Long.parseLong(cursor.getString(0)),
                Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3)
        );
        cursor.close();
        db.close();
        // return Relationship
        return ppr;
    }

    public List<PlaceEventRelationship> getAllPER(){
        List<PlaceEventRelationship> PERList = new ArrayList<PlaceEventRelationship>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PLACE_EVENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding person to list
                PERList.add(new PlaceEventRelationship(Long.parseLong(cursor.getString(0)),Long.parseLong(cursor.getString(1)),Long.parseLong(cursor.getString(2)),cursor.getString(3)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return PER list
        return PERList;
    }
    public List<PlaceEventRelationship> getPERByEid(long eid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_EVENT, new String[] { KEY_PE_RID,
                        KEY_PE_PLACE_ID,KEY_PE_EVENT_ID, KEY_PLACE_EVENT_RELATIONSHIP_TYPE},
                KEY_PE_EVENT_ID + "=?",
                new String[] { String.valueOf(eid) }, null, null, null);
        List<PlaceEventRelationship> PERList = new ArrayList<PlaceEventRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    PlaceEventRelationship epr = new PlaceEventRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    PERList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return PERList
        return PERList;
    }

    public List<PlaceEventRelationship> getPERByPLid(long plid){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACE_EVENT, new String[] { KEY_PE_RID,
                         KEY_PE_PLACE_ID,KEY_PE_EVENT_ID, KEY_PLACE_EVENT_RELATIONSHIP_TYPE},
                KEY_PE_PLACE_ID + "=?",
                new String[] { String.valueOf(plid) }, null, null, null);
        List<PlaceEventRelationship> PERList = new ArrayList<PlaceEventRelationship>();
        if (cursor != null)
            if(cursor.moveToFirst()){
                do{
                    PlaceEventRelationship epr = new PlaceEventRelationship(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)),cursor.getString(3));
                    PERList.add(epr);
                }while(cursor.moveToNext());

            }
        cursor.close();
        db.close();
        // return PERList
        return PERList;
    }

    // Updating PER type by rid
    public int updatePERTypeByRid(String type, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_EVENT_RELATIONSHIP_TYPE, type);
        // updating row
        return db.update(TABLE_PLACE_EVENT, values, KEY_PE_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating PER PlaceID by rid
    public int updatePERPlaceIDByRid(long plid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PE_PLACE_ID, plid);
        // updating row
        return db.update(TABLE_PLACE_EVENT, values, KEY_PE_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

    // Updating PER EventID by rid
    public int updatePEREventIDByRid(long eid, long rid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PE_EVENT_ID, eid);
        // updating row
        return db.update(TABLE_PLACE_EVENT, values, KEY_PE_RID + " = ?",
                new String[] { String.valueOf(rid) });
    }

}
