package ru.mirea.androidcoursework.main;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ru.mirea.androidcoursework.R;
import ru.mirea.androidcoursework.databinding.AddNewCardProductBinding;
import ru.mirea.androidcoursework.entity.CardProduct;

public class AddNewCardProductFragment extends Fragment
{
    public AddNewCardProductFragment()
    {
        // Required empty public constructor
    }

    private AddNewCardProductBinding binding;
    private final String CARD_PRODUCT_KEY = "CardProduct";
    private final String IMAGES_KEY = "imagesForProducts";
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    StorageReference mStorageRefChild;
    private ActivityResultLauncher<String> mGetContent;
    private Uri imageUri;

    private double price;
    private String title;
    private String description;
    private String category;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddNewCardProductBinding.inflate(inflater, container, false);
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

        mDatabase = FirebaseDatabase.getInstance().getReference(CARD_PRODUCT_KEY);


        binding.btAddNewProduct.setOnClickListener(this::writeCardProduct);
        binding.btSelectImage.setOnClickListener(this::getImageFromGallery);

        mStorageRef = FirebaseStorage.getInstance().getReference(IMAGES_KEY);
        mStorageRefChild = mStorageRef.child(System.currentTimeMillis() + "my_image");

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if(uri != null)
                    {
                        binding.imageView.setImageURI(uri);
                        updateImageUri(uri);
                    }
                    else Toast.makeText(view.getContext(), "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show();
                });
    }

    private void getImageFromGallery(View view) {
        mGetContent.launch("image/*");
    }

    private void writeCardProduct(View view)
    {

        title = binding.etTitle.getText().toString();
        description = binding.etDescription.getText().toString();
        category = binding.etCategory.getText().toString();
        try {
            price = Double.parseDouble(binding.etPrice.getText().toString());
        }
        catch (NumberFormatException e)
        {
            Toast.makeText(view.getContext(), "Введите корректную цену", Toast.LENGTH_SHORT).show();
            return;
        }

        if (title.isEmpty() || description.isEmpty() || category.isEmpty() ||price == 0.0)
        {
            Toast.makeText(view.getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Bitmap bitmap = uriToBitmap(getContext(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask up = mStorageRefChild.putBytes(data);
            up.addOnSuccessListener(taskSnapshot -> {
                mStorageRefChild.getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.d("AddNewCardProductFragment", "onViewCreated: " + uri.toString());
                    // Создаем уникальный ключ для нового продукта
                    DatabaseReference newProductRef = mDatabase.push();
                    // Используем этот уникальный ключ как идентификатор для нашего продукта
                    CardProduct cardProduct = new CardProduct(newProductRef.getKey(), uri.toString(), title, description, category, price);
                    // Записываем продукт в базу данных
                    newProductRef.setValue(cardProduct);
                    onHomeFragment(view);
                }).addOnFailureListener(e -> {
                    // Обработка ошибок при получении URL изображения
                    Log.d("AddNewCardProductFragment", "Failed to get download URL: " + e.getMessage());
                });
            }).addOnFailureListener(e -> {
                // Обработка ошибок при загрузке изображения
                Log.d("AddNewCardProductFragment", "Upload failed: " + e.getMessage());
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




    }

    private void onHomeFragment(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_addNewCardProductFragment_to_homeFragment);
    }

    private Bitmap uriToBitmap(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(inputStream);
    }

    public void updateImageUri(Uri uri)
    {
        imageUri = uri;
    }

}
