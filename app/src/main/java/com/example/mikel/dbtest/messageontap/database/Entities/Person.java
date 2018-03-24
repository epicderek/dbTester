package com.example.mikel.dbtest.messageontap.database.Entities;
import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;
import java.util.Map;

import com.example.mikel.dbtest.messageontap.data.Entity;

/**
 * Class for SQL DB compensate the work of Personal Graph
 */


public class Person extends Entity
{

    public Person(long pid, long contactId, String personName, String phoneNumber, String emailAddress, String whatsappUser, String facebookUser)
    {
        super();
        Map<String,Object> record = getItemMap();
        record.put(KEY_PID,pid);
        record.put(KEY_CALENDAR_ID,contactId);
        record.put(KEY_PERSON_NAME,personName);
        record.put(KEY_PHONE_NUMBER,phoneNumber);
        record.put(KEY_EMAIL_ADDRESS,emailAddress);
        record.put(KEY_WHATSAPP_USER,whatsappUser);
        record.put(KEY_FACEBOOK_USER,facebookUser);
    }

    public Person(Map<String,Object> fvals)
    {
        super();
        Map<String,Object> record = getItemMap();
        for(String holder: fvals.keySet())
            if(!PERSON_KEYS.contains(holder))
                throw new RuntimeException("Invalid Fields for a Person Object");
        record.putAll(fvals);
    }

    public String toString()
    {
        return String.format("%s %s %s %s %s %s %s",getValueByField(KEY_PID),getValueByField(KEY_CONTACT_ID),getValueByField(KEY_PERSON_NAME),getValueByField(KEY_PHONE_NUMBER),getValueByField(KEY_EMAIL_ADDRESS),getValueByField(KEY_WHATSAPP_USER),getValueByField(KEY_FACEBOOK_USER));
    }

}
