package com.zt.tz.sunshine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.zt.tz.sunshine.data.WeatherContract;
import com.zt.tz.sunshine.data.WeatherDBHelper;

import static android.content.ContentValues.TAG;

/**
 * Created by zhangtong on 2017-05-11 15:42
 * QQ:xxxxxxxx
 */

public class TestDB extends AndroidTestCase {
    protected int var1 = 1;
    protected int var2 = 1;
    protected String var3 = "my test";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //        mContext.openOrCreateDatabase()
    }

    //前缀为test可以运行此方法
    public void testMyPractice() {
        assertEquals("my test", var3);
        System.out.print("my test success");
        assertTrue("wo de ceshi ", var1 == var2);
    }

    public void testLocationTable() {
        // First step: Get reference to writable database
        WeatherDBHelper dbHelper = new WeatherDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createLocation();

        long rowId;

        rowId = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, testValues);
            assertTrue(""+rowId,rowId != -1L);

        Log.d(TAG, "testLocationTable: "+rowId);
        Cursor cursor = db.query(WeatherContract.LocationEntry.TABLE_NAME, null
                , null, null, null, null, null, null);
        assertTrue("Error: No Records returned from location query", cursor.moveToFirst());
        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed",
                cursor, testValues);

        assertFalse("Error: More than one record returned from location query",
                cursor.moveToNext());

        cursor.close();
        db.close();

        // Finally, close the cursor and database

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.out.print("my test tearDown");
    }
}
