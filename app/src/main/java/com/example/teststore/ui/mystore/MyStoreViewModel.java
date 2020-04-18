package com.example.teststore.ui.mystore;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyStoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyStoreViewModel() {
        mText = new MutableLiveData<>();
        Log.d("shop", "MyStoreViewModel: my store");
        mText.setValue("This is my store fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}