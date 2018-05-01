package com.kelompok8.Bookuku;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kelompok8.Bookuku.model.Post;
import com.kelompok8.Bookuku.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kelompok8.Bookuku.R;


public class AddPost extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    EditText mJudul, mDeskripsi, mStatus, mGenre, mKontak;
    ImageView imageView;
    Button mChooseImage;

    //our database reference object
    DatabaseReference databaseFood;
    FirebaseAuth mAuth;

    private Uri imageUri;

    private StorageReference mStorage;
    Query databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        imageUri = null;
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference().child("Book_Images");
        databaseFood = FirebaseDatabase.getInstance().getReference(MainActivity.table1);
        databaseUser = FirebaseDatabase.getInstance().getReference(MainActivity.table3);
        mAuth = FirebaseAuth.getInstance();

        mKontak = (EditText)findViewById(R.id.et_kontak);
        mStatus = (EditText)findViewById(R.id.status);
        mGenre = (EditText)findViewById(R.id.genre);
        mJudul = (EditText) findViewById(R.id.et_title_post);
        mDeskripsi = (EditText) findViewById(R.id.et_post);
        imageView = findViewById(R.id.img_post);

        mChooseImage = findViewById(R.id.btn_choose_image);
        mChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    public void add(View view) {

        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.child(mAuth.getUid()).getValue(User.class);

                final String name = user.getUsername();
                final String kontak = mKontak.getText().toString();
                final String title = mJudul.getText().toString();
                final String status = mStatus.getText().toString();
                final String genre = mGenre.getText().toString();
                final String deskripsi = mDeskripsi.getText().toString();
                final String id = databaseFood.push().getKey();
                final String userId = mAuth.getUid();

                if (imageUri != null && !TextUtils.isEmpty(name)) {

                    final StorageReference image = mStorage.child(id + ".jpg");

                    image.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {

                            if (uploadTask.isSuccessful()) {

                                String download_url = uploadTask.getResult().getDownloadUrl().toString();
                                Post Books = new Post(id, userId, name, download_url, title, deskripsi, status, genre, kontak);
                                databaseFood.child(id).setValue(Books);

                            } else {
                                Toast.makeText(AddPost.this, "Error : " + uploadTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    //displaying a success toast
                    Toast.makeText(AddPost.this, "Uploaded", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AddPost.this, MainActivity.class);
                    startActivity(i);
                } else {
                    //if the value is not given displaying a toast
                    Toast.makeText(AddPost.this, "Please Fill the form and choose image", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}
