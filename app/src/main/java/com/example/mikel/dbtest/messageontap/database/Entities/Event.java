package com.example.mikel.dbtest.messageontap.database.Entities;
import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;
import com.example.mikel.dbtest.messageontap.data.Entity;
import java.util.Map;

/**
 * Class for SQL DB compensate the work of Personal Graph
 */

public class Event extends Entity
{

    /**
     * Create an Event Object from raw data.
     * @param eid The event id.
     * @param calendarId The calendar id.
     * @param eventName The name of the event.
     * @param type The type of the event.
     * @param startTime The starting time of the event.
     * @param endTime The ending time of the event.
     */
    public Event(long eid, long calendarId, String eventName, String type, long startTime, long endTime)
    {
        super();
        Map<String,Object> record = getItemMap();
        record.put(KEY_EID,eid);
        record.put(KEY_CALENDAR_ID,calendarId);
        record.put(KEY_EVENT_NAME,eventName);
        record.put(KEY_EVENT_TYPE,type);
        record.put(KEY_START_TIME,startTime);
        record.put(KEY_END_TIME,endTime);
    }

    /**
     * A preferred way of reconstruction of an Event Object from the database.
     * @param fvals The fields and their associated values of this Event Object.
     */
    public Event(Map<String,Object> fvals)
    {
        super();
        for(String holder: fvals.keySet())
            if(EVENT_KEYS.contains(holder))
                throw new RuntimeException("Invalid Field for a Event Object");
        Map<String,Object> record = getItemMap();
        record.putAll(fvals);
    }

}
