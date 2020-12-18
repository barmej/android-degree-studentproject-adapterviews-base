package com.barmej.notesapp;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class NewsListGenerator {

    public static List<Notes> generateList(int size){

        ArrayList<Notes>list  = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Uri image = null;
            String noteTextShow = "عنوان الخبر رقم :  "+ i;
//            list.add(new Notes(noteTextShow , null));
        }
        return list;
    }
}
