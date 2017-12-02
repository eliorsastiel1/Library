package elior.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button map;
    private Button readme;
    private TextView firstname;
    private TextView lastname;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        map=(Button)findViewById(R.id.map_btn);
        map.setOnClickListener(this);
        readme=(Button)findViewById(R.id.README);
        readme.setOnClickListener(this);
        String firstname=getIntent().getStringExtra("firstname");
        String lastname=getIntent().getStringExtra("lastname");
        String email=getIntent().getStringExtra("email");
        this.firstname= (TextView)findViewById(R.id.firstname_textView);
        this.firstname.setText(firstname);
        this.lastname= (TextView)findViewById(R.id.lastname_TextView);
        this.lastname.setText(lastname);
        this.email= (TextView)findViewById(R.id.email_TextView);
        this.email.setText(email);

    }


    public void onClick(View v) {
        if(v==map){
            finish();
            startActivity(new Intent(this, MapsActivity.class));
        }
        else if(v==readme){
            startActivity(new Intent(this, README.class));
        }
    }
}
