package com.tajway.tajwaycabs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.responsedata.DriverAccount;

import java.util.List;

public class BookingIDAdapter extends BaseAdapter {
    Context context;
    List<DriverAccount> brandList;
    LayoutInflater inflter;

    public BookingIDAdapter(Context applicationContext, List<DriverAccount> brandList) {
        this.context = applicationContext;
        this.brandList = brandList;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
    public int getCount() {
        return brandList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_spinner, null);
        TextView text1 = view.findViewById(R.id.text1);
        text1.setText(brandList.get(i).getBookingL_id());
      /*  if (brandList.get(i).getBrandName().equalsIgnoreCase("Select")) {
            text1.setTextColor(context.getResources().getColor(R.color.grey));
        } else {
            text1.setTextColor(context.getResources().getColor(R.color.white));
        }
*/
        return view;
    }
}