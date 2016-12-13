package com.mgi.kelasku;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mgi.kelasku.utility.MainViewHolder;
import com.mgi.kelasku.utility.PojoKelas;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //private RecyclerView.Adapter mAdapter;

    String NamaGroup,Deskripsi;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase,mRef;
    FirebaseRecyclerAdapter<PojoKelas, MainViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onConfigurationChanged(new Configuration());
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_home);
        navigationView.setNavigationItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getUserGroup();

        mRecyclerView = (RecyclerView) findViewById(R.id.homeRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        //mAdapter=new RecyclerViewAdapterHome();
       // mRecyclerView.setAdapter(mAdapter);
        new getKelas().execute();
        //getSetData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_group) {
            LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.layout_home_dialog, null);
            final TextInputLayout txtNamaGroup=(TextInputLayout)dialoglayout.findViewById(R.id.txtlayoutupdategroup);
            final TextInputLayout txtDeskripsi=(TextInputLayout)dialoglayout.findViewById(R.id.txtlayoutupdatedeskripsi);
            //txtJudul.setHint("Nama Group");

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.action_add_group);
            alertDialog.setView(dialoglayout).
                    setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...

                            NamaGroup = txtNamaGroup.getEditText().getText().toString().trim();
                            Deskripsi = txtDeskripsi.getEditText().getText().toString().trim();

                            PojoKelas pojoKelas=new PojoKelas(NamaGroup,Deskripsi,firebaseAuth.getCurrentUser().getEmail());

                            String getKeyKelas= mDatabase.push().getKey();
                            mRef=mDatabase.child("kelas");
                            mRef.child(firebaseAuth.getCurrentUser().getUid()).child(getKeyKelas).setValue(pojoKelas);
                            //finish();
                            Toast.makeText(getApplicationContext(),"Group Berhasil di daftarkan ..",Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(),"Group Gagal di daftarkan ..",Toast.LENGTH_LONG).show();
                        }
                    });

            alertDialog.show();

            return true;
        }else if(id==R.id.action_refresh){
            ProgressBar refreshBar=new ProgressBar(this);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_notifications) {
            startActivity(new Intent(this,NotificationsActivity.class));
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_sign_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getUserGroup(){
        //firebaseAuth.getClass().getFields();
    }

  /* public ArrayList<DataObject> getSetData(){
        //ArrayList result=new ArrayList<DataObject>();


        helperAdapter=new SelectAdapter(MainActivity.this);
        Cursor c=helperAdapter.ambilDataUser(db);
        if(c.getCount()!=0){
            if(c.moveToFirst()){
                do{
                    String ambiljudul=c.getString(1);
                    String ambildeskripsi=c.getString(2);
                    String ambillikes=c.getString(3);
                    String ambilgambar=c.getString(4);
                    int convertGambar = getResources().getIdentifier(ambilgambar,"drawable",getPackageName());
                    DataObject obj=new DataObject(ambiljudul,ambildeskripsi,ambillikes,convertGambar);
                    result.add(obj);
                }while(c.moveToNext());
            }
        }*/

       /* String getName="MGI";
        String getDescription="MDP Games Incubator";
        String getAdmin="Kgs. Achmad Siddik MDP";
        String getDisplayPicture="pak siddik";

        int convertGambar = getResources().getIdentifier(getDisplayPicture,"drawable",getPackageName());
        DataObject obj=new DataObject(getName,getDescription,getAdmin,convertGambar);
        result.add(obj);

        getName="Soshub";
        getDescription="Lomba Mesosfer";
        getAdmin="Cendy Prakarsah";
        getDisplayPicture="kak cendy";

        convertGambar = getResources().getIdentifier(getDisplayPicture,"drawable",getPackageName());
        obj=new DataObject(getName,getDescription,getAdmin,convertGambar);
        result.add(obj);

        return result;
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setPadding(90,0,90,0);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setPadding(0,0,0,0);
        }
    }

    class getKelas extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setTitle("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(final String... strings) {
            mRef=mDatabase.child("kelas").child(firebaseAuth.getCurrentUser().getUid());
            mAdapter=new FirebaseRecyclerAdapter<PojoKelas, MainViewHolder>(PojoKelas.class,R.layout.card_view_row_home,MainViewHolder.class,mRef) {
                @Override
                protected void populateViewHolder(MainViewHolder viewHolder, final PojoKelas model, int position) {
                    viewHolder.binToPost(model,HomeActivity.this);
                    Toast.makeText(HomeActivity.this,model.getAdmin(),Toast.LENGTH_LONG).show();

                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println("Nama:"+model.getAdmin());
                            Log.d("Nama Admin:",model.getAdmin());
                            Toast.makeText(HomeActivity.this,model.getAdmin(),Toast.LENGTH_LONG).show();
                            Intent myIntent=new Intent(getApplicationContext(),GroupActivity.class);
                            myIntent.putExtra("NAMAGROUP",model.getNamaGroup());
                            startActivity(myIntent);
                        }
                    });
                }
            };
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            mRecyclerView.setAdapter(mAdapter);
        }
    }


}
