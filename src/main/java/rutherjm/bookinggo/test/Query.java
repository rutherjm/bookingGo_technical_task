package rutherjm.bookinggo.test;

public class Query {
    private String supplierID;
    private Coordinate pickup, dropoff;
    public Query(String supplierID, Coordinate pickup, Coordinate dropoff)
    {
        this.supplierID = supplierID;
        this.pickup = pickup;
        this.dropoff = dropoff;
    }
    public String getSupplierID()
    {
        return supplierID;
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
