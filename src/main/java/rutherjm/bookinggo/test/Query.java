package rutherjm.bookinggo.test;

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
