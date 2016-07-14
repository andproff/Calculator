package com.goroshevsky.calculator.Fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.goroshevsky.calculator.Helpers.DatabaseHelper;
import com.goroshevsky.calculator.Helpers.ShakeDetector;
import com.goroshevsky.calculator.MainActivity;
import com.goroshevsky.calculator.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mathexpressioncalculator.Calculator;

public class MainFragment extends Fragment implements View.OnLongClickListener, View.OnClickListener {
    private EditText textFormula;
    private EditText textResult;
    private String TAG = "Main Fragment";
    public static ViewPager numberPadPager;
    private int nrOfButtonsTapped;
    private LinearLayout layout;
    private final int REQUEST_CAMERA_AND_STORAGE = 0;
    private boolean MAIN_FRAGMENT_IS_IN_FRONT = false;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.main_fragment, parent, false);

        textFormula = (EditText) mainView.findViewById(R.id.formula);
        textResult = (EditText) mainView.findViewById(R.id.result);
        View delButton = mainView.findViewById(R.id.buttonDel);
        delButton.setOnClickListener(this);
        delButton.setOnLongClickListener(this);
        numberPadPager = (ViewPager) mainView.findViewById(R.id.pager);

        nrOfButtonsTapped = 0;
        layout = (LinearLayout) mainView.findViewById(R.id.snackbarLayout);

        Button button1 = (Button) mainView.findViewById(R.id.button1);
        button1.setOnClickListener(this);
        Button button2 = (Button) mainView.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = (Button) mainView.findViewById(R.id.button3);
        button3.setOnClickListener(this);
        Button button4 = (Button) mainView.findViewById(R.id.button4);
        button4.setOnClickListener(this);
        Button button5 = (Button) mainView.findViewById(R.id.button5);
        button5.setOnClickListener(this);
        Button button6 = (Button) mainView.findViewById(R.id.button6);
        button6.setOnClickListener(this);
        Button button7 = (Button) mainView.findViewById(R.id.button7);
        button7.setOnClickListener(this);
        Button button8 = (Button) mainView.findViewById(R.id.button8);
        button8.setOnClickListener(this);
        Button button9 = (Button) mainView.findViewById(R.id.button9);
        button9.setOnClickListener(this);
        Button button0 = (Button) mainView.findViewById(R.id.button0);
        button0.setOnClickListener(this);
        Button buttonDot = (Button) mainView.findViewById(R.id.buttonDot);
        buttonDot.setOnClickListener(this);
        Button buttonEqual = (Button) mainView.findViewById(R.id.buttonEqual);
        buttonEqual.setOnClickListener(this);
        Button buttonDiv = (Button) mainView.findViewById(R.id.buttonDiv);
        buttonDiv.setOnClickListener(this);
        Button buttonMul = (Button) mainView.findViewById(R.id.buttonMul);
        buttonMul.setOnClickListener(this);
        Button buttonMin = (Button) mainView.findViewById(R.id.buttonMin);
        buttonMin.setOnClickListener(this);
        Button buttonPlus = (Button) mainView.findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(this);
        Button buttonSin = (Button) mainView.findViewById(R.id.buttonSin);
        buttonSin.setOnClickListener(this);
        Button buttonCos = (Button) mainView.findViewById(R.id.buttonCos);
        buttonCos.setOnClickListener(this);
        Button buttonTan = (Button) mainView.findViewById(R.id.buttonTan);
        buttonTan.setOnClickListener(this);
        Button buttonLn = (Button) mainView.findViewById(R.id.buttonLn);
        buttonLn.setOnClickListener(this);
        Button buttonLog = (Button) mainView.findViewById(R.id.buttonLog);
        buttonLog.setOnClickListener(this);
        Button buttonFact = (Button) mainView.findViewById(R.id.buttonFact);
        buttonFact.setOnClickListener(this);
        Button buttonPi = (Button) mainView.findViewById(R.id.buttonPi);
        buttonPi.setOnClickListener(this);
        Button buttonExp = (Button) mainView.findViewById(R.id.buttonExp);
        buttonExp.setOnClickListener(this);
        Button buttonPow = (Button) mainView.findViewById(R.id.buttonPow);
        buttonPow.setOnClickListener(this);
        Button buttonOpen = (Button) mainView.findViewById(R.id.buttonOpen);
        buttonOpen.setOnClickListener(this);
        Button buttonClose = (Button) mainView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(this);
        Button buttonSqrt = (Button) mainView.findViewById(R.id.buttonSqrt);
        buttonSqrt.setOnClickListener(this);


        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                Log.d(TAG, "Shake detected. Count: " + String.valueOf(count));
                if (count > 1) {
                    clear();
                    Toast.makeText(getActivity().getApplicationContext(), R.string.clear_input, Toast.LENGTH_LONG).show();
                }
            }
        });

        if (!MainActivity.VIBRATE_WAS_CALLED)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (MAIN_FRAGMENT_IS_IN_FRONT && (TextUtils.isEmpty(textFormula.getText())))
                        vibrate();
                }
            }, 3000);
        Log.d(TAG, "View is created");
        return mainView;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        MAIN_FRAGMENT_IS_IN_FRONT = true;
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        Log.d(TAG, "onResume called");
    }

    @Override
    public void onPause() {
        super.onPause();
        MAIN_FRAGMENT_IS_IN_FRONT = false;
        mSensorManager.unregisterListener(mShakeDetector);
        Log.d(TAG, "onPause called");
    }

    public boolean onLongClick(View view) {
        if (view.getId() == R.id.buttonDel) {
            clear();
            return true;
        }
        return false;
    }

    private void clear() {
        if (TextUtils.isEmpty(textFormula.getText())) {
            return;
        }
        final Editable formulaText = textFormula.getEditableText();
        formulaText.clear();
        final Editable resultText = textResult.getEditableText();
        resultText.clear();
        Log.d(TAG, "clear");
    }

    @Override
    public void onClick(View view) {
        nrOfButtonsTapped++;
        Log.d(TAG, "Test called.");
        switch (view.getId()) {
            case R.id.buttonEqual:
                handleExceptionOnEqual();
                Log.d(TAG, "OnEqual called.");
                break;
            case R.id.buttonDel:
                delete();
                handleExceptionOnRealTime();
                break;
            case R.id.buttonCos:
            case R.id.buttonLog:
            case R.id.buttonLn:
            case R.id.buttonSin:
            case R.id.buttonTan:
                textFormula.append(((Button) view).getText() + "(");
                break;
            case R.id.buttonOpen:
                textFormula.append(((Button) view).getText());
                break;
            default:
                textFormula.append(((Button) view).getText());
                handleExceptionOnRealTime();
                break;
        }

    }

    private void delete() {
        final Editable formulaText = textFormula.getEditableText();
        final int formulaLength = formulaText.length();
        if (formulaLength > 0) {
            formulaText.delete(formulaLength - 1, formulaLength);
        }
    }

    public String preEqual() {
        Calculator calculator = new Calculator();
        String expression = textFormula.getText().toString();
        expression = forFact(expression);
        expression = forSqrt(expression);
        expression = expression.replace("÷", "/");
        expression = expression.replace("×", "*");
        expression = expression.replace("π", "PI");
        expression = expression.replace("log", "log10");
        expression = expression.replace("e", "E");
        expression = expression.replace("!", "fact");
        expression = expression.replace("√", "sqrt");
        calculator.setExpression(expression);
        double result = calculator.getResult();
        NumberFormat nf = new DecimalFormat("##.####");
        String roundedResult = String.valueOf(nf.format(result));
        if (Math.abs(result - Math.round(result)) < 0.001d) {
            //If value after the decimal point is 0 change the formatting
            nf = new DecimalFormat("#");
            roundedResult = String.valueOf(nf.format(result));
            return roundedResult;
        }
        Log.d(TAG, "Result is " + String.valueOf(result));
        return roundedResult;
    }

    public void onEqual() {
        if (TextUtils.isEmpty(textFormula.getText())) {
            textResult.setText("");
            return;
        }
        writeDataToSQLite();
        Editable resultText = textResult.getEditableText();
        resultText.clear();
        String result = preEqual();
        textFormula.setText(result);
        if (result.contentEquals("32")) takeFrontCamPhoto();
    }

    public void handleExceptionOnEqual() {
        try {
            onEqual();
        } catch (IllegalArgumentException | IndexOutOfBoundsException exception) {
            Snackbar snackbar = Snackbar
                    .make(layout, R.string.bad_expression, Snackbar.LENGTH_LONG)
                    .setAction(R.string.clear, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clear();
                        }
                    });
            snackbar.show();
            Log.e(TAG, "Bad formula!");
        }

    }

    public String forFact(String exp) {
        Log.d(TAG, "forFact " + exp);
        Pattern pattern = Pattern.compile("\\d+\\!");
        Matcher matcher = pattern.matcher(exp);
        if (matcher.find()) {
            String data = matcher.group();
            exp = exp.replaceAll("\\d+\\!", "fact(" + (data) + ")");
            exp = exp.replace("!", "");
            Log.d(TAG, "Fact matcher found!");
            Log.d(TAG, exp);
            return exp;
        }
        return exp;
    }

    public String forSqrt(String exp) {
        Log.d(TAG, "forSqrt " + exp);
        Pattern pattern = Pattern.compile("\\√\\d+");
        Matcher matcher = pattern.matcher(exp);
        if (matcher.find()) {
            String data = matcher.group();
            exp = exp.replaceAll("\\√\\d+", "sqrt(" + (data) + ")");
            exp = exp.replace("√", "");
            Log.d(TAG, "Sqrt matcher found!");
            Log.d(TAG, exp);
            return exp;
        }
        return exp;
    }

    public void handleExceptionOnRealTime() {
        try {
            if (TextUtils.isEmpty(textFormula.getText())) {
                textResult.setText("0");
                return;
            }
            textResult.setText(preEqual());

        } catch (IllegalArgumentException | IndexOutOfBoundsException exception) {
            Log.w(TAG, "Bad formula!");
        }
    }

    public void writeDataToSQLite() {
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(getActivity(), "calculator.db", null, 1);
        SQLiteDatabase mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USERNAME_COLUMN, MainActivity.USER_NAME);
        values.put(DatabaseHelper.NR_BUTTONS_TAPPED_COLUMN, nrOfButtonsTapped);
        values.put(DatabaseHelper.ENTERED_DATA, textFormula.getText().toString());
        values.put(DatabaseHelper.RESULT, textResult.getText().toString());
        mSqLiteDatabase.insert("calc_data", null, values);
        Log.d(TAG, "writeDataToSQLite called");
        nrOfButtonsTapped = 0;
    }

    @SuppressWarnings("deprecation")
    private void takeFrontCamPhoto() {
        Toast.makeText(getActivity().getApplicationContext(), R.string.camera_start, Toast.LENGTH_SHORT).show();
        SurfaceView surface = new SurfaceView(getActivity().getApplicationContext());

        if (Build.VERSION.SDK_INT < 23) {

            int numberOfCameras = Camera.getNumberOfCameras();
            Log.d(TAG, "Number of cameras: " + String.valueOf(numberOfCameras));
            Camera.CameraInfo ci = new Camera.CameraInfo();

            int cameraId = -1;

            for (int i = 0; i < numberOfCameras; i++) {
                Camera.getCameraInfo(i, ci);
                if (ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    cameraId = i;
                    Log.d(TAG, "Front camera ID: " + String.valueOf(cameraId));
                }
            }
            final Camera camera = Camera.open(cameraId);
            try {
                camera.setPreviewDisplay(surface.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
            SurfaceTexture st = new SurfaceTexture(getActivity().getApplicationContext().MODE_PRIVATE);
            try {
                camera.setPreviewTexture(st);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
            Camera.Parameters params = camera.getParameters();
            Log.d(TAG, String.valueOf(params.getJpegQuality()));
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
            params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
            params.setExposureCompensation(0);
            params.setJpegQuality(100);
            params.setRotation(270);
            Camera.Size bestSize;
            List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
            bestSize = sizeList.get(0);
            for (int i = 1; i < sizeList.size(); i++) {
                if ((sizeList.get(i).width * sizeList.get(i).height) > (bestSize.width * bestSize.height)) {
                    bestSize = sizeList.get(i);
                }
            }
            params.setPictureSize(bestSize.width, bestSize.height);
            camera.setParameters(params);

            //without delay photos are dark
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    camera.takePicture(null, null, jpegCallback);
                }
            }, 1000);


        } else {

            if ((getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) ||
                    (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_AND_STORAGE);

            } else {

                int numberOfCameras = Camera.getNumberOfCameras();
                Log.d(TAG, "Number of cameras: " + String.valueOf(numberOfCameras));
                Camera.CameraInfo ci = new Camera.CameraInfo();

                int cameraId = -1;

                for (int i = 0; i < numberOfCameras; i++) {
                    Camera.getCameraInfo(i, ci);
                    if (ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        cameraId = i;
                        Log.d(TAG, "Front camera ID: " + String.valueOf(cameraId));
                    }
                }
                final Camera camera = Camera.open(cameraId);
                try {
                    camera.setPreviewDisplay(surface.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SurfaceTexture st = new SurfaceTexture(getActivity().getApplicationContext().MODE_PRIVATE);
                try {
                    camera.setPreviewTexture(st);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                camera.startPreview();
                Camera.Parameters params = camera.getParameters();
                Log.d(TAG, String.valueOf(params.getJpegQuality()));
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
                params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
                params.setExposureCompensation(0);
                params.setJpegQuality(100);
                params.setRotation(270);
                Camera.Size bestSize;
                List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
                bestSize = sizeList.get(0);
                for (int i = 1; i < sizeList.size(); i++) {
                    if ((sizeList.get(i).width * sizeList.get(i).height) > (bestSize.width * bestSize.height)) {
                        bestSize = sizeList.get(i);
                    }
                }
                params.setPictureSize(bestSize.width, bestSize.height);
                camera.setParameters(params);

                //without delay photos are dark
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        camera.takePicture(null, null, jpegCallback);
                    }
                }, 1000);

            }
        }

    }

    @SuppressWarnings("deprecation")
    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File myDir = new File(root + "/CalcPictures");
                File file = new File(myDir, "IMG_" + timeStamp + ".jpg");

                if (!myDir.exists()) {
                    myDir.mkdir();
                }
                FileOutputStream out = new FileOutputStream(file);
                out.write(data);
                out.close();
                Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                camera.stopPreview();
                camera.release();
                camera = null;
                Toast.makeText(getActivity().getApplicationContext(), R.string.photo_saved, Toast.LENGTH_LONG).show();
            }
            Log.d(TAG, "onPictureTaken - jpeg");
        }
    };

    private void vibrate() {
        Toast.makeText(getActivity().getApplicationContext(), R.string.vibrate, Toast.LENGTH_LONG).show();
        Vibrator v = (Vibrator) getActivity().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
        MainActivity.VIBRATE_WAS_CALLED = true;
        Log.d(TAG, "vibrate called");
    }
}
