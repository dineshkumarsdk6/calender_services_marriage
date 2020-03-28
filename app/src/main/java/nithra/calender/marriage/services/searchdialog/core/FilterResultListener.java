package nithra.calender.marriage.services.searchdialog.core;

import java.util.ArrayList;

/**
 * Created by MADNESS on 5/14/2017.
 */

public interface FilterResultListener<T> {
    void onFilter(ArrayList<T> items);
}
