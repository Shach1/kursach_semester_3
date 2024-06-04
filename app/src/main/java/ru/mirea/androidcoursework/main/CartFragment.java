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

import ru.mirea.androidcoursework.Decoration.SpaceItemDecoration;
import ru.mirea.androidcoursework.adapter.CardProductPreviewInCartAdapter;
import ru.mirea.androidcoursework.databinding.CartFragmentBinding;
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

        initGlobal();
    }


    private void initGlobal()
    {
        productsInCart = ProductsInCart.getInstance();

        binding.rvCartProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCartProducts.addItemDecoration(new SpaceItemDecoration(20));
        CardProductPreviewInCartAdapter adapter = new CardProductPreviewInCartAdapter(productsInCart.getProductsInCart(), this);
        binding.rvCartProducts.setAdapter(adapter);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = productsInCart.calculateTotalPrice();
        binding.tvTotalPrice.setText(totalPrice + " ₽");
    }

    @Override
    public void onCartChanged() {
        updateTotalPrice();
    }
}
