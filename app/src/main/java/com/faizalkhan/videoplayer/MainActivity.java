package com.faizalkhan.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridLayout;
import android.widget.Toast;

import com.khizar1556.mkvideoplayer.MKPlayer;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter adapter;
    File directory;
    public static int REQUEST_PERMISSION=1;
    boolean permission;
    public static ArrayList<File> fileArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list_video_items);

        directory = new File("/mnt/");

        GridLayoutManager gridLayout = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        permissionforVideo();
       // adapter.setHasStableIds(true);



    }

    private void permissionforVideo() {

        if(ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
                }
            }
        else{
            permission = true;
            getFile(directory);
            adapter = new MyAdapter(getApplicationContext(),fileArrayList);
            //recyclerView.setAdapter(adapter);
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(adapter);
                }
            },100);
        }

        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                permission = true;
                getFile(directory);
                adapter = new MyAdapter(getApplicationContext(),fileArrayList);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                },100);
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private ArrayList<File> getFile(File directory) {

        File[] listfile = directory.listFiles();
        if(listfile!=null && listfile.length>0){
            for(int i=0; i<listfile.length; i++){
                if(listfile[i].isDirectory()){
                    getFile(listfile[i]);
                }
                else{

                    permission = false;
                    if(listfile[i].getName().endsWith(".mp4")){
                        for(int j=0; j<fileArrayList.size();j++){
                            if(fileArrayList.get(j).getName().equals(listfile[i].getName())){
                                permission = true;
                            }else{

                            }
                        }
                        if(permission){
                            permission =true;
                        }else{
                            fileArrayList.add(listfile[i]);
                        }
                    }


                }


            }
        }
        return fileArrayList;
    }


}
