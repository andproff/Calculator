package com.goroshevsky.calculator.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.goroshevsky.calculator.Helpers.DatabaseHelper;
import com.goroshevsky.calculator.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


/**
 * Created by vadim on 12-Jul-16.
 */
public class GraphFragment extends Fragment {

    public static String TAG = "GraphFragment";
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    public PieChart chart;
    private int plusCalled = 0;
    private int minusCalled = 0;
    private int divideCalled = 0;
    private int multiplyCalled = 0;
    private int sinusCalled = 0;
    private int cosinusCalled = 0;
    private int factorialCalled = 0;
    private int powCalled = 0;
    private int tanCalled = 0;
    private int sqrtCalled = 0;
    private int lnCalled = 0;
    private int logCalled = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View graphView = inflater.inflate(R.layout.graph_fragment, parent, false);
        chart = (PieChart) graphView.findViewById(R.id.chart);
        chart.setDescription("");
        chart.setCenterText(getString(R.string.popular_operations));
        chart.setCenterTextSize(15);
        chart.animateX(500);
        loadData asyncTask = new loadData();
        asyncTask.execute();
        return graphView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    class loadData extends AsyncTask<Void, Void, int[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected int[] doInBackground(Void... params) {
            mDatabaseHelper = new DatabaseHelper(getActivity(), "calculator.db", null, 1);
            mSqLiteDatabase = mDatabaseHelper.getReadableDatabase();
            Cursor cursor = mSqLiteDatabase.query("calc_data", new String[]{DatabaseHelper.ENTERED_DATA}, null, null, null, null, null);
            ArrayList<String> formulas = new ArrayList<>();

            try {
                while (cursor.moveToNext()) {
                    formulas.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ENTERED_DATA)));
                }
            } finally {
                cursor.close();
            }

            for (String item : formulas) {
                for (int i = 0; i < item.length(); i++) {
                    switch (item.charAt(i)) {
                        case '+':
                            plusCalled++;
                            break;
                        case '-':
                            minusCalled++;
                            break;
                        case '×':
                            multiplyCalled++;
                            break;
                        case '÷':
                            divideCalled++;
                            break;
                        case '!':
                            factorialCalled++;
                            break;
                        case '^':
                            powCalled++;
                            break;
                        case '√':
                            sqrtCalled++;
                            break;

                        default:
                            break;
                    }

                }

                sinusCalled = sinusCalled + StringUtils.countMatches(item, "sin");
                cosinusCalled = cosinusCalled + StringUtils.countMatches(item, "cos");
                tanCalled = tanCalled + StringUtils.countMatches(item, "tan");
                lnCalled = lnCalled + StringUtils.countMatches(item, "ln");
                logCalled = logCalled + StringUtils.countMatches(item, "log");
            }

            int[] result = new int[]{plusCalled, minusCalled, multiplyCalled, divideCalled, factorialCalled,
                    powCalled, sqrtCalled, sinusCalled, cosinusCalled, tanCalled, lnCalled, logCalled};

            Log.d(TAG, "Number of formulas: " + String.valueOf(formulas.size()));
            return result;
        }

        @Override
        protected void onPostExecute(int[] result) {
            super.onPostExecute(result);
            chart.setData(generatePieData(result));
            chart.invalidate();
            Log.d(TAG, "onPostExecute called");
        }
    }

    protected PieData generatePieData(int[] result) {

        ArrayList<PieEntry> entries = new ArrayList<>();
        if (result[0] != 0) entries.add(new PieEntry((float) result[0], "+"));
        if (result[1] != 0) entries.add(new PieEntry((float) result[1], "-"));
        if (result[2] != 0) entries.add(new PieEntry((float) result[2], "×"));
        if (result[3] != 0) entries.add(new PieEntry((float) result[3], "÷"));
        if (result[4] != 0) entries.add(new PieEntry((float) result[4], "!"));
        if (result[5] != 0) entries.add(new PieEntry((float) result[5], "^"));
        if (result[6] != 0) entries.add(new PieEntry((float) result[6], "√"));
        if (result[7] != 0) entries.add(new PieEntry((float) result[7], "sin"));
        if (result[8] != 0) entries.add(new PieEntry((float) result[8], "cos"));
        if (result[9] != 0) entries.add(new PieEntry((float) result[9], "tan"));
        if (result[10] != 0) entries.add(new PieEntry((float) result[10], "ln"));
        if (result[11] != 0) entries.add(new PieEntry((float) result[11], "log"));

        PieDataSet ds1 = new PieDataSet(entries, getString(R.string.popular_operations));

        ds1.setColors(ColorTemplate.MATERIAL_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        Log.d(TAG, "generateData called");
        return d;
    }
}
