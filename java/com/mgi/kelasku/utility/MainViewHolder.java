package com.mgi.kelasku.utility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgi.kelasku.R;


/**
 * Created by Achmad Siddik on 13/12/2016.
 */

public class MainViewHolder extends RecyclerView.ViewHolder{
    //ImageView imgThumb;
    TextView tvNamaGroup;
    TextView tvDeskripsi;
    TextView tvAdmin;

    public MainViewHolder(View itemView) {
        super(itemView);
        //imgThumb=(ImageView)itemView.findViewById(R.id.imgThumb);
        tvNamaGroup=(TextView)itemView.findViewById(R.id.txtGroupName);
        tvDeskripsi=(TextView)itemView.findViewById(R.id.txtGroupDescription);
        tvAdmin=(TextView)itemView.findViewById(R.id.txtAdmin);
    }


    public void binToPost(PojoKelas pojoKelas, Context context){
        tvNamaGroup.setText(pojoKelas.namaGroup);
        tvDeskripsi.setText(pojoKelas.deskripsi);
        tvAdmin.setText(pojoKelas.admin);
    }

}
