package com.example.nuevoproyecto52;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button camara;
    ImageView ima;
    TextView textodentrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camara=findViewById(R.id.bntAbrircamara);
        ima=findViewById(R.id.imageView);
        textodentrada=findViewById(R.id.textView);

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrircamara();
            }
        });
    }
    public void abrircamara() {
        Intent abrir = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(abrir, 5);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                Bundle nombre = data.getExtras();
                ima.setImageBitmap((Bitmap) nombre.get("data"));
                mueve((Bitmap) nombre.get("data"));

            }
        } catch (Exception e) {
        }
    }

    public void mueve(Bitmap imagenes) {
        ImageLabeler etiqueta = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
        etiqueta.process(InputImage.fromBitmap(imagenes, 0)).addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
            @Override
            public void onSuccess(@NonNull @org.jetbrains.annotations.NotNull List<ImageLabel> imageLabels) {
                for (ImageLabel etiqueta : imageLabels) {
                    textodentrada.append( etiqueta.getText()+"\n");
                }
            }
        });


    }
}