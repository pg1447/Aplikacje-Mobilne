package com.example.Galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //table with images
    ArrayList<Integer> mImageIds = new ArrayList<>(Arrays.asList(
            R.drawable.img2,R.drawable.img3,R.drawable.img4,
            R.drawable.img5,R.drawable.img6,R.drawable.img7,R.drawable.img8,
            R.drawable.img9,R.drawable.img10,R.drawable.img11
     ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.myGrid);
        gridView.setAdapter(new ImageAdaptor(mImageIds, this));

        gridView.setOnItemClickListener(( parent, view, position, id ) -> {
            int item_pos = mImageIds.get(position);

            ShowDialogBox(item_pos);
        });
    }

    public void ShowDialogBox(final int item_pos){
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.custom_dialog);

        //Getting custom dialog views
        TextView Image_name = dialog.findViewById(R.id.txt_Image_name);
        ImageView Image = dialog.findViewById(R.id.img);
        Button btn_Full = dialog.findViewById(R.id.btn_full);
        Button btn_Close = dialog.findViewById(R.id.btn_close);

        String title = getResources().getResourceName(item_pos);

        //extracting name
        int index = title.indexOf("/");
        String name = title.substring(index+1);
        Image_name.setText(name);

        Image.setImageResource(item_pos);

        btn_Close.setOnClickListener(v -> dialog.dismiss());

        btn_Full.setOnClickListener(v -> {
            Intent i = new Intent( MainActivity.this, FullView.class);
            i.putExtra("img_id",item_pos);
            startActivity(i);

        });

        dialog.show();

    }
    }