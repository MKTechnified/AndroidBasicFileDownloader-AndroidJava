package com.example.basicfiledownloader;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFile extends AsyncTask<String, Integer, String> {
    private ProgressDialog progressDialog;
    private Activity activity;
    private String fileName;
    private String fileUrl;
    private boolean isDownloaded;

    public DownloadFile(Activity activity, String fileName, String fileUrl) {
        this.activity = activity;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.isDownloaded = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Downloading...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            connection.connect();
            // getting file length
            int lengthOfFile = connection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            String path = Environment.getExternalStorageDirectory() + "/Download/";
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            OutputStream output = new FileOutputStream(path + fileName);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                publishProgress((int) (total * 100 / lengthOfFile));
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

            isDownloaded = true;
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        progressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if (isDownloaded) {
            Toast.makeText(activity, "File downloaded successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "Failed to download file", Toast.LENGTH_SHORT).show();
        }
    }
}
