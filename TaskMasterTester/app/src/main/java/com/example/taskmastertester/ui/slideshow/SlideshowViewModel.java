package com.example.taskmastertester.ui.slideshow;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskmastertester.CustomAdapter;
import com.example.taskmastertester.Menu;
import com.example.taskmastertester.R;
import com.example.taskmastertester.model.Evento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlideshowViewModel extends ViewModel {

    private ListView listEvents;

    public static CustomAdapter customAdapter;

    ArrayList<Evento> eventoArrayList = new ArrayList<>();
    private final MutableLiveData<String> mText;


    private final MutableLiveData<List<Evento>> lista;

    private final MutableLiveData<List<Evento>> itemList;

    public LiveData<List<Evento>> getItemList() {
        return itemList;
    }

    public void setItemList(List<Evento> items) {
        itemList.postValue(items);
    }


    public SlideshowViewModel() {
        lista = new MutableLiveData<>();
        itemList = new MutableLiveData<>();
        lista.setValue(Menu.lista);
        mText = new MutableLiveData<>();
        mText.setValue("Tareas");
    }

    public MutableLiveData<List<Evento>> getLista() {
        //customAdapter = new CustomAdapter(this,Menu.lista);
        return lista;
    }

    public LiveData<String> getText() {
        return mText;
    }




}