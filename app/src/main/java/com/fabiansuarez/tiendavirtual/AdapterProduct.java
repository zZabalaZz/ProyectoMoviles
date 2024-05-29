package com.fabiansuarez.tiendavirtual;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    private ArrayList<Product> listadoObjetos;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AdapterProduct(ArrayList<Product> listadoObjetos) {
        this.listadoObjetos = listadoObjetos;
        this.onItemClickListener = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.associate(listadoObjetos.get(position));
    }

    @Override
    public int getItemCount() {
        return listadoObjetos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameProduct, priceProduct;
        private ImageView ivProduct;
        private Button btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.tv_name_product);
            priceProduct = itemView.findViewById(R.id.tv_price_product);
            ivProduct = itemView.findViewById(R.id.iv_product_item);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }

        public void associate(Product myProduct) {
            nameProduct.setText(myProduct.getName());
            priceProduct.setText(myProduct.getPrice().toString());
            Picasso.get().load(myProduct.getUrlImage())
                    .into(ivProduct);

            if (onItemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(myProduct, getAdapterPosition());
                    }
                });

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onAddToCartClick(myProduct);
                    }
                });
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product myProduct, int position);
        void onAddToCartClick(Product myProduct);
    }
}
