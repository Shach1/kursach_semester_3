package ru.mirea.androidcoursework.main;

import static android.content.Context.MODE_PRIVATE;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import ru.mirea.androidcoursework.databinding.SettingsFragmentBinding;


public class SettingsFragment extends Fragment
{
    public SettingsFragment()
    {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;
    SettingsFragmentBinding binding;

    private void init()
    {
        mAuth = FirebaseAuth.getInstance();
        binding.btLogout.setOnClickListener(this::onLogout);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SettingsFragmentBinding.inflate(inflater, container, false);
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
        Log.d("SettingFragment", "onViewCreated: ");

        init();
    }

    private void onLogout(View view)
    {
        mAuth.signOut();
        Log.d("SettingFragment", "onLogout: ");
        getActivity().findViewById(ru.mirea.androidcoursework.R.id.bottom_navigation).setVisibility(View.GONE);
        getActivity().getSharedPreferences("login", MODE_PRIVATE).edit().putBoolean("isRememberMe", false).apply();
        Navigation.findNavController(view).navigate(ru.mirea.androidcoursework.R.id.action_settingsFragment_to_loginFragment);
    }

}
