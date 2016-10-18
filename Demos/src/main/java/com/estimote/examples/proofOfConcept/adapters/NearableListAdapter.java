package com.estimote.examples.proofOfConcept.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.estimote.examples.proofOfConcept.R;
import com.estimote.sdk.Nearable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Displays basic information about nearable.
 *
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class NearableListAdapter extends BaseAdapter {

  private ArrayList<Nearable> nearables;
  private LayoutInflater inflater;

  public NearableListAdapter(Context context) {
    this.inflater = LayoutInflater.from(context);
    this.nearables = new ArrayList<>();
  }

  public void replaceWith(Collection<Nearable> newNearables) {
    this.nearables.clear();
    this.nearables.addAll(newNearables);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return nearables.size();
  }

  @Override
  public Nearable getItem(int position) {
    return nearables.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    view = inflateIfRequired(view, position, parent);
    bind(getItem(position), view);
    return view;
  }

    String sticker_Name =  "";
  private void bind(Nearable nearable, View view) {

      if(nearable.identifier.equals("67f5428a8837df2b")){
          sticker_Name="Shoe";
      }

      if(nearable.identifier.equals("269ea3df261f3160")){
          sticker_Name="G.W. bin";
      }

      if(nearable.identifier.equals("27bd8ea432e7aa07")){
          sticker_Name="bike";
      }

      if(nearable.identifier.equals("f687c289768d12e6")){
          sticker_Name="Chair";
      }

      if(nearable.identifier.equals("e7c108df695cfed4")){
          sticker_Name="R.ew  m Bin";
      }
      if(nearable.identifier.equals("9fece25292cf1198")){
          sticker_Name="....";
      }

      if(nearable.identifier.equals("8d35e485345aec02")){
          sticker_Name="Dog";
      }

      if(nearable.identifier.equals("923e56a922c88a63")){
          sticker_Name = "Bin 1 (Baby blue Sticker)";
      }

      if(nearable.identifier.equals("f034e1fb3d756b81")){
          sticker_Name="Genearic";
      }


      if(nearable.identifier.equals("87d566c30fd8be4a")){
          sticker_Name = "Bin 2 (dark blue Sticker)";

      }




    ViewHolder holder = (ViewHolder) view.getTag();
//    holder.macTextView.setText(String.format("ID: %s (%s)", nearable.identifier, Utils.computeProximity(nearable).toString()));
//    holder.majorTextView.setText("Major: " + nearable.region.getMajor());
//    holder.minorTextView.setText("Minor: " + nearable.region.getMinor());
//    holder.measuredPowerTextView.setText("MPower: " + nearable.power.powerInDbm);
  //  holder.rssiTextView.setText("RSSI: " + nearable.rssi);
    holder.stickerNameTextView.setText(sticker_Name);
  }

  private View inflateIfRequired(View view, int position, ViewGroup parent) {
    if (view == null) {
      view = inflater.inflate(R.layout.nearable_item, null);
      view.setTag(new ViewHolder(view));
    }
    return view;
  }

  static class ViewHolder {
    final TextView macTextView;
    final TextView majorTextView;
    final TextView minorTextView;
    final TextView measuredPowerTextView;
    final TextView rssiTextView;
    final TextView stickerNameTextView;

    ViewHolder(View view) {
      macTextView = (TextView) view.findViewWithTag("mac");
      majorTextView = (TextView) view.findViewWithTag("major");
      minorTextView = (TextView) view.findViewWithTag("minor");
      measuredPowerTextView = (TextView) view.findViewWithTag("mpower");
      rssiTextView = (TextView) view.findViewWithTag("rssi");
      stickerNameTextView = (TextView) view.findViewWithTag("sname");
    }
  }
}
