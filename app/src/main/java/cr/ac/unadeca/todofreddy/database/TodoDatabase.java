package cr.ac.unadeca.todofreddy.database;


/**
 * Created by Freddy on 2/5/2018.
 */
import com.raizlabs.android.dbflow.annotation.Database;
@Database(name = TodoDatabase.NAME, version = TodoDatabase.VERSION)

public class TodoDatabase {
    public static final String NAME = "todoFreddy";

    public static final int VERSION = 3;

}
