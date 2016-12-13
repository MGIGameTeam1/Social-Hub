package com.mgi.kelasku;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mgi.kelasku.utility.PojoKelas;
import com.mgi.kelasku.utility.PojoPost;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.data;

public class GroupActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    FloatingActionButton fab, fabPost, fabPhotos;
    boolean isFABOpen;

    FirebaseAuth firebaseAuth;
    DatabaseReference mRef,mDatabase;
    StorageReference riversRef;
    String Post,namaGroup;
    private int REQUEST_CODE_IMAGE = 1;
    private StorageReference storageReference;
    private Uri filepath;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        //imageView =(ImageView)findViewById(R.id.imgviewphotos);






        Intent myIntent= getIntent();
        namaGroup=myIntent.getStringExtra("NAMAGROUP");

        mRef=mDatabase.child("kelas").child(namaGroup);

        //Animations
        //final Animation show_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_show);
        //final Animation hide_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_hide);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabPost = (FloatingActionButton) findViewById(R.id.fab1);
        fabPhotos = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fabPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.layout_group_dialog, null);
                final TextInputLayout txtPost = (TextInputLayout) dialoglayout.findViewById(R.id.txtupdategroupdata);
                txtPost.setHint("Isi Post");

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
                alertDialog.setTitle(R.string.action_new_post);
                alertDialog.setView(dialoglayout).
                        setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                // TAMBAH KE DATABASE
                                // sign in the user ...
                                Post = txtPost.getEditText().getText().toString().trim();

                                String waktu = new java.text.SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(java.util.Calendar.getInstance().getTime()).toString();


                                PojoPost pojoPost=new PojoPost(waktu,Post);

                                //String getKeyKelas= mDatabase.push().getKey();
                                mRef=mDatabase.child("Post");
                                mRef.child(firebaseAuth.getCurrentUser().getUid()).child(namaGroup).setValue(pojoPost);
                                //finish();
                                Toast.makeText(getApplicationContext(),"Post berhasil dibuat ..",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"Post gagal dibuat ..",Toast.LENGTH_LONG).show();
                            }
                        });

                alertDialog.show();
            }
        });


        fabPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.layout_photo_dialog, null);

                ImageButton btnGallery=(ImageButton)findViewById(R.id.IBtngallry);
                Button btnUpload=(Button)findViewById(R.id.btnupload);

                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uplaodfile();
                    }
                });

                btnGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGallery();
                    }
                });
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
                alertDialog.setTitle("Post Photo");
                alertDialog.setView(dialoglayout).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_group);
        navigationView.setNavigationItemSelectedListener(this);

        //mRecyclerView = (RecyclerView) findViewById(R.id.homeRecyclerView);

        //mRecyclerView.setHasFixedSize(true);

        //layoutManager=new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(layoutManager);
        //mAdapter=new RecyclerViewAdapterGroup(getSetData());
        //mRecyclerView.setAdapter(mAdapter);

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
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_member_add) {
            LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.layout_home_dialog, null);
            TextInputLayout txtDialog = (TextInputLayout) dialoglayout.findViewById(R.id.txtlayoutupdategroup);
            txtDialog.setHint("Kode Pengguna");

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.action_join_group);
            alertDialog.setView(dialoglayout).
                    setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            alertDialog.show();

            return true;
        }else if (id==R.id.action_refresh){

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this,HomeActivity.class));
            finishAffinity();
        } else if (id == R.id.nav_notifications) {
            startActivity(new Intent(this,NotificationsActivity.class));
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_sign_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<DataObject> getSetData(){
        ArrayList result=new ArrayList<DataObject>();
        /*helperAdapter=new SelectAdapter(MainActivity.this);
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
        }

        String getName="MGI";
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
*/
        return result;
    }

    private void showFABMenu(){
        isFABOpen=true;
        fabPost.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        fabPhotos.animate().translationY(-getResources().getDimension(R.dimen.standard_130));

        fab.animate()
                .rotation(135.0F)
                .withLayer()
                .setDuration(200L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabPost.animate().translationY(0);
        fabPhotos.animate().translationY(0);
        fab.animate()
                .rotation(0.0F)
                .withLayer()
                .setDuration(200L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();
    }


    public void openGallery(){
        //Intent intent = new Intent(Intent.ACTION_PICK);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(intent,REQUEST_CODE_IMAGE);
        startActivityForResult(Intent.createChooser(intent,"Select Image .."), REQUEST_CODE_IMAGE);
    }

    public void uplaodfile(){
        riversRef = storageReference.child("Photos");
        riversRef.child(firebaseAuth.getCurrentUser().getUid()).child(namaGroup);

        riversRef.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(getApplicationContext(),"Upload Success",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Tunggu upload ..",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){

            filepath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
