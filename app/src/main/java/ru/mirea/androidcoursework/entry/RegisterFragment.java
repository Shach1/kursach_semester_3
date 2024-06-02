package ru.mirea.androidcoursework.entry;

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
import ru.mirea.androidcoursework.R;
import ru.mirea.androidcoursework.databinding.RegisterFragmentBinding;


public class RegisterFragment extends Fragment
{
    public RegisterFragment()
    {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;;
    RegisterFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RegisterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Тут инифиализируем и устанавливаем слушателми событий

        mAuth = FirebaseAuth.getInstance();
        binding.btRegister.setOnClickListener(this::registerNewUser);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void registerNewUser(View view)
    {
        registerNewUserInFireBase(view);
        Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);

    }

    private void registerNewUserInFireBase(View view)
    {
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (email.isEmpty())
        {
            // TODO: Сделать красный текст ошибки под полем
            Toast.makeText(getContext(), "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty())
        {
            // TODO: Сделать красный текст ошибки под полем
            Toast.makeText(getContext(), "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "User registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
