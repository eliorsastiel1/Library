package elior.library;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private Button submit;
    private Button camera;
    private ImageButton addimg;
    public Uri ImageURI;
    private static final int PICK_IMG = 100;
    private User user;
    private EditText address;
    private EditText firstname;
    private EditText lastname;
    private EditText password;
    private EditText email;
    private ProgressDialog progressDialog;
    // TODO: FirebaseStorage vs StorageReference
    private DatabaseReference mDatabaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        buttonSignIn = (Button) findViewById(R.id.SignIn_btn);
        buttonSignIn.setOnClickListener(this);
        submit = (Button) findViewById(R.id.submit_btn);
        submit.setOnClickListener(this);
        camera = (Button) findViewById(R.id.Camera_btn);
        camera.setOnClickListener(this);
        addimg = (ImageButton) findViewById(R.id.addImg);
        addimg.setOnClickListener(this);
        address = (EditText) findViewById(R.id.Address_EditText);
        firstname = (EditText) findViewById(R.id.FirstName_EditText);
        lastname = (EditText) findViewById(R.id.LastName_EditText);
        password = (EditText) findViewById(R.id.Password_EditText);
        email = (EditText) findViewById(R.id.EditText_email);
        progressDialog = new ProgressDialog(this);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();
    }

    public void onClick(View v) {
        if (v == buttonSignIn) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else if (v == submit) {
            registerUser();
        } else if (v == addimg) {
            openGallery();

        } else if (v == camera) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
        }
    }

    private void registerUser() {
        mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        final String address1 = this.address.getText().toString().trim();
        final String firstname1 = this.firstname.getText().toString().trim();
        final String lastname1 = this.lastname.getText().toString().trim();
        final String password1 = this.password.getText().toString().trim();
        final String email1 = this.email.getText().toString().trim();
        if (TextUtils.isEmpty(email1)) {
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password1)) {
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Register User..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email1,password1)
        .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();
                if(task.isSuccessful()){
                    String id=firebaseAuth.getCurrentUser().getUid();
                    uploadImg(id);
                    Map<String,String> user=new HashMap<String, String>();
                    user.put("email",email1);
                    user.put("password", password1);
                    user.put("first name",firstname1);
                    user.put("last name",lastname1);
                    user.put("address",address1);
                    mDatabaseReference.child("users").child(id).setValue(user);
                    boolean cheackIfAdmin=isAdmin(email1);
                    if(cheackIfAdmin){
                        mDatabaseReference.child("admin").child(id).setValue(user);
                    }

                    startActivity(new Intent(SignUpActivity.this, MenuActivity.class));
            }
            else{
                    Toast.makeText(SignUpActivity.this, "Register Faild", Toast.LENGTH_SHORT).show();
                }
            }


        });
        }

    private void uploadImg(String id) {
        if (ImageURI != null) {
            StorageReference ref = storageReference.child("Images/" + id );
            ref.putFile(ImageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "uploaded successfuled", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            });
            // we put sleep because we want to give time to upload the image to the data base
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAdmin(String email) {
        return email.toLowerCase().contains("admin");
    }

    private void showToast() {
        Context context = getApplicationContext();
        CharSequence text = "The image added succesfull!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public void openGallery(){
        Intent galerry= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galerry,PICK_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMG){
            ImageURI = data.getData();
            addimg.setImageURI(ImageURI);
        }
        showToast();
    }
}