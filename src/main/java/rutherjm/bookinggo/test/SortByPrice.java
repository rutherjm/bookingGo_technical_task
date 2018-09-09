package rutherjm.bookinggo.test;

import rutherjm.bookinggo.test.JSONEntities.JsonOption;

import java.util.Comparator;

public class SortByPrice implements Comparator<JsonOption> {
    public int compare(JsonOption a, JsonOption b)
    {
        if (a.price < b.price)
        {
            return -1;
        }
        else if (a.price == b.price){
            return 0;
        }
        else{
            return 1;
        }
    }
}
