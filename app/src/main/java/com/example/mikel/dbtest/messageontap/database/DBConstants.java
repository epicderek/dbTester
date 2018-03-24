package com.example.mikel.dbtest.messageontap.database;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * All the constants representing the specific fields of, Person, Place, and Event, and their corresponding identical column names in the tables of the database.
 */
public class DBConstants
{
    /**
     * Table name for Person objects.
     */
    public static final String TABLE_PERSONS = "persons";

    /*
        Column names for persons table.
     */
    public static final String KEY_PID = "pid";
    public static final String KEY_CONTACT_ID = "contact_id";
    public static final String KEY_PERSON_NAME = "person_name";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_EMAIL_ADDRESS = "email_address";
    public static final String KEY_WHATSAPP_USER = "what's app username";
    public static final String KEY_FACEBOOK_USER = "facebook username";
    /**
     * All the column names of the persons table.
     */
    public static final Set<String> PERSON_KEYS = new HashSet<>();
    // Events table name
    public static final String TABLE_EVENTS = "events";
    // Events Table Columns names
    public static final String KEY_EID = "eid";
    public static final String KEY_CALENDAR_ID = "calendar_id";
    public static final String KEY_EVENT_TYPE = "event_type";
    public static final String KEY_EVENT_NAME = "event_name";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";

    public static final Set<String> EVENT_KEYS = new HashSet<>();
    // Places table name
    public static final String TABLE_PLACES = "places";

    // Places Table Columns names
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LNG = "longitude";
    public static final String KEY_PLACE_NAME = "place_name";
    public static final String KEY_PLACE_TYPE = "place_type";
    public static final String KEY_STREET_NUM = "street number";
    public static final String KEY_ROUTE = "route";
    public static final String KEY_NEIGHBORHOOD = "neighborhood";
    public static final String KEY_LOCALITY = "locality";
    public static final String KEY_ADMINISTRATIVE2 = "county";
    public static final String KEY_ADMINISTRATIVE1 = "state";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_ZIP = "zip";
    public static final String KEY_STREET_ADDRESS = "street_address";
    public static final String KEY_PLID = "plid";
    public static final Set<String> PLACE_KEYS = new HashSet<>();


    // Events Person table name
    public static final String TABLE_EVENT_PERSON = "event_person";

    // EventPerson Relationship Table Columns names
    public static final String KEY_EP_RID = "ep_rid";
    public static final String KEY_EP_EVENT_ID = "ep_event_id";
    public static final String KEY_EP_PERSON_ID = "ep_person_id";
    public static final String KEY_EVENT_PERSON_RELATIONSHIP_TYPE = "event_person_relationship_type";
    /**
     * All the column names for the Event Person Relationship table.
     */
    public static final Set<String> EP_KEYS = new HashSet<>();


    // Person Place table name
    public static final String TABLE_PERSON_PLACE = "person_place";
    // PersonPlace Relationship Table Columns names
    public static final String KEY_PP_RID = "pp_rid";
    public static final String KEY_PP_PLACE_ID = "pp_place_id";
    public static final String KEY_PP_PERSON_ID = "pp_person_id";
    public static final String KEY_PERSON_PLACE_RELATIONSHIP_TYPE = "person_place_relationship_type";
    /**
     * All the columnn names for the Person Place Relationship table.
     */
    public static final Set<String> PP_KEYS = new HashSet<>();

    // Place Event table name
    public static final String TABLE_PLACE_EVENT = "place_event";
    // PlaceEvent Relationship Table Columns names
    public static final String KEY_PE_RID = "pe_rid";
    public static final String KEY_PE_EVENT_ID = "pe_event_id";
    public static final String KEY_PE_PLACE_ID = "pe_place_id";
    public static final String KEY_PLACE_EVENT_RELATIONSHIP_TYPE = "place_event_relationship_type";
    /**
     * All the column names for the Place Event Relationship table.
     */
    public static final Set<String> PE_KEYS = new HashSet<>();

    static
    {
        Collections.addAll(PLACE_KEYS,KEY_LAT,KEY_LNG,KEY_PLACE_NAME,KEY_PLACE_TYPE,KEY_STREET_NUM,KEY_ROUTE,KEY_NEIGHBORHOOD,KEY_LOCALITY,KEY_ADMINISTRATIVE2,KEY_ADMINISTRATIVE1,KEY_COUNTRY,KEY_ZIP,KEY_STREET_ADDRESS,KEY_PLID);
        Collections.addAll(PERSON_KEYS,KEY_PID,KEY_CONTACT_ID,KEY_PERSON_NAME,KEY_PHONE_NUMBER,KEY_EMAIL_ADDRESS,KEY_WHATSAPP_USER,KEY_FACEBOOK_USER);
        Collections.addAll(EVENT_KEYS,KEY_EID,KEY_CALENDAR_ID,KEY_EVENT_TYPE,KEY_EVENT_NAME,KEY_START_TIME,KEY_END_TIME);
        Collections.addAll(EP_KEYS,KEY_EP_RID,KEY_EP_PERSON_ID,KEY_EP_EVENT_ID,KEY_EVENT_PERSON_RELATIONSHIP_TYPE);
        Collections.addAll(PP_KEYS,KEY_PP_RID,KEY_PP_PLACE_ID,KEY_PP_PERSON_ID,KEY_PERSON_PLACE_RELATIONSHIP_TYPE);
        Collections.addAll(PE_KEYS,KEY_PE_RID,KEY_PE_PLACE_ID,KEY_PE_EVENT_ID,KEY_EVENT_PERSON_RELATIONSHIP_TYPE);
    }

}
