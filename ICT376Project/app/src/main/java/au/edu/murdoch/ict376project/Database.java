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
    public static final String DB_NAME = "database.db";

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
    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PRICE = "price";
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


    public void addProduct(String pName, int pPrice, String pDescription, String pRating, String pPlatform, String pStatus) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //contentValues.put(PRODUCT_ID, pId);
        contentValues.put(PRODUCT_NAME, pName);
        contentValues.put(PRODUCT_PRICE, pPrice);
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
        addProduct("Mario Odyssey", 69, "3D adventures of Mario", "All ages", "Nintendo", "0");
        addProduct("Mario Kart", 69, "Kart racing with Mario", "All ages", "Nintendo", "0");
        addProduct("Zelda", 69, "3D adventures of Link", "8 years+", "Nintendo", "0");
        addProduct("Witcher3", 69, "Third episode of the Witcher", "15 years+", "Nintendo", "0");
        addProduct("FIFA", 69, "Football by FIFA", "All ages", "Nintendo", "0");
        // etc etc etc etc
    }

    public Cursor getAllProducts(String status){

        SQLiteDatabase db = this.getWritableDatabase();
        // getting Cursor for all items -> access via the Cursor object -> query
        Cursor mCursor = db.query(PRODUCT_TABLE, new String[] {PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DESCRIPTION, PRODUCT_STATUS, PRODUCT_RATING, PRODUCT_PLATFORM},
                PRODUCT_STATUS + " like '%" + status + "%'",null, null, null, null, null);
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


    /**
     *         INSERT DATA INTO DATABASE
     *         SQLiteDatabase db = (new Database(this)).getWritableDatabase();
     *         ContentValues values = new ContentValues();
     *         values.put("firstName", "John");
     *         values.put("lastName", "Smith");
     *         values.put("phone", "0428400823");
     *         values.put("email", "john@gmail.com");
     *         values.put("address", "23 George Street, Perth");
     *         db.insert("CUSTOMER", null, values);
     *
     *
     *         SELECT DATA FROM DATABASE
     *         ArrayList<String> array_list = new ArrayList<>();
     *         db = (new Database(this)).getReadableDatabase();
     *         Cursor query = db.rawQuery("select * from CUSTOMER", null);
     *         query.moveToFirst();
     *         while(query.isAfterLast() == false)
     *         {
     *             array_list.add(query.getString(query.getColumnIndex("firstName")));
     *             query.moveToNext();
     *         }
     */
}
