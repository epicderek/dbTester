package com.example.mikel.dbtest.messageontap.database.Entities;

import static com.example.mikel.dbtest.messageontap.database.DBConstants.*;

import java.util.*;
//import javax.json.*;
import com.example.mikel.dbtest.messageontap.data.Entity;

/**
 *  Relevant information of this Place such as the zip code and the region are derived.
 * @author Derek
 *
 */
public class Place extends Entity
{
    /*
        The portions of the url for the google api.
     */
    private static final String urlFirstPortion;
    private static final String urlSecondPortion;
    /**
     * The api keys for the google api.
     */
    private static String[] keys;
    /**
     * The counter that keeps track of the number of keys exhausted.
     */
    public static int keyCount;

    static
    {
        keys = new String[]{"AIzaSyAe67qjsHOQomCNyIIyi_UKm5uqNvTGcvA", "AIzaSyDzqYwvSvnHhBAPf0ZisEPCuZWZv2ldry4", "AIzaSyDG4Gjp_mW0T25VA17Jmk5pYJRJUFvnbUA", "AIzaSyDnPcdlEZyqR6Cr2ite9aAvoTMDzDhty_E", "AIzaSyDnfk0csfpKC1G12EUh4BzyuXOoa_B0fKE", "AIzaSyCZ-htBGcdRL3edgKX3XIHbvcH52Z-ITIk", "AIzaSyCmhv5_zalf68jlqsdeZAbwJxzsbCy7U4k", "AIzaSyC7_s8YWKxnaKqswFBp7riTccHxNI3EBoA", "AIzaSyC-a4AUdyCipXHFev5hopOUz_Mo0QVg2Tk"};
        keyCount = 0;
        urlFirstPortion = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
        urlSecondPortion = "&key=";
    }

    /**
     * Create a Place of the given latitude and longitude along with an optional name.
     * @param lat  The latitude.
     * @param lon  The longitude.
     * @param name An optional preferred name of this Place.
     */
    public Place(double lat, double lon, String... name)
    {
        super();
        setFieldValue(KEY_LAT,lat);
        setFieldValue(KEY_LNG,lon);
        setFieldValue(KEY_PLACE_NAME,name[0]);
        try
        {
            //interpretLoc(lat,lon);
        }catch(Exception ex)
        {
            if(keyCount<keys.length)
                keyCount++;
            try {
                //interpretLoc(lat,lon);
            }
            catch(Exception ex2)
            {
                throw new RuntimeException(ex2);
            }
        }
    }

    /**
     * A preferred way of reconstruction of a Place object retrieved from the database. The inherited map of fields and values would be cloned from this given map.
     * @param fvals The keys as the fields of this Place, the values as the values.
     */
    public Place(Map<String,Object> fvals)
    {
        super();
        for(String holder: fvals.keySet())
            if(!PLACE_KEYS.contains(holder))
                throw new RuntimeException("Invalid Fields for a Place Object");
        getItemMap().putAll(fvals);
    }

    /**
     * A reserved constructor for reconstruction of objects retrieved from the database to avoid duplicate reverse geo-coding process.
     */
    public Place()
    {

    }

//    /**
//     * A helper method that computes relevant geographic information based on the latitude and longitude given through the google map geocoding api. The results are recorded in the given map whose keys, to be filled by this method along with their proper values, are the enumerated types declared in PhyLocation.
//     * @param lati The latitude of the location.
//     * @param lon The longitude of the location.
//     * @return An object array whose first index is the formatted address, the second the types of this location, third and fourth the adjusted coordinate returned by the google map that comports with the facility located.
//     * @throws IOException
//     */
//    public void interpretLoc(Double lati, Double lon) throws IOException
//    {
//        Map<String,Object> record = getItemMap();
//        URL url = new URL(String.format("%s%s,%s%s%s",uFirst,lati,lon,uSecond,key));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//        StringBuilder json = new StringBuilder();
//        String line;
//        while((line=reader.readLine())!=null)
//        {
//            json.append(line);
//            json.append("\n");
//        }
//        String resp = json.substring(0,json.length()-1);
//        JsonReader read = Json.createReader(new StringReader(resp));
//        JsonObject loc = read.readObject().getJsonArray("results").getJsonObject(0);
//        JsonArray add = loc.getJsonArray("address_components");
//        for(JsonValue holder: add)
//        {
//            JsonObject obj = (JsonObject)holder;
//            if(obj.getJsonArray("types").getString(0).equals("street_number"))
//                record.put(KEY_STREET_NUM,obj.getString("long_name"));
//            if(obj.getJsonArray("types").getString(0).equals("route"))
//                record.put(KEY_ROUTE,obj.getString("short_name"));
//            if(obj.getJsonArray("types").getString(0).equals("neighborhood"))
//                record.put(KEY_NEIGHBORHOOD,obj.getString("short_name"));
//            if(obj.getJsonArray("types").getString(0).equals("locality"))
//                record.put(KEY_LOCALITY,obj.getString("short_name"));
//            if(obj.getJsonArray("types").getString(0).equals("administrative_area_level_2"))
//                record.put(KEY_ADMINISTRATIVE2,obj.getString("short_name"));
//            if(obj.getJsonArray("types").getString(0).equals("administrative_area_level_1"))
//                record.put(KEY_ADMINISTRATIVE1,obj.getString("short_name"));
//            if(obj.getJsonArray("types").getString(0).equals("country"))
//                record.put(KEY_COUNTRY,obj.getString("short_name"));
//            if(obj.getJsonArray("types").getString(0).equals("postal_code"))
//                record.put(KEY_ZIP,obj.getString("short_name"));
//        }
//        record.put(KEY_STREET_ADDRESS,loc.getString("formatted_address"));
//        JsonObject geo = loc.getJsonObject("geometry");
//        record.put(KEY_PLACE_TYPE,(String)loc.getJsonArray("types").toArray()[0]);
//        JsonObject lal = geo.getJsonObject("Place");
//        record.put(KEY_LAT,lal.getJsonNumber("lat").doubleValue());\
//        record.put(KEY_LNG,lal.getJsonNumber("lng").doubleValue());
//    }
}
