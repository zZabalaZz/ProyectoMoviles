package com.fabiansuarez.tiendavirtual;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<CartItem> cartItemList;

    public CartAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @Override
    public int getCount() {
        return cartItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        }

        CartItem cartItem = cartItemList.get(position);

        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
        EditText etQuantity = convertView.findViewById(R.id.etQuantity);
        Button btnRemove = convertView.findViewById(R.id.btnRemove);

        tvProductName.setText(cartItem.getProduct().getName());
        etQuantity.setText(String.valueOf(cartItem.getQuantity()));

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No se necesita implementar
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Actualizar la cantidad en cartItemList cuando el usuario cambia la cantidad
                int quantity = 1;
                try {
                    quantity = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    // En caso de que el usuario ingrese un número inválido
                    quantity = 1;
                }
                cartItem.setQuantity(quantity);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItemList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
