package com.example.zerowastehubs.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.CategoryDto;
import com.example.zerowastehubs.dto.ProductDto;
import com.example.zerowastehubs.dto.SubCategoryDto;
import com.example.zerowastehubs.repository.CategoryRepository;
import com.example.zerowastehubs.repository.ProductRepository;
import com.example.zerowastehubs.utitle.TokenManager;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment {

    AppCompatSpinner category,subCategory;
    AppCompatButton buUplP, btnCancel;
    CardView prodImaCar;
    ImageView prodImage, imgCamera, imgGallery;

    private Uri selectedImageUri;
    private Bitmap cameraBitmap;
    AppCompatEditText  price,title,details,description,location;
    List<CategoryDto> categoryDtoLis = new ArrayList<>();
    List<SubCategoryDto> subCategoryDtoLis = new ArrayList<>();


    public ShareFragment() {
        // Required empty public constructor
    }

    /* ---------------- CAMERA RESULT ---------------- */
    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    cameraBitmap = (Bitmap) result.getData().getExtras().get("data");
                    prodImage.setImageBitmap(cameraBitmap);
                }
            });

    /* ---------------- GALLERY RESULT ---------------- */
    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    prodImage.setImageURI(selectedImageUri);
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share, container, false);

        // Views
        prodImage = view.findViewById(R.id.prodImage);
        prodImaCar = view.findViewById(R.id.prodImaCar);
        category = view.findViewById(R.id.category);
        subCategory = view.findViewById(R.id.subCategory);
        buUplP = view.findViewById(R.id.buUplP);
        price = view.findViewById(R.id.price);
        title = view.findViewById(R.id.title);
        details = view.findViewById(R.id.details);
        description = view.findViewById(R.id.description);
        location = view.findViewById(R.id.location);



        /* ---------------- IMAGE PICK DIALOG ---------------- */
        prodImaCar.setOnClickListener(v -> showImagePickerDialog());

        // 🔑 Replace this with your JWT token from Login
        String token = TokenManager.getToken(requireContext());
        String bearerToken = "Bearer " + token;
        // categories
        CategoryRepository repo = new CategoryRepository(requireContext());
        repo.getCategories(bearerToken, new CategoryRepository.CategoryCallback() {
            @Override
            public void onSuccess(List<CategoryDto> categories) {
                categoryDtoLis.clear();
                categoryDtoLis.addAll(categories);
                List<String> catNames = new ArrayList<>();
                catNames.add("Select Product Category");

                for (CategoryDto c : categories) {
                    catNames.add(c.getCategoryName()); // 👈 assuming getName()
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        catNames
                );
                category.setAdapter(adapter);

                Log.e("Categories", new Gson().toJson(categories));

                Gson gson = new Gson();
                Log.e("All Categories ShareF", gson.toJson(categoryDtoLis));

            }
            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        // subCategories
        repo.getSubCategories(bearerToken, new CategoryRepository.SubCategoryCallback() {
            @Override
            public void onSuccess(List<SubCategoryDto> subCategories) {
                subCategoryDtoLis.clear();
                subCategoryDtoLis.addAll(subCategories);
                Gson gson = new Gson();
                Log.e("All SubCategories ShareF ", gson.toJson(subCategoryDtoLis));
            }

            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return; // ignore "Select Category"

                int selectedCategoryId = categoryDtoLis.get(position - 1).getId();

                loadSubCategories(selectedCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        /* ---------------- SPINNER SETUP ---------------- */
        /*// Category
        ArrayList<String> categoryItem = new ArrayList<>();
        categoryItem.add("Select Product Category");
        categoryItem.add("BOOK");
        categoryItem.add("CYCLE");
        categoryItem.add("PHONE");
        categoryItem.add("CLOTHES");
        categoryItem.add("SHOES");
        categoryItem.add("ELECTRIC PRODUCT");
        categoryItem.add("OTHER");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                categoryItem
        );
        category.setAdapter(adapter);

        //Sub Category
        ArrayList<String> subCategoryI= new ArrayList<>();
        subCategoryI.add("Select Sub Category");
        subCategoryI.add(" SCIENCE");
        subCategoryI.add("Food");
        subCategoryI.add("Cloths");
        subCategoryI.add("man wears");
        subCategoryI.add("sta");
        subCategoryI.add("Select Sub Category");
        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item,
        subCategoryI);
        subCategory.setAdapter(subAdapter);*/

        /* ---------------- UPLOAD BUTTON ---------------- */
        buUplP.setOnClickListener(v -> {

            // -------- VALIDATIONS --------

            if (category.getSelectedItemPosition() == 0) {
                Toast.makeText(requireContext(), "Select category", Toast.LENGTH_SHORT).show();
                return;
            }

            if (subCategory.getSelectedItemPosition() == 0) {
                Toast.makeText(requireContext(), "Select sub-category", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cameraBitmap == null && selectedImageUri == null) {
                Toast.makeText(requireContext(), "Select product image", Toast.LENGTH_SHORT).show();
                return;
            }

            String priceText = price.getText().toString().trim();
            String productTitle = title.getText().toString().trim();
            String productDetails = details.getText().toString().trim();
            String productDescription = description.getText().toString().trim();
            String productLocation = location.getText().toString().trim();
            int catId = category.getSelectedItemPosition();
            int subCatId = subCategory.getSelectedItemPosition();

            if (priceText.isEmpty() || productTitle.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // -------- IMAGE → FILE --------
            File imageFile = convertUriToFile();
            if (imageFile == null) {
                Toast.makeText(requireContext(), "Image conversion error", Toast.LENGTH_SHORT).show();
                return;
            }

            // -------- CALL API --------
            ProductRepository prepo = new ProductRepository(requireContext());
            prepo.createProduct(
                    bearerToken,
                    productTitle,
                    productDetails,
                    productDescription,
                    productLocation,
                    priceText,
                    String.valueOf(catId),
                    String.valueOf(subCatId),
                    imageFile,
                    new ProductRepository.CreateProductCallback() {
                        @Override
                        public void onSuccess(ProductDto productDto) {
                            Toast.makeText(requireContext(),
                                    "Product Uploaded!", Toast.LENGTH_SHORT).show();
                            Log.e("UPLOAD", "SUCCESS: " + new Gson().toJson(productDto));
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(requireContext(),
                                    "Upload Failed: " + message, Toast.LENGTH_SHORT).show();
                            Log.e("UPLOAD", "ERROR: " + message);
                        }
                    }
            );

        });


        return view;
    }

    /* ---------------- IMAGE PICKER DIALOG ---------------- */
    private void showImagePickerDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_image_upload);
        dialog.show();

        imgCamera = dialog.findViewById(R.id.imgCamera);
        imgGallery = dialog.findViewById(R.id.imgGallery);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        imgCamera.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(cameraIntent);
            dialog.dismiss();
        });

        imgGallery.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            galleryLauncher.launch(galleryIntent);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
    }
    private File convertUriToFile() {
        try {
            Bitmap bitmap;

            if (cameraBitmap != null) {
                // Image from camera
                bitmap = cameraBitmap;
            } else if (selectedImageUri != null) {
                // Image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().getContentResolver(),
                        selectedImageUri
                );
            } else {
                Log.e("FILE_ERROR", "No image selected");
                return null;
            }

            // Create file in cache
            File file = new File(requireContext().getCacheDir(), "upload_img.jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();

            return file;

        } catch (Exception e) {
            Log.e("FILE_ERROR", "convertUriToFile: " + e.getMessage());
            return null;
        }
    }

    private String convertImageToBase64() {
        try {
            Bitmap bitmap;

            if (cameraBitmap != null) {
                bitmap = cameraBitmap;
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().getContentResolver(), selectedImageUri);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.encodeToString(imageBytes, Base64.DEFAULT);

        } catch (Exception e) {
            Log.e("convertImageToBase64", "Error converting image: " + e.getMessage());
            return null;
        }
    }
    private void loadSubCategories(int categoryId) {
        List<String> subNames = new ArrayList<>();
        subNames.add("Select Sub Category");

        for (SubCategoryDto sc : subCategoryDtoLis) {
            if (sc.getCategoryId() == categoryId) {   // match by categoryId
                subNames.add(sc.getSubCategoryName()); // assuming getName()
            }
        }

        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                subNames
        );

        subCategory.setAdapter(subAdapter);
    }


}
