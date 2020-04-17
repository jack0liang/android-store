package com.example.teststore.ui.mystore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.teststore.R;

public class MyStoreFragment extends Fragment {

    private MyStoreViewModel myStoreViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myStoreViewModel =
                ViewModelProviders.of(this).get(MyStoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mystore, container, false);
        final TextView textView = root.findViewById(R.id.text_mystore);
        myStoreViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
