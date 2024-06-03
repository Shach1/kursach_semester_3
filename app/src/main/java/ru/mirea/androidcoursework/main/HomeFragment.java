package ru.mirea.androidcoursework.main;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ru.mirea.androidcoursework.Activity;
import ru.mirea.androidcoursework.R;
import ru.mirea.androidcoursework.databinding.HomeFragmentBinding;


public class HomeFragment extends Fragment
{
    public HomeFragment()
    {
        // Required empty public constructor
    }

    HomeFragmentBinding binding;
    private SharedPreferences sharedPreferences;
    private boolean isAdmin;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
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
        Log.d("HomeFragment", "onViewCreated: ");

        try {
            sharedPreferences = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
            isAdmin = sharedPreferences.getBoolean("isAdmin", false);
        }
        catch (Exception e){ e.printStackTrace();}

        if(isAdmin) binding.btAddNewProduct.setVisibility(View.VISIBLE);
        binding.btAddNewProduct.setOnClickListener(this::onAddNewCardProduct);
    }

    private void onAddNewCardProduct(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addNewCardProductFragment);
    }


}
