package com.mgi.kelasku;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapterHome extends RecyclerView.Adapter<RecyclerViewAdapterHome.DataObjectHolder>{
    private static String LOG_TAG="MyRecyclerViewAdapter";
    ArrayList<DataObject> dataSet;


    public RecyclerViewAdapterHome(ArrayList<DataObject> dataSet){
        this.dataSet=dataSet;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row_home,parent, false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view,parent.getContext());
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.imgGambar.setBackgroundResource(dataSet.get(position).getGroupDisplayPicture());
        holder.txtGroupName.setText(dataSet.get(position).getGroupName());
        holder.txtGroupDescription.setText(dataSet.get(position).getGroupDescription());
        holder.txtOther.setText(dataSet.get(position).getOther());
    }


    public void AddItem(DataObject obj, int index){
        dataSet.add(index, obj);
        notifyItemInserted(index);
    }

    public void DeleteItem(int index){
        dataSet.remove(index);
        notifyItemInserted(index);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txtGroupName,txtGroupDescription,txtOther;
        ImageView imgGambar;
        public DataObjectHolder(View itemView, final Context context) {
            super(itemView);
            imgGambar=(ImageView)itemView.findViewById(R.id.imgGroupDP);
            txtGroupName=(TextView)itemView.findViewById(R.id.txtGroupName);
            txtGroupDescription=(TextView)itemView.findViewById(R.id.txtGroupDescription);
            txtOther=(TextView)itemView.findViewById(R.id.txtAdmin);

            itemView.setClickable(true);

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (view.isPressed() && motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        long eventDuration = motionEvent.getEventTime() - motionEvent.getDownTime();
                        if (eventDuration > 1000) {
                            AlertDialog.Builder ad = new AlertDialog.Builder(context);
                            ad.setCancelable(false);
                            ad.setMessage("Leave Group")
                                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // FIRE ZE MISSILES!
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                        }
                                    });
                            ad.show();
                        }
                    }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        Intent myIntent=new Intent(context,GroupActivity.class);
                        context.startActivity(myIntent);
                        Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });
        }
    }
}
