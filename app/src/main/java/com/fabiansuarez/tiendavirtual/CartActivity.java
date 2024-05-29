package com.fabiansuarez.tiendavirtual;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import java.util.Date;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView lvCartItems;
    private Button btnCheckout, btnContinueShopping;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvCartItems = findViewById(R.id.lvCartItems);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnContinueShopping = findViewById(R.id.btnContinueShopping);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Cargar los datos del carrito desde SharedPreferences
        String cartJson = sharedPreferences.getString("cart", "[]");
        cartItemList = gson.fromJson(cartJson, new TypeToken<List<CartItem>>(){}.getType());

        // Configurar el adaptador para la lista de items del carrito
        cartAdapter = new CartAdapter(this, cartItemList);
        lvCartItems.setAdapter(cartAdapter);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkout();
            }
        });

        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Guardar los datos del carrito en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cart", gson.toJson(cartItemList));
        editor.apply();
    }

    private void checkout() {
        if (cartItemList.isEmpty()) {
            Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        // Procesar la compra
        double totalPrice = 0;
        for (CartItem item : cartItemList) {
            double unitPrice = item.getProduct().getPrice();
            int quantity = item.getQuantity();

            // Verificar y loguear los valores
            if (unitPrice <= 0 || quantity <= 0) {
                Log.e("Checkout", "Error en los datos: Precio unitario o cantidad inválida.");
                Toast.makeText(this, "Error en los datos del producto: Precio o cantidad inválida.", Toast.LENGTH_SHORT).show();
                return;
            }

            double itemTotalPrice = unitPrice * quantity;
            totalPrice += itemTotalPrice;

            // Log para depuración
            Log.d("Checkout", "Producto: " + item.getProduct().getName() +
                    ", Cantidad: " + quantity +
                    ", Precio Unitario: " + unitPrice +
                    ", Precio Total del Item: " + itemTotalPrice);
        }

        Log.d("Checkout", "Precio Total de la Compra: " + totalPrice);

        // Crear una nueva compra
        Purchase purchase = new Purchase(new Date(), cartItemList, totalPrice);

        // Obtener el historial de compras actual
        String historyJson = sharedPreferences.getString("purchaseHistory", "[]");
        List<Purchase> purchaseHistory = gson.fromJson(historyJson, new TypeToken<List<Purchase>>() {}.getType());

        // Agregar la nueva compra al historial
        purchaseHistory.add(purchase);

        // Guardar el historial actualizado en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("purchaseHistory", gson.toJson(purchaseHistory));
        editor.apply();

        // Limpiar el carrito después del checkout
        cartItemList.clear();
        cartAdapter.notifyDataSetChanged();

        // Guardar los cambios en SharedPreferences
        editor.putString("cart", gson.toJson(cartItemList));
        editor.apply();

        Toast.makeText(this, "Compra realizada con éxito", Toast.LENGTH_SHORT).show();
    }


}
