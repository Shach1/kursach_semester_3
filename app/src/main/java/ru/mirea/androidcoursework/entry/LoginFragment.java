package ru.mirea.androidcoursework.entry;

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

import ru.mirea.androidcoursework.R;
import ru.mirea.androidcoursework.databinding.LoginFragmentBinding;
import ru.mirea.androidcoursework.main.HomeFragment;


public class LoginFragment extends Fragment
{
    public LoginFragment()
    {
        // Required empty public constructor
    }

    private LoginFragmentBinding binding;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Тут инифиализируем и устанавливаем слушателми событий

        mAuth = FirebaseAuth.getInstance();
        fragmentManager = getParentFragmentManager();
        binding.tvRegister.setOnClickListener(this::onRegisterFragment);
        binding.btLogin.setOnClickListener(this::onLogin);
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            // TODO: Изменить при получении пользователя
            Toast.makeText(getContext(), "User already logged in", Toast.LENGTH_SHORT).show();
            getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentConrainer, new HomeFragment())
                    .commit();
        }
        else
        {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onRegisterFragment(View view)
    {
        // TODO: Перейти на navigation
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentConrainer, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }


    private void onLogin(View view) {
        onLoginInFireBase(view);
        if (currentUser != null)
        {
            // TODO: Перейти на navigation
            getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentConrainer, new HomeFragment())
                    .commit();
        }

    }

    private void onLoginInFireBase(View view) {
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (email.isEmpty()) {
            // TODO: Сделать красный текст ошибки под полем
            Toast.makeText(getContext(), "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            // TODO: Сделать красный текст ошибки под полем
            Toast.makeText(getContext(), "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentConrainer, new HomeFragment())
                                .commit();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
