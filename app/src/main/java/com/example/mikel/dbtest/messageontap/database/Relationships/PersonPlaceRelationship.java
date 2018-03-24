package com.example.mikel.dbtest.messageontap.database.Relationships;
import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;

import java.util.Map;

/**
 * A relationship between a person and a place.
 */
public class PersonPlaceRelationship extends Relationship
{
    /**
     * Create a relationship between a Place and a Person, specifically the residence or advent of such person with such a place.
     * @param rid The relationship id of this relationship.
     * @param personID  The id of this person.
     * @param placeID The id of this place. .
     * @param type A brief description of this relationship.
     */
    public PersonPlaceRelationship(long rid, long personID, long placeID, String type)
    {
        super();
        Map<String,Object> record = getItemMap();
        record.put(KEY_PP_RID,rid);
        record.put(KEY_PP_PERSON_ID,personID);
        record.put(KEY_PP_PLACE_ID,placeID);
        record.put(KEY_PERSON_PLACE_RELATIONSHIP_TYPE,type);
    }

    /**
     * The preferred way of retrieval and reconstruction of this relationship from the database.
     * @param fvals The fields and associated values of this relationship.
     */
    public PersonPlaceRelationship(Map<String,Object> fvals)
    {
        super();
        for(String holder: fvals.keySet())
            if(!PP_KEYS.contains(holder))
                throw new RuntimeException("Invalid Field for a PersonPlaceRelationship Object");
        Map<String,Object> record = getItemMap();
        record.putAll(fvals);
    }

}
