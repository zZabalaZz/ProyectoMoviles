package com.fabiansuarez.tiendavirtual;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);

        ImageView imageView = findViewById(R.id.imageView_display);
        TextView productInfo = findViewById(R.id.textView_product_info);
        Button takeAnotherPhoto = findViewById(R.id.button_take_another_photo);
        Button backToStore = findViewById(R.id.button_back_to_store);
        Button addToCart = findViewById(R.id.button_add_to_cart);

        String imagePath = getIntent().getStringExtra("image_path");
        boolean productExists = getIntent().getBooleanExtra("product_exists", false);
        String productName = getIntent().getStringExtra("product_name");
        double productPrice = getIntent().getDoubleExtra("product_price", 0.0);

        imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));

        if (productExists) {
            productInfo.setText("Nombre del producto: " + productName + "\nPrecio: $" + productPrice);
        } else {
            productInfo.setText("El producto no existe en la tienda");
        }

        takeAnotherPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(DisplayPhotoActivity.this, MainActivity.class);
            startActivity(intent);
        });

        backToStore.setOnClickListener(v -> {
            finish();
        });

        addToCart.setOnClickListener(v -> {
            // Lógica para agregar el producto al carrito
        });
    }
}
