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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.androidcoursework.Decoration.SpaceItemDecoration;
import ru.mirea.androidcoursework.R;
import ru.mirea.androidcoursework.adapter.CardProductPreviewAdapter;
import ru.mirea.androidcoursework.databinding.HomeFragmentBinding;
import ru.mirea.androidcoursework.entity.CardProduct;


public class HomeFragment extends Fragment
{
    public HomeFragment()
    {
        // Required empty public constructor
    }

    private HomeFragmentBinding binding;
    private SharedPreferences sharedPreferences;
    private boolean isAdmin;
    private DatabaseReference databaseReference;



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

        binding.rvProductsPreview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvProductsPreview.addItemDecoration(new SpaceItemDecoration(20));



        databaseReference = FirebaseDatabase.getInstance().getReference("CardProduct");

        // Создание слушателя для получения данных
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (binding == null) {
                    // Если binding равен null, прекращаем выполнение метода
                    return;
                }
                List<CardProduct> cardProductList = new ArrayList<>();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    CardProduct cardProduct = productSnapshot.getValue(CardProduct.class);
                    cardProductList.add(cardProduct);
                }

                // Создание адаптера и установка его для RecyclerView
                CardProductPreviewAdapter adapter = new CardProductPreviewAdapter(cardProductList);
                binding.rvProductsPreview.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("HomeFragment", "Failed to read value.", databaseError.toException());
            }
        };

        // Добавление слушателя к базе данных
        databaseReference.addValueEventListener(valueEventListener);

    }

    private void onAddNewCardProduct(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addNewCardProductFragment);
    }


}
