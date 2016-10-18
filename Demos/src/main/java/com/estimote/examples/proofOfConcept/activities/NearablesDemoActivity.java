package com.estimote.examples.proofOfConcept.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.estimote.examples.proofOfConcept.R;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Demo that shows nearable details.

public class NearablesDemoActivity extends Activity {

    private static final String TAG = NearablesDemoActivity.class.getSimpleName();

    private Nearable currentNearable;
    private BeaconManager beaconManager;

    boolean binFliped = false;
    StringBuilder myBuilder;
    StringBuilder fileSave;
    Button logBtn;
    String separator = System.getProperty("line.separator");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearable_demo);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        currentNearable = getIntent().getExtras().getParcelable(ListNearablesActivity.EXTRAS_NEARABLE);
        displayCurrentNearableInfo();

        beaconManager = new BeaconManager(this);

    }

    public void showLog(View view){

        Intent i = new Intent(this, DisplayBinLog.class );
        startActivity(i);


    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> nearables) {
                updateCurrentNearable(nearables);
                displayCurrentNearableInfo();
                beaconManager.setForegroundScanPeriod(1, 0);
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startNearableDiscovery();
                beaconManager.setForegroundScanPeriod(1, 0);
            }
        });
    }

    @Override
    protected void onStop() {
        beaconManager.disconnect();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    String sticker_Name;
    String binStatus;
    boolean binStatusCheck;
    String currentTimeStamp;
    boolean timeStampRecieved = false;

    public boolean checkifLifted() {

        binStatus = currentNearable.orientation.toString();

        if (binStatus == "RIGHT_SIDE") {
            binStatusCheck = true;

            if (timeStampRecieved == false) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                currentTimeStamp = dateFormat.format(new Date()); // Find todays date
                timeStampRecieved = true;
            }
        }

        return binStatusCheck;
    }


    //**Switch**
    private void displayCurrentNearableInfo() {


        if (currentNearable.identifier.equals("67f5428a8837df2b")) {
            sticker_Name = "Shoe";
        }

        if (currentNearable.identifier.equals("269ea3df261f3160")) {
            sticker_Name = "G.W. bink";
        }

        if (currentNearable.identifier.equals("27bd8ea432e7aa07")) {
            sticker_Name = "bike";
        }

        if (currentNearable.identifier.equals("f687c289768d12e6")) {
            sticker_Name = "Chair";
        }

        if (currentNearable.identifier.equals("e7c108df695cfed4")) {
            sticker_Name = "R. Bin";
        }
        if (currentNearable.identifier.equals("9fece25292cf1198")) {
            sticker_Name = "....";
        }

        if (currentNearable.identifier.equals("8d35e485345aec02")) {
            sticker_Name = "Dog";
        }

        if (currentNearable.identifier.equals("923e56a922c88a63")) {
            sticker_Name = "Bin 1 (Baby blue Sticker)";
        }

        if (currentNearable.identifier.equals("f034e1fb3d756b81")) {
            sticker_Name = "Genearic";
        }


        if (currentNearable.identifier.equals("87d566c30fd8be4a")) {
            sticker_Name = "Bin 2 (dark blue Sticker)";

        }

        checkifLifted();
        myBuilder = new StringBuilder()

                //**Unnessasary info for Demo**

                //append("Major: ").append(currentNearable.region.getMajor()).append("\n")
                // .append("Minor: ").append(currentNearable.region.getMinor()).append("\n")
                //.append("Advertising interval: ").append("2000").append("ms\n")
                //.append("Broadcasting power: ").append(currentNearable.power.powerInDbm).append(" dBm\n")
                // .append("Battery level: ").append(currentNearable.batteryLevel.toString()).append("\n")
                // .append("Firmware: ").append(currentNearable.firmwareVersion).append("\n\n")

                .append("Has bin Lifted: ").append(binStatusCheck ? "Yes" : "No").append("\n\n")
                .append("Is bin in Motion: ").append(currentNearable.isMoving ? "Yes" : "No").append("\n\n")
                .append("Temperature: ").append(String.format("%.1f\u00b0C", currentNearable.temperature)).append("\n\n")
                .append(String.format("Motion Data: x: %.0f   y: %.0f   z: %.0f", currentNearable.xAcceleration, currentNearable.yAcceleration, currentNearable.zAcceleration)).append("\n\n")
                .append("Orientation: ").append(currentNearable.orientation.toString()).append("\n\n")
                  .append("Time Lifted: ").append(currentTimeStamp).append("\n\n");

        int count = 0;
        if (binStatusCheck) {
            do {

                myBuilder.append("Time Lifted: ").append(currentTimeStamp).append("\n\n");
                count++;
            }
            while (count == 0);


        }

        myBuilder.append("Sticker Name: ").append(sticker_Name).append("\n\n")
                .append("Unique Identifier: ").append(currentNearable.identifier).append("\n\n");


        //file save
        fileSave = new StringBuilder()
                .append(sticker_Name).append("\t\t\t\t\t\t")
                .append(binStatusCheck ? "Yes" : "No").append("\t\t\t\t\t\t")
                .append(currentTimeStamp).append("\n");


        TextView infoText = (TextView) findViewById(R.id.nearable_static_details);
        infoText.setText(myBuilder.toString());


        //if bin has not been lifted do the following
        if (binFliped == false) {
            //check to see if sticker is upside down (has been lifted)
            if (currentNearable.orientation.toString() == "RIGHT_SIDE") {
                binFliped = true;
                new AlertDialog.Builder(this).setTitle("Bin Status").setMessage("Bin Lifted").setNeutralButton("Close", null).show();
                try {
//                    FileOutputStream fos = openFileOutput("text.txt", MODE_APPEND);
//                    OutputStreamWriter osw = new OutputStreamWriter(fos);
//                  //  osw.write(String.valueOf(System.getProperty("line.separator").getBytes()));
//                    osw.write(fileSave.toString());
//                    osw.append(separator);
//                    osw.flush();
//                    osw.close();
//                    Toast.makeText(getBaseContext(), "Written to FIle", Toast.LENGTH_LONG).show();

                    //**
//                    File myFile = new File("/sdcard/myBinLog.txt");
//                    if(!myFile.exists())
//                         myFile.createNewFile();
//                    FileOutputStream fOut = new FileOutputStream(myFile);
//                    OutputStreamWriter myOutWriter =new OutputStreamWriter(fOut);
//


                    FileOutputStream fos = openFileOutput("text.txt", MODE_APPEND);
                   OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
                   // myOutWriter.append(txtData.getText());
                    myOutWriter.append(fileSave.toString());
                    myOutWriter.flush();
                    myOutWriter.close();
                    fos.close();
                   // Toast.makeText(getBaseContext(),"Done writing SD 'myBinLog.txt'", Toast.LENGTH_SHORT).show();




                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String outpout = readFromFile();
                //new AlertDialog.Builder(this).setTitle("Bin Status").setMessage(outpout).setNeutralButton("Close", null).show();

            }
        }
    }





    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("text.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    private void updateCurrentNearable(List<Nearable> nearables) {
        for (Nearable nearable : nearables) {
            if (nearable.equals(currentNearable)) {
                currentNearable = nearable;
            }
        }
    }
}
