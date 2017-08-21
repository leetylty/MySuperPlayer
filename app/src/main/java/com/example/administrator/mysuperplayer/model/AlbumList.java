package com.example.administrator.mysuperplayer.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/21.
 */

public class AlbumList extends ArrayList<Album> {
     private static final  String TAG = AlbumList.class.getCanonicalName();

     public void DebugModel(){
         for (Album  album :this){
             Log.i(TAG,"-->>>album"+album.toString());
         }
     }

}
