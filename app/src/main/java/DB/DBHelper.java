package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "travel.db";

    private final String DESTINATION = "destination";
    private final String AIRLINE = "there_airline";
    private final String FLIGHT_NO = "there_flightNo";
    private final String PRICE = "there_price";
    private final String DEPARTURE_DATE = "there_Deptdate";
    private final String DEPARTURE_TIME = "there_deptTime";
    private final String ARRIVAL_DATE = "there_arrivalDate";
    private final String ARRIVAL_TIME = "there_arrivalTime";
    private final String HOTEL_NAME = "hotel_name";
    private final String HOTEL_PRICE = "hotel_price";
    private final String HOTEL_ADDRESS = "hotel_address";
    private final String BACK_PRICE = "back_price";
    private final String BACK_DEPARTURE_DATE = "back_deptDate";
    private final String BACK_DEPARTURE_TIME = "back_deptTime";
    private final String BACK_ARRIVAL_DATE = "back_arrivalDate";
    private final String BACK_ARRIVAL_TIME = "back_arrivalTime";
    private static final String BACK_AIRLINE = "back_airline";
    private static final String BACK_FLIGHT_NO = "back_flight_no";
    private final String ORIGIN = "origin";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTable());
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private String createTable() {
        return "CREATE TABLE travel (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DESTINATION + " TEXT, " +
                AIRLINE + " TEXT, " +
                FLIGHT_NO + " TEXT, " +
                PRICE + " REAL, " +
                DEPARTURE_DATE + " TEXT, " +
                DEPARTURE_TIME + " TEXT, " +
                ARRIVAL_DATE + " TEXT, " +
                ARRIVAL_TIME + " TEXT, " +
                HOTEL_NAME + " TEXT, " +
                HOTEL_PRICE + " REAL, " +
                HOTEL_ADDRESS + " TEXT, " +
                BACK_PRICE + " REAL, " +
                BACK_DEPARTURE_DATE + " TEXT, " +
                BACK_DEPARTURE_TIME + " TEXT, " +
                BACK_ARRIVAL_DATE + " TEXT, " +
                BACK_ARRIVAL_TIME + " TEXT, " +
                BACK_AIRLINE + " TEXT, " +
                BACK_FLIGHT_NO + " TEXT, " +
                ORIGIN + " TEXT)";
    }

    private String dropTable() {
        return "DROP TABLE IF EXISTS travel";
    }

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = ?", new String[] { tableName });
        boolean tableExists = (cursor != null) && (cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return tableExists;
    }
    public void insertTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (!isTableExists(db, "travel")) {
            onCreate(db);
        }

        ContentValues values = new ContentValues();
        values.put(DESTINATION, trip.getDestination());
        values.put(AIRLINE, trip.getAirline());
        values.put(FLIGHT_NO, trip.getFlightNo());
        values.put(PRICE, trip.getPrice());
        values.put(DEPARTURE_DATE, trip.getDepartureDate());
        values.put(DEPARTURE_TIME, trip.getDepartureTime());
        values.put(ARRIVAL_DATE, trip.getArrivalDate());
        values.put(ARRIVAL_TIME, trip.getArrivalTime());
        values.put(HOTEL_NAME, trip.getHotelName());
        values.put(HOTEL_PRICE, trip.getHotelPrice());
        values.put(HOTEL_ADDRESS, trip.getHotelAddress());
        values.put(BACK_PRICE, trip.getBackPrice());
        values.put(BACK_DEPARTURE_DATE, trip.getBackDepartureDate());
        values.put(BACK_DEPARTURE_TIME, trip.getBackDepartureTime());
        values.put(BACK_ARRIVAL_DATE, trip.getBackArrivalDate());
        values.put(BACK_ARRIVAL_TIME, trip.getBackArrivalTime());
        values.put(BACK_AIRLINE, trip.getBackAirline());
        values.put(BACK_FLIGHT_NO, trip.getBackArrivalTime());
        values.put(ORIGIN, trip.getOrigin());

        db.insert("travel", null, values);

        db.close();

    }

    public List<String[]> getAllTrips() {
        List<String[]> tripList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                DESTINATION, ORIGIN, PRICE, HOTEL_PRICE, BACK_PRICE
        };

        Cursor cursor = db.query("travel", columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String destination = cursor.getString(cursor.getColumnIndexOrThrow(DESTINATION));
            String origin = cursor.getString(cursor.getColumnIndexOrThrow(ORIGIN));
//            big decimal and rounding mode are used because of inaccuracies with double
            BigDecimal price = BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(PRICE)));
            BigDecimal hotelPrice = BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(HOTEL_PRICE)));
            BigDecimal backPrice = BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(BACK_PRICE)));

            BigDecimal totalPrice = price.add(hotelPrice).add(backPrice);

            // Use setScale() to round the totalPrice to two decimal places
            totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);

            String[] trip = new String[] {destination, origin, String.valueOf(totalPrice)};

            tripList.add(trip);
        }

        cursor.close();
        db.close();

        return tripList;
    }

    public String[] getTripDetails(String origin, String destination) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                DESTINATION, AIRLINE, FLIGHT_NO, PRICE, DEPARTURE_DATE, DEPARTURE_TIME,
                ARRIVAL_DATE, ARRIVAL_TIME, HOTEL_NAME, HOTEL_PRICE, HOTEL_ADDRESS,
                BACK_PRICE, BACK_DEPARTURE_DATE, BACK_DEPARTURE_TIME, BACK_ARRIVAL_DATE,
                BACK_ARRIVAL_TIME,BACK_AIRLINE,BACK_FLIGHT_NO, ORIGIN
        };

        Cursor cursor = db.query("travel", columns, ORIGIN + "=? AND " + DESTINATION + "=?", new String[]{origin, destination}, null, null, null);

        String[] tripDetails = null;

        if (cursor.moveToFirst()) {
            tripDetails = new String[cursor.getColumnCount()];

            for(int i=0; i<cursor.getColumnCount(); i++) {
                tripDetails[i] = cursor.getString(i);
            }
        }

        cursor.close();
        db.close();

        return tripDetails;
    }
    public String[] getTripDetailsDelete(String origin, String destination) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                DESTINATION, AIRLINE, FLIGHT_NO, PRICE, DEPARTURE_DATE, DEPARTURE_TIME,
                ARRIVAL_DATE, ARRIVAL_TIME, HOTEL_NAME, HOTEL_PRICE, HOTEL_ADDRESS,
                BACK_PRICE, BACK_DEPARTURE_DATE, BACK_DEPARTURE_TIME, BACK_ARRIVAL_DATE,
                BACK_ARRIVAL_TIME,BACK_AIRLINE,BACK_FLIGHT_NO, ORIGIN
        };

        Cursor cursor = db.query("travel", columns, ORIGIN + "=? AND " + DESTINATION + "=?", new String[]{origin, destination}, null, null, null);

        String[] tripDetails = null;

        if (cursor.moveToFirst()) {
            tripDetails = new String[cursor.getColumnCount()];

            for(int i=0; i<cursor.getColumnCount(); i++) {
                tripDetails[i] = cursor.getString(i);
            }
        }

        cursor.close();

        return tripDetails;
    }


    public void deleteTripDetails(String origin, String destination) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] tripDetails = getTripDetailsDelete(origin, destination);

        if (tripDetails != null) {
            db.delete("travel", ORIGIN + "=? AND " + DESTINATION + "=?", new String[]{origin, destination});
        }

        db.close();
    }


}

