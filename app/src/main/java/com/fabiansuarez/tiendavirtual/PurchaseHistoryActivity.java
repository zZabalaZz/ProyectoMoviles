package com.fabiansuarez.tiendavirtual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class PurchaseHistoryActivity extends AppCompatActivity {

    private ListView lvPurchaseHistory;
    private PurchaseHistoryAdapter purchaseHistoryAdapter;
    private List<Purchase> purchaseList;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        lvPurchaseHistory = findViewById(R.id.lvPurchaseHistory);
        Button btnClearHistory = findViewById(R.id.btn_clear_history);
        Button btnBackToStore = findViewById(R.id.btn_back_to_store);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Cargar el historial de compras desde SharedPreferences
        String historyJson = sharedPreferences.getString("purchaseHistory", "[]");
        purchaseList = gson.fromJson(historyJson, new TypeToken<List<Purchase>>() {}.getType());

        // Configurar el adaptador para la lista del historial de compras
        purchaseHistoryAdapter = new PurchaseHistoryAdapter(this, purchaseList);
        lvPurchaseHistory.setAdapter(purchaseHistoryAdapter);

        // Configurar el botón para limpiar el historial de compras
        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPurchaseHistory();
            }
        });

        // Configurar el botón para regresar a la tienda
        btnBackToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToStore();
            }
        });
    }

    private void clearPurchaseHistory() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("purchaseHistory");
        editor.apply();

        // Limpiar la lista y notificar al adaptador
        purchaseList.clear();
        purchaseHistoryAdapter.notifyDataSetChanged();
    }

    private void backToStore() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
