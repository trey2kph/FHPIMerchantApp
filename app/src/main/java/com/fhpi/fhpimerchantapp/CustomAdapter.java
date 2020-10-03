package com.fhpi.fhpimerchantapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by TR1-05 on 2/22/2018.
 */
public class CustomAdapter extends BaseAdapter {
    AllItemsDTO allItemsDTO;
    Context context;
    LinearLayout divData;
    int id;
    private static LayoutInflater inflater = null;

    public CustomAdapter(AllItemsDTO allItemsDTO, Context context) {
        this.allItemsDTO = allItemsDTO;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (allItemsDTO.getItems().size() > 0)
            return allItemsDTO.getItems().size();
        return 0;
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView lblTransID, lblMerchant, lblPoints, lblDate, lblType;
        LinearLayout divData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder h = new Holder();
        View v = inflater.inflate(R.layout.row_view, null);

        h.divData = (LinearLayout)v.findViewById(R.id.divData);
        h.lblTransID = (TextView)v.findViewById(R.id.lblTransID);
        h.lblMerchant = (TextView)v.findViewById(R.id.lblMerchant);
        h.lblPoints = (TextView)v.findViewById(R.id.lblPoints);
        h.lblDate = (TextView)v.findViewById(R.id.lblDate);
        h.lblType = (TextView)v.findViewById(R.id.lblType);

        h.lblTransID.setText(allItemsDTO.getItems().get(position).getTrans_id());
        h.lblMerchant.setText(allItemsDTO.getItems().get(position).getTrans_merchant());
        h.lblPoints.setText(allItemsDTO.getItems().get(position).getTrans_points() + ".00");
        h.lblDate.setText(allItemsDTO.getItems().get(position).getTrans_date());
        h.lblType.setText("(" + allItemsDTO.getItems().get(position).getTrans_type() + ")");

        return v;
    }
}
