package elior.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignUp;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSignUp=(Button)findViewById(R.id.sign_up_btn);
        buttonSignUp.setOnClickListener(this);
        submit=(Button)findViewById(R.id.submit_btn);
        submit.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v==buttonSignUp){
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
       else if(v==submit){
            finish();
            startActivity(new Intent(this, MenuActivity.class));
        }
    }
}
