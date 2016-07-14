package com.goroshevsky.calculator.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.goroshevsky.calculator.Helpers.DatabaseHelper;
import com.goroshevsky.calculator.R;

import java.util.concurrent.TimeUnit;


/**
 * Created by vadim on 11-Jul-16.
 */
public class SQLDataFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    ListView listViewData;
    SimpleCursorAdapter scAdapter;
    private static final int CM_DELETE_ID = 1;
    public static String TAG = "SQLDataFragment";
    private View loadingPanel;
    private static boolean SQLDataWasLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View sqlDataView = inflater.inflate(R.layout.fragment_sql_data, parent, false);

        mDatabaseHelper = new DatabaseHelper(getActivity(), "calculator.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getReadableDatabase();

        String[] from = new String[]{DatabaseHelper._ID, DatabaseHelper.USERNAME_COLUMN, DatabaseHelper.NR_BUTTONS_TAPPED_COLUMN, DatabaseHelper.ENTERED_DATA, DatabaseHelper.RESULT};
        int[] to = new int[]{R.id.listId, R.id.usernameLV, R.id.nrbuttonsLV, R.id.enteredDataLV, R.id.resultLV};
        scAdapter = new SimpleCursorAdapter(getActivity(), R.layout.item, null, from, to, 0);
        listViewData = (ListView) sqlDataView.findViewById(R.id.listView);
        listViewData.setAdapter(scAdapter);
        registerForContextMenu(listViewData);
        loadingPanel = sqlDataView.findViewById(R.id.loadingPanel);
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        SQLDataWasLoaded = true;
        return sqlDataView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SQLDataWasLoaded) {
            Log.d(TAG, "onResume called");
            getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
            Log.d(TAG, "forceLoad called");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            mSqLiteDatabase.delete(DatabaseHelper.DATABASE_TABLE, DatabaseHelper._ID + " = " + acmi.id, null);
            getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
            Log.d(TAG, "Record deleted");
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(getActivity(), mDatabaseHelper);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        loadingPanel.setVisibility(View.GONE);
        scAdapter.swapCursor(data);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mDatabaseHelper != null) mDatabaseHelper.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static class MyCursorLoader extends CursorLoader {

        DatabaseHelper db;

        public MyCursorLoader(Context context, DatabaseHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {

            Cursor cursor = db.getAllData();
            Log.d(TAG, "getAllDataCalled");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }

    }
}