package com.example.basicfiledownloader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button downloadFileBtn;
    EditText fileLinkET,fileNameET, fileExtensionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileLinkET = findViewById(R.id.fileLinkET);
        downloadFileBtn = findViewById(R.id.downloadFileBtn);
        fileExtensionET = findViewById(R.id.fileExtensionET);
        fileNameET = findViewById(R.id.fileNameET);

        downloadFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fileLink = fileLinkET.getText().toString();
                String filename = fileNameET.getText().toString();
                String fileextension = fileExtensionET.getText().toString();

                if (filename.isEmpty() || fileextension.isEmpty() || fileLink.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please make sure to no fields are left empty.", Toast.LENGTH_SHORT).show();
                }else{
                    new DownloadFile(MainActivity.this, filename+"."+fileextension, fileLink).execute();
                }
            }
        });
    }
}