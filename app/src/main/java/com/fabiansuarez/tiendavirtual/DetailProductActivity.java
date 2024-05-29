package com.fabiansuarez.tiendavirtual;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Product myProduct;
    private TextView titleTextView, priceTextView, descriptionTextView;
    private ImageView ivProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        putFindViewById();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        myProduct = (Product) getIntent().getSerializableExtra("product");

        loadInformation();
    }

    private void putFindViewById() {
        toolbar = findViewById(R.id.top_app_bar_detail_product);
        titleTextView = findViewById(R.id.tv_title_detail_product);
        descriptionTextView = findViewById(R.id.tv_decription_detail_product);
        priceTextView = findViewById(R.id.tv_price_detail_product);
        ivProduct = findViewById(R.id.iv_detail_product);
    }

    private void loadInformation() {
        titleTextView.setText(myProduct.getName());
        priceTextView.setText("$ " + myProduct.getPrice().toString());
        descriptionTextView.setText(myProduct.getDescripcion());
        Picasso.get().load(myProduct.getUrlImage())
                .into(ivProduct);
    }
}
