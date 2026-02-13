package com.example.zerowastehubs.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zerowastehubs.utitle.TokenManager.clearToken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.zerowastehubs.Activity.SingInActivity;
import com.example.zerowastehubs.Activity.UserOwnProductActivity;
import com.example.zerowastehubs.Activity.WatchListActivity;
import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.UserDto;
import com.example.zerowastehubs.repository.UserRepository;
import com.example.zerowastehubs.utitle.TokenManager;


public class AccountFragment extends Fragment {
    TextView userName,userPhone;
    AppCompatButton editProfile;
    RelativeLayout mode,notification,account,wishlist,language,aboutUs,faqs,logout,listitem,sandMessage;
    public AccountFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
         userName = view.findViewById(R.id.userName);
         userPhone = view.findViewById(R.id.userPhone);
         editProfile   =  view.findViewById(R.id.editProfile);
         mode = view.findViewById(R.id.mode);
         notification = view.findViewById(R.id.notification);
         account = view.findViewById(R.id.account);
         language = view.findViewById(R.id.language);
         aboutUs = view.findViewById(R.id.aboutUs);
         faqs = view.findViewById(R.id.faqs);
         logout = view.findViewById(R.id.logout);
         wishlist = view.findViewById(R.id.wishlist);
        listitem = view.findViewById(R.id.list_item);
        sandMessage = view.findViewById(R.id.sandMessage);
        String token = TokenManager.getToken(requireContext());
        String bearerToken = "Bearer " + token;
        // user details
        UserRepository userRepository = new UserRepository(requireContext());
               userRepository.getUserOwn(bearerToken, new UserRepository.UserCallback() {
                   @Override
                   public void onSuccess(UserDto userDto) {
                       if (userDto == null) {
                           Log.e("DEBUG", "userDto == NULL");
                           return;
                       }
                       userName.setText(String.valueOf(userDto.getName()));
                       userPhone.setText(String.valueOf(userDto.getPhoneNumber()));

                   }

                   @Override
                   public void onError(String message) {
                       Toast.makeText(requireContext(),"User Not Load"+ message, Toast.LENGTH_SHORT).show();
                       Log.e("user",message);
                   }
               });


        //LogOut
        logout.setOnClickListener(v -> {
            if(getActivity() != null){
                // clear token
              clearToken(getActivity());
                // 2️ Clear login flag
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("flag", true);
                editor.apply();
                // 3️Go to Login screen
                Intent intent = new Intent(getActivity(), SingInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(getActivity(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
           startActivity(new Intent(requireContext(), SingInActivity.class));
        });

         //List Item
        listitem.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Developing mode", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(requireContext(), UserOwnProductActivity.class));
        });
        //Sand us Message
        sandMessage.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Developing mode", Toast.LENGTH_SHORT).show();
        });
         //wishlist
        wishlist.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), WatchListActivity.class));
        });
        return  view;
    }
}
