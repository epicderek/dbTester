package com.example.mikel.dbtest.messageontap.database.Relationships;
import java.util.Map;
import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;

/**
 * A relationship between a person and an event. .
 */

public class EventPersonRelationship extends Relationship
{
    /**
     * Create a relationship between an Event and a Person, specifically the participants or the the organizer of an event.
     * @param rid The relationship id of this relationship.
     * @param eventID  The id of the event.
     * @param personID The id of the person associated with this event.
     * @param type A brief description of this relationship.
     */
    public EventPersonRelationship(long rid, long eventID, long personID, String type)
    {
        super();
        Map<String,Object> record = getItemMap();
        record.put(KEY_EP_RID,rid);
        record.put(KEY_EP_PERSON_ID,personID);
        record.put(KEY_EP_EVENT_ID,eventID);
        record.put(KEY_EVENT_PERSON_RELATIONSHIP_TYPE,type);
    }


    /**
     * The preferred way of retrieval and reconstruction of this relationship from the database.
     * @param fvals The fields and associated values of this relationship.
     */
    public EventPersonRelationship(Map<String,Object> fvals)
    {
        super();
        Map<String,Object> record = getItemMap();
        for(String holder: fvals.keySet())
            if(!EP_KEYS.contains(holder))
                throw new RuntimeException("Invalid Field for a EventPersonRelationship Object");
        record.putAll(fvals);
    }


}
