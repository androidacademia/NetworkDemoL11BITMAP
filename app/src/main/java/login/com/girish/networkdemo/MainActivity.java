package login.com.girish.networkdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String urlString = "https://pbs.twimg.com/profile_images/707557253637742592/8xPxraAg_400x400.jpg";
    private ProgressBar progressBar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);

    }

    public void download(View view) {
        if (ConnectivityStatus.isConnected(this)){
            //1. Create an object of AsynTask ie. ImageTask
            ImageTask imageTask = new ImageTask();
            //2. call execute method for AsynTask Lifecycle
            imageTask.execute(urlString,"1","2","4","5");
        }else {
            Toast.makeText(this, "Not connected....", Toast.LENGTH_SHORT).show();
        }
    }

    public class ImageTask extends AsyncTask<String,Void,Bitmap>{
        //3. this will call first
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        //4. background will be go here
        @Override
        protected Bitmap doInBackground(String... strings) {
            String str =  strings[0];
            Bitmap bitmap = null;

            //5. networking will go herel
            try {
                URL url = new URL(str);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                ////////////////////////////////////////////////////////////

                    //6. don't forget to convert into appropriate datatype by using Factory class
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressBar.setVisibility(View.GONE);
            //7.  if not null update UI
            if (bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}











