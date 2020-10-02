package au.edu.murdoch.ict376project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
    public static final String DB_NAME = "database.db";

    public static final String CUSTOMER_TABLE = "CUSTOMER";
    public static final String CUSTOMER_ID = "id";
    public static final String CUSTOMER_FIRSTNAME = "firstName";
    public static final String CUSTOMER_LASTNAME = "lastName";
    public static final String CUSTOMER_PHONE = "phone";
    public static final String CUSTOMER_EMAIL = "email";
    public static final String CUSTOMER_ADDRESS = "address";

    public static final String PRODUCT_TABLE = "PRODUCT";
    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_STOCK = "stock";

    public Database(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + CUSTOMER_TABLE + "(" +
                    CUSTOMER_ID + " integer primary key, " +
                    CUSTOMER_FIRSTNAME + " text, " +
                    CUSTOMER_LASTNAME + " text, " +
                    CUSTOMER_PHONE+ " text, " +
                    CUSTOMER_EMAIL + " text, " +
                    CUSTOMER_ADDRESS + " text)");

        db.execSQL("create table " + PRODUCT_TABLE + "(" +
                PRODUCT_ID + " integer primary key, " +
                PRODUCT_NAME + " text, " +
                PRODUCT_PRICE + " text, " +
                PRODUCT_DESCRIPTION+ " text, " +
                PRODUCT_STOCK + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {

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
