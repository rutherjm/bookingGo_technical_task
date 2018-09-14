package rutherjm.bookinggo.test;

import rutherjm.bookinggo.test.JSONEntities.Coordinate;

public class Query {
    private Coordinate pickup, dropoff;
    public Query(Coordinate pickup, Coordinate dropoff)
    {
        this.pickup = pickup;
        this.dropoff = dropoff;
    }

    public Coordinate getPickUp()
    {
        return pickup;
    }
    public Coordinate getDropOff()
    {
        return dropoff;
    }
}
