package com.estimote.examples.proofOfConcept.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.estimote.examples.proofOfConcept.R;

public class AllDemosActivity extends Activity {

  Button logBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.all_demos);

        findViewById(R.id.nearables_demo_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(AllDemosActivity.this, ListNearablesActivity.class);
        intent.putExtra(ListNearablesActivity.EXTRAS_TARGET_ACTIVITY, NearablesDemoActivity.class.getName());
        startActivity(intent);
      }
    });

    findViewById(R.id.logBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(AllDemosActivity.this, DisplayBinLog.class);
        startActivity(intent);
      }
    });

  }
}
