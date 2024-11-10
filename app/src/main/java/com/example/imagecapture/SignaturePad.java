package com.example.imagecapture;


import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class SignaturePad extends AppCompatActivity {

    private SignaturePadView signaturePad;
    private Button btnClear, btnSave;

    private static final int REQUEST_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_pad);
        signaturePad=findViewById(R.id.signaturePad);
        btnClear = findViewById(R.id.btnClear);
        btnSave = findViewById(R.id.btnSave);

//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
//        }


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = signaturePad.getSignatureBitmap();
                System.out.println("bitmap"+bitmap);
                saveSignature(bitmap);
            }
        });
    }

    private void saveSignature(Bitmap bitmap) {
        File directory = new File(Environment.getExternalStorageDirectory() + "/Signatures");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, "signature_" + System.currentTimeMillis() + ".png");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "Signature saved to " + file.getPath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving signature", Toast.LENGTH_SHORT).show();
        }
    }
}