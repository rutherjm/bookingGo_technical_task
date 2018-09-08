package rutherjm.bookinggo.test;

import java.util.Comparator;
// See https://medium.freecodecamp.org/utilizing-javas-arrays-sort-for-any-list-of-objects-e3e2db61d70b

public class SortByPrice implements Comparator<Option> {
    public int compare(Option a, Option b)
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
