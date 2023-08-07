package acs.upb.licenta.aplicatiegrup.adapters;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.eventActivities.ShoppingListActivity;

public class ShoppingListAdapter extends ArrayAdapter<String> {

    ArrayList<String> list;
    Context context;

    public ShoppingListAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.shopping_cart_item, items);
        this.context = context;
        list = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.shopping_cart_item, null);
            TextView item = convertView.findViewById(R.id.item);
            ImageView remove = convertView.findViewById(R.id.remove);
            CheckBox checkBox = convertView.findViewById(R.id.checked);
            checkBox.setChecked(ShoppingListActivity.getCheckSituation(position));
            item.setText(list.get(position));

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShoppingListActivity.removeItem(position);
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShoppingListActivity.checkItem(position);
                }
            });
        }
        return convertView;
    }
}
