package elior.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ProfileImg;
    private String email1;
    private String lastname1;
    private String firstname1;
    private Button map;
    private Button books;
    private Button readme;
    private TextView firstname;
    private TextView lastname;
    private TextView email;
    private DatabaseReference db;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        map=(Button)findViewById(R.id.map_btn);
        map.setOnClickListener(this);
        readme=(Button)findViewById(R.id.README);
        readme.setOnClickListener(this);
        books=(Button)findViewById(R.id.Books_Btn);
        books.setOnClickListener(this);

        // the code that take the user data from firebase database
        firebaseAuth = FirebaseAuth.getInstance();
        String id=firebaseAuth.getCurrentUser().getUid();
        db=FirebaseDatabase.getInstance().getReference("users");
        db.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                    Map<String, String> td = (HashMap<String,String>) dataSnapshot.getValue();
                    firstname1=td.get("first name");
                    lastname1=td.get("last name");
                    email1=td.get("email");
                    setTheText(firstname1,lastname1,email1);
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        // The code that take the image from fire base and put it into imageView
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef=storage.getReferenceFromUrl("gs://library-187413.appspot.com/Images/"+id);
        ProfileImg = (ImageView)findViewById(R.id.ProfilePicture);
        Glide.with(this).using(new FirebaseImageLoader()).load(storageRef).into(ProfileImg);

        this.firstname= (TextView)findViewById(R.id.firstname_textView);
        this.lastname= (TextView)findViewById(R.id.lastname_TextView);
        this.email= (TextView)findViewById(R.id.email_TextView);
    }

    private void setTheText(String firstname1, String lastname, String email1) {
        this.firstname.setText("user name: " + firstname1);
        this.lastname.setText("user last name: " + lastname);
        this.email.setText("user email: " +email1);
    }


    public void onClick(View v) {
        if(v==map){
            startActivity(new Intent(this, MapsActivity.class));
        }
        else if(v==readme){
            startActivity(new Intent(this, README.class));
        }
        else if(v==books){
            startActivity(new Intent(this, Books.class ));
        }
    }
}
