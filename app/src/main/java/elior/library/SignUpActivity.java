package elior.library;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;
import java.net.URI;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private Button submit;
    private ImageButton addimg;
    public  Uri ImageURI;
    private static final int PICK_IMG=100;
    private User user;
    EditText address;
    EditText firstname;
    EditText lastname;
    EditText password;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        buttonSignIn=(Button)findViewById(R.id.SignIn_btn);
        buttonSignIn.setOnClickListener(this);
        submit=(Button)findViewById(R.id.submit_btn);
        submit.setOnClickListener(this);
        addimg=(ImageButton)findViewById(R.id.addImg);
        addimg.setOnClickListener(this);
        address=(EditText)findViewById(R.id.Address_EditText);
        firstname=(EditText)findViewById(R.id.FirstName_EditText);
        lastname=(EditText)findViewById(R.id.LastName_EditText);
        password=(EditText)findViewById(R.id.Password_EditText);
        email=(EditText)findViewById(R.id.EditText_email);
    }

    public void onClick(View v) {
        if(v==buttonSignIn){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        else if(v==submit){
            String address=this.address.toString();
            String firstname=this.firstname.getText().toString();
            String lastname=this.lastname.getText().toString();
            String password=this.password.getText().toString();
            String email=this.email.getText().toString();
            user=new User(firstname,lastname, email,address,password);
            Intent i = new Intent(this, MenuActivity.class);
            i.putExtra("firstname",firstname);
            i.putExtra("lastname",lastname);
            i.putExtra("email",email);
            finish();
            startActivity(i);
        }
        else if( v==addimg){
            openGallery();
        }
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
    }
}
