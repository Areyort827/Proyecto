package com.example.taskmastertester;

import com.example.taskmastertester.model.Evento;

import java.util.List;

public interface OnItemListUpdatedListener {

    void updateListViewData(List<Evento> newData);
}
