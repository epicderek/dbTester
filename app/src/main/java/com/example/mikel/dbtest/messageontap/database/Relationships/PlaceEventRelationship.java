package com.example.mikel.dbtest.messageontap.database.Relationships;
import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;
import java.util.Map;

/**
 * This is the class for Event Person relationship table
 */

public class PlaceEventRelationship extends Relationship
{
    /**
     * Create a relationship between an Event and a Place, specifically the place of advent of this event..
     * @param rid The relationship id of this relationship.
     * @param eventID  The id of the event.
     * @param placeID The id of this place.
     * @param type A brief description of this relationship.
     */
    public PlaceEventRelationship(long rid, long placeID, long eventID, String type)
    {
        super();
        Map<String,Object> record = getItemMap();
        record.put(KEY_PE_RID,rid);
        record.put(KEY_PE_EVENT_ID,eventID);
        record.put(KEY_PE_PLACE_ID,placeID);
        record.put(KEY_PLACE_EVENT_RELATIONSHIP_TYPE,type);
    }

    /**
     * The preferred way of retrieval and reconstruction of this relationship from the database.
     * @param fvals The fields and associated values of this relationship.
     */
    public PlaceEventRelationship(Map<String,Object> fvals)
    {
        super();
        for(String holder: fvals.keySet())
            if(!PE_KEYS.contains(holder))
                throw new RuntimeException("Invalid Field for a PlaceEventRelationship Object");
        Map<String,Object> record = getItemMap();
        record.putAll(fvals);
    }
}
