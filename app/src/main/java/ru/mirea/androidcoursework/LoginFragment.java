package ru.mirea.androidcoursework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.androidcoursework.databinding.EntryFragmentBinding;

public class LoginFragment extends Fragment
{
    public LoginFragment()
    {
        // Required empty public constructor
    }

    private EntryFragmentBinding binding;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EntryFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Тут инифиализируем и устанавливаем слушателми событий

        mAuth = FirebaseAuth.getInstance();
        fragmentManager = getParentFragmentManager();
        binding.tvRegister.setOnClickListener(v -> onRegisterFragment(v));
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            // TODO: Изменить при получении пользователя
            Toast.makeText(getContext(), "User already logged in", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    public void onRegisterFragment(View view)
    {
        // TODO: Перейти на navigation
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentConrainer, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }
}
