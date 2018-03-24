package com.example.mikel.dbtest.messageontap.database.Entities;
import com.example.mikel.dbtest.messageontap.database.DBHandler;

/**
 * An optional wrapper that wraps up a Place, ex.whether inserts it to the database or not.
 */
public abstract class GeocodeWrapper
{
    /**
     * The database with which this Place object might be concerned.
     */
    protected final DBHandler db;

    /**
     * Create a wrapper with a database designated.
     * @param dbh The database this place may be interested in.
     */
    public GeocodeWrapper(DBHandler dbh)
    {
        db = dbh;
    }
    /**
     * Finalizes the construction of this Place object in the given database.
     * @param loc The Place in consideration.
     */
    public abstract void wrapUp(Place loc);
}
