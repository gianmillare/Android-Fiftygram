package io.gianmillare.fiftygram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
//    Reference the image view created in activity_main.xml
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        assign the referenced imageView above (private), using the id created in activity_main.xml
        imageView = findViewById(R.id.image_view);
    }

//    Create a method that will allow a user to upload a picture into the app
    public void choosePhoto(View view) {
//        create an intent to open a document
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        specify what type of document should be opened/uploaded
        intent.setType("image/*");
//        start the activity
        startActivityForResult(intent, 1);
    }
}