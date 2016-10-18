package com.estimote.examples.proofOfConcept.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.examples.proofOfConcept.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DisplayBinLog extends Activity {

    private TextView binLog;
    String textToSaveString ;
    int count = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bin_log);
        binLog = (TextView) findViewById(R.id.displayFile);
        binLog.setText(readFromFile());
        textToSaveString = binLog.getText().toString();


    }

    int countFile =0;
    private void writeToFile(String data) {

        try {

            File myFile = new File("/sdcard/myBinLog"+countFile+".txt");
                    if(!myFile.exists())
                         myFile.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(myFile);
                    OutputStreamWriter outputStreamWriter =new OutputStreamWriter(fOut);



            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        countFile++;

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_bin_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                stringBuilder.append("ID\t\t\t\t\t\t\t\tLifted\t\t\t\t\t\t\tTime\n\n");
                stringBuilder.append("------------------------------------------------\n" +
                        "\n");

                while ((receiveString = bufferedReader.readLine()) != null) {

                    stringBuilder.append("\n\n"+receiveString);
                    stringBuilder.append("\n\n");




                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
            else
                ret = "Log Empty,\tNo bins lifted yet";
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    public void clear(View view) throws IOException {
//        try{
//            FileOutputStream fos = openFileOutput("text.txt", M);
//            OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
//            myOutWriter.write("/");
//            myOutWriter.flush();
//            myOutWriter.close();
//            fos.close();



        FileOutputStream fos = openFileOutput("text.txt", MODE_PRIVATE);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
        myOutWriter.write("");
        myOutWriter.close();

        binLog.setText(readFromFile());



    }


    public void save(View view){
        writeToFile(textToSaveString);
        Toast.makeText(getBaseContext(),"Done writing SD 'myBinLog.txt'", Toast.LENGTH_SHORT).show();

    }
}
