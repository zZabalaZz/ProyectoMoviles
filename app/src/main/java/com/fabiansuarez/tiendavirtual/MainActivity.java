package com.fabiansuarez.tiendavirtual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ArrayList<SlideModel> imageList = new ArrayList<>();
    private ImageSlider imageSlider;
    private Toolbar topAppBar;
    private ImageView ivProfile;
    private ArrayList<Product> productsList = new ArrayList<>();
    private User userSession = new User();
    private ImageView userImageProfil;
    private RecyclerView rvProducts;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    private AdapterProduct adapterProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvProducts = findViewById(R.id.rv_products);
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        userImageProfil = findViewById(R.id.iv_profile_home_user);
        Picasso.get().load(userSession.getUrlImageProfil()).into(userImageProfil);
        imageSlider = findViewById(R.id.image_slider_home);

        imageList.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4FkmvIrIJoaTQ6AXoIIMV3OLcuii_vahisR2bR8UtKw&s", ScaleTypes.FIT));
        imageList.add(new SlideModel("https://www.movilexito.com/sites/default/files/2021-08/V3_Paqu_LP_Movil_Recibe50_770x315_030821.jpg", ScaleTypes.FIT));
        imageList.add(new SlideModel("https://vtex-resources.s3.amazonaws.com/landings/2024/marzo/landing-dia-redondo/images/mobile/banner.jpg", ScaleTypes.FIT));
        imageList.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4FkmvIrIJoaTQ6AXoIIMV3OLcuii_vahisR2bR8UtKw&s", "Aproveche.", null));
        imageList.add(new SlideModel("https://www.movilexito.com/sites/default/files/2021-08/V3_Paqu_LP_Movil_Recibe50_770x315_030821.jpg", "Elephants and tigers may become extinct.", null));
        imageList.add(new SlideModel("https://vtex-resources.s3.amazonaws.com/landings/2024/marzo/landing-dia-redondo/images/mobile/banner.jpg", "And people do that.", null));
        imageSlider.setImageList(imageList, ScaleTypes.FIT);

        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        adapterProduct = new AdapterProduct(productsList);
        adapterProduct.setOnItemClickListener(new AdapterProduct.OnItemClickListener() {
            @Override
            public void onItemClick(Product myProduct, int position) {
                Intent myIntent = new Intent(MainActivity.this, DetailProductActivity.class);
                myIntent.putExtra("product", myProduct);
                startActivity(myIntent);
            }

            @Override
            public void onAddToCartClick(Product product) {
                addToCart(product);
            }
        });
        rvProducts.setAdapter(adapterProduct);

        topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

        topAppBar.setNavigationOnClickListener(view ->
                Toast.makeText(MainActivity.this, "Click en Account", Toast.LENGTH_SHORT).show());

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.add_product) {
                startActivity(new Intent(MainActivity.this, FormProductActivity.class));
                return true;
            }  else if (item.getItemId() == R.id.purchase_history) {
                startActivity(new Intent(MainActivity.this, PurchaseHistoryActivity.class));
                return true;
            } else if (item.getItemId() == R.id.cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            } else if (item.getItemId() == R.id.log_out) {
                cerrarSesion();
                return true;
            }
            return false;
        });

        FloatingActionButton takePhotoButton = findViewById(R.id.fab_take_photo);
        takePhotoButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TakePhotoActivity.class);
            startActivity(intent);
        });


        loadProducts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(MainActivity.this, DisplayPhotoActivity.class);
            intent.putExtra("photo", imageBitmap);
            startActivity(intent);
        }
    }

    private void loadFakeData() {
        productsList.add(new Product("1", "Crema de Afeitar", 12000.0, "https://locatelcolombia.vtexassets.com/arquivos/ids/330757-800-450?v=638086193988830000&width=800&height=450&aspect=true", "bueno"));
        productsList.add(new Product("2", "DESENGRASNATE", 800.0, "https://i0.wp.com/www.faggidistribuciones.com.co/wp-content/uploads/2020/07/Dise%C3%B1o-sin-t%C3%ADtulo-2021-05-19T164759.572.jpg?fit=800%2C800&ssl=1", "bueno"));
        productsList.add(new Product("3", "JABÓN", 1500.0, "https://e00-telva.uecdn.es/assets/multimedia/imagenes/2019/11/08/15732087888279.jpg", "bueno"));
        productsList.add(new Product("4", "PAPAS", 200.0, "https://i5.walmartimages.com.mx/gr/images/product-images/img_large/00750101111559L1.jpg", "bueno"));
        productsList.add(new Product("5", "PERFUME", 300.0, "https://belcorpcolombia.vtexassets.com/arquivos/ids/953747-800-800?v=638458740156730000&width=800&height=800&aspect=true", "bueno"));
        productsList.add(new Product("6", "JABÓN LIQUIDO", 12000.0, "https://skincarelab.com.co/wp-content/uploads/2020/05/jabon-liquido-x-230_NUEVO.png", "bueno"));
        productsList.add(new Product("7", "SALSA", 800.0, "https://vaquitaexpress.com.co/media/catalog/product/cache/e89ece728e3939ca368b457071d3c0be/7/7/7702047130308_38.jpg", "bueno"));
        productsList.add(new Product("8", "SHAMPOO", 1500.0, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_zMEg7KtMzdCRRtlHAiwM1mgOJrjuK5G6Zg&s", "bueno"));
        productsList.add(new Product("9", "TALCO", 200.0, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0dib_qCpeo7vgYoAMC5FCnJ3RPP7vgJaECw&s", "bueno"));
        productsList.add(new Product("10", "TERMO", 300.0, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeB9UvdnBB2pMfHa7sZlAfFHiwd0tPP5jpnQ&s", "bueno"));
    }

    private void loadProducts() {
        String productsJson = sharedPreferences.getString("products", "[]");
        List<Product> loadedProducts = gson.fromJson(productsJson, new TypeToken<List<Product>>() {}.getType());
        if (loadedProducts == null) {
            loadedProducts = new ArrayList<>();
        }

        // Combinar productos iniciales con los productos cargados
        productsList.clear();
        productsList.addAll(getInitialProducts());
        productsList.addAll(loadedProducts);

        saveProducts();
    }

    private List<Product> getInitialProducts() {
        List<Product> initialProducts = new ArrayList<>();
        initialProducts.add(new Product("1", "Crema de Afeitar", 12000.0, "https://locatelcolombia.vtexassets.com/arquivos/ids/330757-800-450?v=638086193988830000&width=800&height=450&aspect=true", "bueno"));
        initialProducts.add(new Product("2", "DESENGRASNATE", 800.0, "https://i0.wp.com/www.faggidistribuciones.com.co/wp-content/uploads/2020/07/Dise%C3%B1o-sin-t%C3%ADtulo-2021-05-19T164759.572.jpg?fit=800%2C800&ssl=1", "bueno"));
        initialProducts.add(new Product("3", "JABÓN", 1500.0, "https://e00-telva.uecdn.es/assets/multimedia/imagenes/2019/11/08/15732087888279.jpg", "bueno"));
        initialProducts.add(new Product("4", "PAPAS", 200.0, "https://i5.walmartimages.com.mx/gr/images/product-images/img_large/00750101111559L1.jpg", "bueno"));
        initialProducts.add(new Product("5", "PERFUME", 300.0, "https://belcorpcolombia.vtexassets.com/arquivos/ids/953747-800-800?v=638458740156730000&width=800&height=800&aspect=true", "bueno"));
        initialProducts.add(new Product("6", "JABÓN LIQUIDO", 12000.0, "https://skincarelab.com.co/wp-content/uploads/2020/05/jabon-liquido-x-230_NUEVO.png", "bueno"));
        initialProducts.add(new Product("7", "SALSA", 800.0, "https://vaquitaexpress.com.co/media/catalog/product/cache/e89ece728e3939ca368b457071d3c0be/7/7/7702047130308_38.jpg", "bueno"));
        initialProducts.add(new Product("8", "SHAMPOO", 1500.0, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_zMEg7KtMzdCRRtlHAiwM1mgOJrjuK5G6Zg&s", "bueno"));
        initialProducts.add(new Product("9", "TALCO", 200.0, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0dib_qCpeo7vgYoAMC5FCnJ3RPP7vgJaECw&s", "bueno"));
        initialProducts.add(new Product("10", "TERMO", 300.0, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeB9UvdnBB2pMfHa7sZlAfFHiwd0tPP5jpnQ&s", "bueno"));
        return initialProducts;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();

        if (rvProducts != null && rvProducts.getAdapter() != null) {
            ((AdapterProduct) rvProducts.getAdapter()).notifyDataSetChanged();
        } else {
            Log.e("MainActivity", "RecyclerView or its adapter is null");
        }
    }

    public void cerrarSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Crear una nueva intención para iniciar la actividad de inicio de sesión
        Intent intent = new Intent(this, LoginActivity.class);

        // Agregar banderas para limpiar la pila de actividades y crear una nueva tarea
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Iniciar la actividad de inicio de sesión y cerrar la actividad actual
        startActivity(intent);
        finish();
    }

    private void addToCart(Product product) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String cartJson = sharedPreferences.getString("cart", "[]");
        List<CartItem> cartItemList = gson.fromJson(cartJson, new TypeToken<List<CartItem>>() {}.getType());

        boolean itemExists = false;
        for (CartItem item : cartItemList) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cartItemList.add(new CartItem(product, 1));
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cart", gson.toJson(cartItemList));
        editor.apply();

        Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
    }

    private void saveProducts() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("products", gson.toJson(productsList));
        editor.apply();
    }
}