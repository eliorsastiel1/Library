package elior.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Books extends AppCompatActivity {
    private DatabaseReference db;
    private TextView text_books;
    private String text_books1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        // this code take the books from books jason, that located in firebase data base
        text_books=(TextView)findViewById(R.id.books_list);
        db= FirebaseDatabase.getInstance().getReference("books");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> td = (ArrayList<String>) dataSnapshot.getValue();
                for(int i=0; i<td.size(); i++){
                    String book = td.get(i);
                    setTheText(book);
                }
          }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setTheText(String books){
        text_books1=text_books1 + books+ ", ";
        text_books.setText(text_books1);
    }
}
