package elior.library;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignUp;
    private Button submit;
    private ProgressDialog progressDialog;
    private EditText inputbox_Email;
    private EditText inputbox_Password;
    private FirebaseAuth firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSignUp=(Button)findViewById(R.id.sign_up_btn);
        buttonSignUp.setOnClickListener(this);
        inputbox_Email=(EditText)findViewById(R.id.InputBox_email);
        inputbox_Password=(EditText)findViewById(R.id.inputBox_Password);
        submit=(Button)findViewById(R.id.submit_btn);
        submit.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        firebase = FirebaseAuth.getInstance();
    }

    public void onClick(View v) {
        if(v==buttonSignUp){
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
       else if(v==submit){
            loginIn();
        }
    }
    private void loginIn() {
        String password1=inputbox_Password.getText().toString().trim();
        String email1=inputbox_Email.getText().toString().trim();

        if(TextUtils.isEmpty(email1)){
            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password1)){
            Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        firebase.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Sign In Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
