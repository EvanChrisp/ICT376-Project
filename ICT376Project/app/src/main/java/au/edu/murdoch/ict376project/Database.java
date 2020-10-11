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
    public static final String CUSTOMER_ID = "id";
    public static final String CUSTOMER_FIRSTNAME = "firstName";
    public static final String CUSTOMER_LASTNAME = "lastName";
    public static final String CUSTOMER_PHONE = "phone";
    public static final String CUSTOMER_EMAIL = "email";
    public static final String CUSTOMER_ADDRESS = "address";
    public static final String CUSTOMER_PASSWORD = "password";

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
        addProduct(4, "Witcher 3", 69, "witcher3_nintendo", "Third episode of the Witcher", "15 years+", "Nintendo", "0");
        addProduct(5, "FIFA 19", 69, "fifa_nintendo", "Football by FIFA", "All ages", "Nintendo", "0");

        addProduct(6, "Halo", 89, "halo", "Adventures of MasterChief","15 years+", "Xbox", "0");
        addProduct(7, "FIFA 19", 79, "fifa_xbox", "Football by FIFA","All ages", "Xbox", "0");
        addProduct(8, "Final Fantasy XXV", 99, "ffxv_xbox", "JRPG masterpiece","15 years+", "Xbox", "0");
        addProduct(9, "Forza", 119, "forza", "Racing Sim","All ages", "Xbox", "0");
        addProduct(10, "Gears of War", 59, "gears_of_war", "3rd person shooter", "15 years+", "Xbox", "0");

        addProduct(11, "Crash Bandicoot", 49, "crash_bandicoot", "Adventures of Crash", "All ages", "Playstation", "0");
        addProduct(12, "Final Fantasy XV", 99, "ffxv_ps", "JRPG masterpiece", "15 years+", "Playstation", "0");
        addProduct(13, "Ridge Racer", 99, "ridge_racer", "Arcade Racer", "All ages", "Playstation", "0");
        addProduct(14, "Spiderman", 89, "spiderman", "The adventures of Peter Parker", "All ages", "Playstation", "0");
        addProduct(15, "Red Dead Redemption 2", 69, "rdr2", "Open-world wild west", "15 years+", "Playstation", "0");

        addProduct(16, "The Witcher3", 49, "witcher3_pc", "Third episode of the Witcher", "15 years+", "PC", "0");
        addProduct(17, "Call of Duty", 49, "call_of_duty", "First Person Shooter", "15 years+", "PC", "0");
        addProduct(18, "MS Flight Simulator", 99, "ms_flight_sim", "Flight simulator", "All ages", "PC", "0");
        addProduct(19, "Monster Hunter World", 79, "monster_hunter", "The world of monsters", "All ages", "PC", "0");
        addProduct(20, "Half Life 3", 149, "half_life", "Third episode of Half Life", "15 years+", "PC", "0");

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