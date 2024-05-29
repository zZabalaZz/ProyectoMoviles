package com.fabiansuarez.tiendavirtual;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FormProductActivity extends AppCompatActivity {

    private TextInputLayout nameTextField;
    private TextInputLayout descriptionTextField;
    private TextInputLayout priceTextField;
    private TextInputLayout imageUrlTextField;
    private Button saveButton;
    private Toolbar topAppBar;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_product);

        nameTextField = findViewById(R.id.et_name_product);
        descriptionTextField = findViewById(R.id.et_description_product);
        priceTextField = findViewById(R.id.et_price_product);
        imageUrlTextField = findViewById(R.id.et_image_product);
        saveButton = findViewById(R.id.btn_save_information);
        topAppBar = findViewById(R.id.topAppBar_form_add_product);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        topAppBar.setNavigationOnClickListener(view -> finish());

        saveButton.setOnClickListener(v -> {
            if (validateForm()) {
                addProduct();
            }
        });
    }

    private boolean validateForm() {
        nameTextField.setError(null);
        descriptionTextField.setError(null);
        priceTextField.setError(null);
        imageUrlTextField.setError(null);

        String name = nameTextField.getEditText().getText().toString().trim();
        String description = descriptionTextField.getEditText().getText().toString().trim();
        String price = priceTextField.getEditText().getText().toString().trim();
        String imageUrl = imageUrlTextField.getEditText().getText().toString().trim();

        if (name.isEmpty()) {
            nameTextField.setError(getString(R.string.por_favor_ingrese_el_nombre));
            return false;
        }

        if (description.isEmpty()) {
            descriptionTextField.setError(getString(R.string.por_favor_ingrese_la_descripcion));
            return false;
        }

        if (price.isEmpty()) {
            priceTextField.setError(getString(R.string.por_favor_ingrese_el_precio));
            return false;
        }

        if (imageUrl.isEmpty()) {
            imageUrlTextField.setError(getString(R.string.por_favor_ingrese_la_url_de_la_imagen));
            return false;
        }
        return true;
    }

    private void addProduct() {
        String name = nameTextField.getEditText().getText().toString().trim();
        String description = descriptionTextField.getEditText().getText().toString().trim();
        double price;
        try {
            price = Double.parseDouble(priceTextField.getEditText().getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show();
            return;
        }
        String imageUrl = imageUrlTextField.getEditText().getText().toString().trim();

        Product newProduct = new Product(generateProductId(), name, price, imageUrl, description);

        String productsJson = sharedPreferences.getString("products", "[]");
        List<Product> productsList = gson.fromJson(productsJson, new TypeToken<List<Product>>() {}.getType());
        if (productsList == null) {
            productsList = new ArrayList<>();
        }
        productsList.add(newProduct);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("products", gson.toJson(productsList));
        editor.apply();

        Toast.makeText(this, "Producto agregado exitosamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String generateProductId() {
        return "prod_" + System.currentTimeMillis();
    }
}
