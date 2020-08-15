package io.gianmillare.fiftygram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;

public class MainActivity extends AppCompatActivity {
//    Reference the image view created in activity_main.xml
    private ImageView imageView;
    private Bitmap image;

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

//    Create the method that applies a Sepia Filter on the image
    public void applySepia(View view) {
//        The below code is found from the documentation of "glide transformations github"
        Glide.with(this).load(image).apply(RequestOptions.bitmapTransform(new SepiaFilterTransformation())).into(imageView);
    }

//    Create a method that returns a new activity after the above method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        this method should only execute with the required requestCode and the data is not null
        if (resultCode == Activity.RESULT_OK && data != null) {
            try {
//            pass a URI that contains the path to the data you are pulling (an image on the desk)
                Uri uri = data.getData();
//            pull the data from the path
                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                imageView.setImageBitmap(image);
            } catch (IOException e) {
                Log.e("cs50", "Image not found", e);
            }
        }
    }
}