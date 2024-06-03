package ru.mirea.androidcoursework.entry;

import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import ru.mirea.androidcoursework.Activity;
import ru.mirea.androidcoursework.R;
import ru.mirea.androidcoursework.databinding.LoginFragmentBinding;


public class LoginFragment extends Fragment
{
    public LoginFragment()
    {
        // Required empty public constructor
    }

    private LoginFragmentBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private boolean isRememberMe;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void init()
    {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        binding.tvRegister.setOnClickListener(this::onRegisterFragment);
        binding.btLogin.setOnClickListener(this::onLoginInFireBase);
        binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> isRememberMe = isChecked);

        try {sharedPreferences = requireActivity().getSharedPreferences("login", MODE_PRIVATE);}
        catch (Exception e){ e.printStackTrace();}
        isRememberMe = sharedPreferences.getBoolean("isRememberMe", false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (currentUser != null && isRememberMe)
        {
            if (currentUser.getEmail().equals("admin@mail.ru")) sharedPreferences.edit().putBoolean("isAdmin", true).apply();
            else sharedPreferences.edit().putBoolean("isAdmin", false).apply();

            onHomeFragment();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onRegisterFragment(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
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

        if (isRememberMe)
        {
            sharedPreferences.edit().putBoolean("isRememberMe", isRememberMe).apply();
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        if (email.equals("admin@mail.ru")) sharedPreferences.edit().putBoolean("isAdmin", true).apply();
                        else sharedPreferences.edit().putBoolean("isAdmin", false).apply();
                        onHomeFragment();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onHomeFragment()
    {
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
    }
}
