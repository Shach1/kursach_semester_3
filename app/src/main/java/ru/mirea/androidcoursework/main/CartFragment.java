package ru.mirea.androidcoursework.main;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.mirea.androidcoursework.Decoration.SpaceItemDecoration;
import ru.mirea.androidcoursework.adapter.CardProductPreviewInCartAdapter;
import ru.mirea.androidcoursework.databinding.CartFragmentBinding;
import ru.mirea.androidcoursework.entity.CardProductInCart;
import ru.mirea.androidcoursework.instance.ProductsInCart;
import ru.mirea.androidcoursework.interfaceses.IOnCartChangedListener;


public class CartFragment extends Fragment implements IOnCartChangedListener
{
    public CartFragment()
    {
        // Required empty public constructor
    }

    private CartFragmentBinding binding;
    private ProductsInCart productsInCart;
    private CardProductPreviewInCartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CartFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("CartFragment", "onViewCreated: ");

        init();
    }


    private void init()
    {
        productsInCart = ProductsInCart.getInstance();
        binding.btPurchase.setOnClickListener(this::onPurchase);
        binding.rvCartProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCartProducts.addItemDecoration(new SpaceItemDecoration(20));
        adapter = new CardProductPreviewInCartAdapter(productsInCart.getProductsInCart(), this);
        binding.rvCartProducts.setAdapter(adapter);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = productsInCart.calculateTotalPrice();
        binding.tvTotalPrice.setText(totalPrice + " ₽");
    }

    private void onPurchase(View view)
    {
        if (productsInCart.getProductsInCart().isEmpty())
        {
            return;
        }
        List<Product> products = new ArrayList<>();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", ",");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String date = sdf.format(new Date());

        for (CardProductInCart productInCart : productsInCart.getProductsInCart())
        {
            products.add(new Product(productInCart.getCardProduct().getTitle(), productInCart.getAmount()));
        }
        Map<String, Product> productsMap = new HashMap<>();
        for (Product product : products) {
            String key = FirebaseDatabase.getInstance().getReference().child("orders").child(email).child(date).push().getKey();
            productsMap.put(key, product);
        }
        FirebaseDatabase.getInstance().getReference().child("orders").child(email).child(date).setValue(productsMap);

        productsInCart.clearProductsInCart();
        updateTotalPrice();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCartChanged() {
        updateTotalPrice();
    }

    private class Product
    {
        private String name;
        private int amount;

        public Product(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public int getAmount() {
            return amount;
        }
    }
}
