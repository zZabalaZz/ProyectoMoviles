package com.fabiansuarez.tiendavirtual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class PurchaseHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<Purchase> purchaseList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PurchaseHistoryAdapter(Context context, List<Purchase> purchaseList) {
        this.context = context;
        this.purchaseList = purchaseList;
    }

    @Override
    public int getCount() {
        return purchaseList.size();
    }

    @Override
    public Object getItem(int position) {
        return purchaseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_purchase, parent, false);
        }

        Purchase purchase = purchaseList.get(position);

        TextView tvPurchaseDate = convertView.findViewById(R.id.tvPurchaseDate);
        TextView tvTotalPrice = convertView.findViewById(R.id.tvTotalPrice);

        tvPurchaseDate.setText(dateFormat.format(purchase.getDate()));
        tvTotalPrice.setText(String.format("Precio Total: $%.2f", purchase.getTotalPrice()));

        return convertView;
    }
}

