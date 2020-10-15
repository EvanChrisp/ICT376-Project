package au.edu.murdoch.ict376project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import 	android.util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper
{
    public static final String DB_NAME = "database2.db";

    // helper
    //private Database mDbHelper;
    //private SQLiteDatabase mDb;

    // tag for debugging ShopDbAdapter
    private static final String TAG = "ShopDbAdapter";

    // customer table
    public static final String CUSTOMER_TABLE = "customers";
    public static final String CUSTOMER_ID = "_id";
    public static final String CUSTOMER_FIRSTNAME = "firstName";
    public static final String CUSTOMER_LASTNAME = "lastName";
    public static final String CUSTOMER_PHONE = "phone";
    public static final String CUSTOMER_EMAIL = "email";
    public static final String CUSTOMER_ADDRESS = "address";
    public static final String CUSTOMER_PASSWORD = "password";
    // public static final String CUSTOMER_IS_LOGGED_IN = "loggedin";

    // product table
    public static final String PRODUCT_TABLE = "products";
    public static final String PRODUCT_ID = "_id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_FILE = "file";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_STATUS = "status";
    public static final String PRODUCT_RATING = "rating";
    public static final String PRODUCT_PLATFORM = "platform";

    // version
    static int ver =1;

    // context variable
    //private final Context mContext;


    public Database(Context context)
    {
        // SQLiteOpenHelper(Context context, String name, SQLiteDatabase.curosrFactory factory, int version)
        super(context, DB_NAME, null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // table creation -> customer table
        db.execSQL("create table " + CUSTOMER_TABLE + "(" +
                CUSTOMER_ID + " integer primary key, " +
                CUSTOMER_FIRSTNAME + " text, " +
                CUSTOMER_LASTNAME + " text, " +
                CUSTOMER_PHONE+ " text, " +
                CUSTOMER_EMAIL + " text, " +
                CUSTOMER_PASSWORD + " text," +
                CUSTOMER_ADDRESS + " text)");
        // need to add in either
        // CUSTOMER_LOGGED_IN + " text, " +
        // OR
        // CUSTOMER_LOGGED_IN + " integer, " +

        // table creation -> product table
        db.execSQL("create table " + PRODUCT_TABLE + "(" +
                PRODUCT_ID + " integer primary key, " +
                PRODUCT_NAME + " text, " +
                PRODUCT_PRICE + " text, " +
                PRODUCT_FILE + " text, " +
                PRODUCT_DESCRIPTION + " text, " +
                PRODUCT_RATING + " text, " +
                PRODUCT_PLATFORM + " text, " +
                PRODUCT_STATUS + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
        onCreate(db);
    }

    public void addUserDetails(int pId, String pName, int pPrice, String pFile, String pDescription, String pRating, String pPlatform, String pStatus) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, pId);
        contentValues.put(PRODUCT_NAME, pName);
        contentValues.put(PRODUCT_PRICE, pPrice);
        contentValues.put(PRODUCT_FILE, pFile);
        contentValues.put(PRODUCT_DESCRIPTION, pDescription);
        contentValues.put(PRODUCT_STATUS, pStatus);
        contentValues.put(PRODUCT_RATING, pRating);
        contentValues.put(PRODUCT_PLATFORM, pPlatform);

        db.insert(PRODUCT_TABLE, null, contentValues);
        //return true;
    }


    public void addProduct(int pId, String pName, int pPrice, String pFile, String pDescription, String pRating, String pPlatform, String pStatus) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, pId);
        contentValues.put(PRODUCT_NAME, pName);
        contentValues.put(PRODUCT_PRICE, pPrice);
        contentValues.put(PRODUCT_FILE, pFile);
        contentValues.put(PRODUCT_DESCRIPTION, pDescription);
        contentValues.put(PRODUCT_STATUS, pStatus);
        contentValues.put(PRODUCT_RATING, pRating);
        contentValues.put(PRODUCT_PLATFORM, pPlatform);

        db.insert(PRODUCT_TABLE, null, contentValues);
        //return true;
    }

    // change the value of status to "0" or "1" - Zero is not added to cart, One is added to cart
    public boolean addToCart (Integer id, String val){
        // get database
        SQLiteDatabase db = this.getWritableDatabase();
        // create new contentValues object
        ContentValues contentValues = new ContentValues();
        // put status value into contentValues
        contentValues.put(PRODUCT_STATUS, val);
        // adding means updating database table ->
        db.update(PRODUCT_TABLE, contentValues, "_id= ? ", new String[]{Integer.toString(id)});
        // after entry remember to close the database -> memory leaks
        db.close();
        // if successful -> return is true
        return true;
    }

    // change the value of status to "0" or "1" - Zero is not added to cart, One is added to cart
    public boolean removeFromCart (Integer id, String val){
        // get database
        SQLiteDatabase db = this.getWritableDatabase();
        // create new contentValues object
        ContentValues contentValues = new ContentValues();
        // put status value into contentValues
        contentValues.put(PRODUCT_STATUS, val);
        // adding means updating database table ->
        db.update(PRODUCT_TABLE, contentValues, "_id= ? ", new String[]{Integer.toString(id)});
        // after entry remember to close the database -> memory leaks
        db.close();
        // if successful -> return is true
        return true;
    }

    public void deleteAllItems() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(PRODUCT_TABLE, null , null);
        //Log.w(TAG, Integer.toString(doneDelete));

    }

    public void insertMyShopItems() {
        // items for database
        addProduct(1, "Mario Odyssey", 69, "mario_odyssey", "3D adventures of Mario", "All ages", "Nintendo", "0");
        addProduct(2, "Mario Kart", 69, "mario_kart", "Kart racing with Mario", "All ages", "Nintendo", "0");
        addProduct(3, "Zelda", 69, "zelda", "3D adventures of Link", "8 years+", "Nintendo", "0");
        addProduct(4, "The Witcher 3", 69, "witcher3_nintendo", "Third episode of the Witcher", "15 years+", "Nintendo", "0");
        addProduct(5, "FIFA 19", 69, "fifa_nintendo", "Football by FIFA", "All ages", "Nintendo", "0");
        addProduct(6, "Pokemon Shield", 69, "pokemon_shield", "Pokemon Shield Edition", "All ages", "Nintendo", "0");
        addProduct(7, "Pokemon Sword", 69, "pokemon_sword", "Pokemon Sword Edition", "All ages", "Nintendo", "0");
        addProduct(8, "Pokemon Mystery Dungeon", 69, "pokemon_mystery_dungeon", "Pokemon Mystery Dungeon Remake", "All ages", "Nintendo", "0");
        addProduct(9, "Mario Party", 69, "mario_party", "Latest Mario Party","All ages", "Nintendo", "0");
        addProduct(10, "Overcooked 2", 69, "overcooked_2", "Cook with friends", "All ages", "Nintendo", "0");

        addProduct(11, "Halo", 89, "halo", "Adventures of MasterChief","15 years+", "Xbox", "0");
        addProduct(12, "FIFA 19", 79, "fifa_xbox", "Football by FIFA","All ages", "Xbox", "0");
        addProduct(13, "Final Fantasy XXV", 99, "ffxv_xbox", "JRPG masterpiece","15 years+", "Xbox", "0");
        addProduct(14, "Forza", 119, "forza", "Racing Sim","All ages", "Xbox", "0");
        addProduct(15, "Gears of War", 59, "gears_of_war", "3rd person shooter", "15 years+", "Xbox", "0");
        addProduct(16, "Dark Souls 3", 44, "dark_souls_3_xbox", "Final Game of Dark Souls Trilogy", "15 years+", "Xbox", "0");
        addProduct(17, "Modern Warfare", 99, "modern_warfare_xbox", "Latest Call of Duty Game", "15 years+", "Xbox", "0");
        addProduct(18, "Metro Exodus", 30, "metro_exodus_xbox", "Explore a Frozen Apocalyptic Russia", "15 years+", "Xbox", "0");
        addProduct(19, "Sunset Overdrive", 10, "sunset_overdrive", "Xbox Launch Title", "15 years+", "Xbox", "0");
        addProduct(20, "Jedi Fallen Order", 48, "jedi_fallen_order_xbox", "Play as a Jedi and fight the Empire", "15 years+", "Xbox", "0");

        addProduct(21, "Crash Bandicoot", 49, "crash_bandicoot", "Adventures of Crash", "All ages", "Playstation", "0");
        addProduct(22, "Final Fantasy XV", 99, "ffxv_ps", "JRPG masterpiece", "15 years+", "Playstation", "0");
        addProduct(23, "Ridge Racer", 99, "ridge_racer", "Arcade Racer", "All ages", "Playstation", "0");
        addProduct(24, "Spiderman", 89, "spiderman", "The adventures of Peter Parker", "All ages", "Playstation", "0");
        addProduct(25, "Red Dead Redemption 2", 69, "rdr2", "Open-world wild west", "15 years+", "Playstation", "0");
        addProduct(26, "Dark Souls 3", 44, "dark_souls_3_ps4", "Final Game of Dark Souls Trilogy", "15 years+", "Playstation", "0");
        addProduct(27, "Modern Warfare", 99, "modern_warfare_ps4", "Latest Call of Duty Game", "15 years+", "Playstation", "0");
        addProduct(28, "Spyro Reignited Trilogy", 70, "spyro_ps4", "Remake of all three original spyro games", "All ages", "Playstation", "0");
        addProduct(29, "God of War", 25, "god_of_war_ps4", "Long awaited sequel to the God of War Franchise", "15 years+", "Playstation", "0");
        addProduct(30, "Bloodborne", 20, "bloodborne", "Spin off of Dark Souls Franchise", "15 years+", "Playstation", "0");

        addProduct(31, "Team Fortress 2", 25, "tf2", "Classic FPS", "15 years+", "PC", "0");
        addProduct(32, "Command and Conquer Red Alert 3", 30, "c_and_c_red_alert_3", "Wacky Strategy Game", "15 years+", "PC", "0");
        addProduct(33, "Among Us", 5, "among_us", "Social Deception game with friends", "15 years+", "PC", "0");
        addProduct(34, "Dark Souls 3", 44, "dark_souls_3_pc", "Final Game of Dark Souls Trilogy", "15 years+", "PC", "0");
        addProduct(35, "Rainbow Six Siege", 30, "rainbow_six_siege_PC", "Team Based Shooter", "15 years+", "PC", "0");
        addProduct(36, "The Witcher 3", 49, "witcher3_pc", "Third episode of the Witcher", "15 years+", "PC", "0");
        addProduct(37, "Call of Duty", 49, "call_of_duty", "First Person Shooter", "15 years+", "PC", "0");
        addProduct(38, "MS Flight Simulator", 99, "ms_flight_sim", "Flight simulator", "All ages", "PC", "0");
        addProduct(39, "Monster Hunter World", 79, "monster_hunter", "The world of monsters", "All ages", "PC", "0");
        addProduct(40, "Half-Life 3", 149, "half_life", "Third episode of Half Life", "15 years+", "PC", "0");

    }
    public int getTotalItemsCount() {
        // get the database
        SQLiteDatabase db = this.getWritableDatabase();
        // for total items count -> save the query string
        String countQuery = "SELECT  * FROM " + PRODUCT_TABLE;
        // cursor object -> rawQuery -> countQuery String with no selection criteria
        Cursor cursor = db.rawQuery(countQuery, null);
        // the count equals the cursor object.getCount();
        int cnt = cursor.getCount();
        // close the cursor object -> memory leaks
        cursor.close();
        // return the count
        return cnt;
    }

    public Cursor getProductById(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        // id changed to _id in where clause -> causes errors with cursorAdapters
        Cursor res = db.rawQuery("select * from products where _id = " + id, null);

        if (res.getCount() > 0 && res != null)
        {
            res.moveToFirst();
        }
        return res;
    }


    public Cursor getShoppingCart(String cartStatus)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        // id changed to _id in where clause -> causes errors with cursorAdapters
        Cursor res = db.rawQuery("select * from products where status = " + cartStatus, null);

        if (res.getCount() > 0 && res != null)
        {
            res.moveToFirst();
        }
        return res;
    }

    public Cursor getCursorProducts(String platform){

        SQLiteDatabase db = this.getWritableDatabase();
        // getting Cursor for all items -> access via the Cursor object -> query
        Cursor mCursor = db.query(PRODUCT_TABLE,
                new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DESCRIPTION, PRODUCT_STATUS, PRODUCT_FILE, PRODUCT_RATING, PRODUCT_PLATFORM},
                PRODUCT_PLATFORM + " like '%" + platform + "%'",
                null,
                null,
                null,
                null,
                null);
        if (mCursor != null) {
            // iterate through the cursor rows
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getCursorSearchProducts(String searchTerms){

        SQLiteDatabase db = this.getWritableDatabase();
        // getting Cursor for all items -> access via the Cursor object -> query
        Cursor mCursor = db.query(PRODUCT_TABLE,
                new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DESCRIPTION, PRODUCT_STATUS, PRODUCT_FILE, PRODUCT_RATING, PRODUCT_PLATFORM},
                PRODUCT_NAME + " like '%" + searchTerms + "%'",
                null,
                null,
                null,
                null,
                null);
        if (mCursor != null) {
            // iterate through the cursor rows
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public ArrayList <Pair<Integer, String>> getProductList(){
        ArrayList<Pair<Integer, String>> array_list = new ArrayList<Pair<Integer, String>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from products", null);
        if (res.getCount() >0){
            res.moveToFirst();

            while(res.isAfterLast()== false){
                array_list.add(new Pair (res.getInt(res.getColumnIndex(PRODUCT_ID)),res.getString(res.getColumnIndex(PRODUCT_NAME))));
                res.moveToNext();
            }
        }
        return array_list;
    }

    public ArrayList <Pair<Integer, String>> getNintendoProductList(){
        ArrayList<Pair<Integer, String>> array_list = new ArrayList<Pair<Integer, String>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from products where platform =?",new String[]{"Nintendo"});
        //Cursor res = db.rawQuery("select * from products where column = ?",new String[]{"data"});
        if (res.getCount() >0){
            res.moveToFirst();

            while(res.isAfterLast()== false){
                array_list.add(new Pair (res.getInt(res.getColumnIndex(PRODUCT_ID)),res.getString(res.getColumnIndex(PRODUCT_NAME))));
                res.moveToNext();
            }
        }
        return array_list;
    }

    public ArrayList <Pair<Integer, String>> getPlaystationProductList(){
        ArrayList<Pair<Integer, String>> array_list = new ArrayList<Pair<Integer, String>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from products where platform =?",new String[]{"Playstation"});
        //Cursor res = db.rawQuery("select * from products where column = ?",new String[]{"data"});
        if (res.getCount() >0){
            res.moveToFirst();

            while(res.isAfterLast()== false){
                array_list.add(new Pair (res.getInt(res.getColumnIndex(PRODUCT_ID)),res.getString(res.getColumnIndex(PRODUCT_NAME))));
                res.moveToNext();
            }
        }
        return array_list;
    }

    public ArrayList <Pair<Integer, String>> getXboxProductList(){
        ArrayList<Pair<Integer, String>> array_list = new ArrayList<Pair<Integer, String>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from products where platform =?",new String[]{"Xbox"});
        //Cursor res = db.rawQuery("select * from products where column = ?",new String[]{"data"});
        if (res.getCount() >0){
            res.moveToFirst();

            while(res.isAfterLast()== false){
                array_list.add(new Pair (res.getInt(res.getColumnIndex(PRODUCT_ID)),res.getString(res.getColumnIndex(PRODUCT_NAME))));
                res.moveToNext();
            }
        }
        return array_list;
    }

    public ArrayList <Pair<Integer, String>> getPCProductList(){
        ArrayList<Pair<Integer, String>> array_list = new ArrayList<Pair<Integer, String>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from products where platform =?",new String[]{"PC"});
        //Cursor res = db.rawQuery("select * from products where column = ?",new String[]{"data"});
        if (res.getCount() >0){
            res.moveToFirst();

            while(res.isAfterLast()== false){
                array_list.add(new Pair (res.getInt(res.getColumnIndex(PRODUCT_ID)),res.getString(res.getColumnIndex(PRODUCT_NAME))));
                res.moveToNext();
            }
        }
        return array_list;
    }

}
