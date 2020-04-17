package com.example.teststore.ui.mystore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyStoreViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyStoreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my store fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}