package com.mylovemagicspinyouround.httpconnecttest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private ImageView img;

    public void sentDataPost(final String mData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uri = Uri.parse("http://140.123.97.110:1337/brushTeeth/uploads/imgPost.php")
                            .buildUpon()
                            .build().toString();

                    URL url = new URL(uri);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Connection", "Keep-Alive");
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                    writer.write("data=" + URLEncoder.encode(mData, "utf-8"));
                    writer.flush();
                    writer.close();

                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String tempStr;
                    StringBuffer stringBuffer = new StringBuffer();

                    while ((tempStr = bufferedReader.readLine()) != null) {
                        stringBuffer.append(tempStr);
                    }

                    bufferedReader.close();
                    inputStream.close();
                    urlConnection.disconnect();
                    Log.d("Hello", stringBuffer.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b=(Button)findViewById(R.id.button);
        Button b2=(Button)findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("拜託","開始");

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pika);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                String encodeImage = Base64.encodeToString(b ,Base64.DEFAULT);

                findViews();
                secondImage.setImageResource(R.drawable.pika);
                sentDataPost(encodeImage);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("拜託","開始");

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.eevee);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                String encodeImage = Base64.encodeToString(b ,Base64.DEFAULT);

                findViews();
                secondImage.setImageResource(R.drawable.eevee);
                sentDataPost(encodeImage);
            }
        });
    }
    private ImageView secondImage;

    private void findViews() {
        secondImage = (ImageView) findViewById(R.id.imageView2);
    }

}
