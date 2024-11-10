package com.example.imagecapture;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadPdfActivity extends AppCompatActivity {

    private WebView webView;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_pdf);
        webView = findViewById(R.id.webView);
        downloadButton = findViewById(R.id.downloadButton);

        // Load the webpage in the WebView
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://example.com");

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPageAsPdf();
            }
        });
    }

    private void downloadPageAsPdf() {
        // Create a PdfDocument object
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(webView.getWidth(), webView.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Draw the content of WebView onto the PDF page
        webView.draw(page.getCanvas());
        pdfDocument.finishPage(page);

        // Create a file in the external storage directory
        File pdfFile = new File(Environment.getExternalStorageDirectory(), "downloaded_page.pdf");

        try {
            pdfFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            pdfDocument.writeTo(fileOutputStream);
            pdfDocument.close();
            fileOutputStream.close();
            Toast.makeText(this, "PDF downloaded successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}