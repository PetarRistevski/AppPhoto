package photoapp.mca.mk.appphoto;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendPhoto extends AppCompatActivity {
    public static final String URL_TO_SEND = "http://jsonplaceholder.typicode.com/photos";
    public static final String POST = "POST";
    Button send;
    Bitmap myBitmap;
    String myBase64Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_photo);
        send = findViewById(R.id.buttonSend);
    }
    public void sendPhotoToServer(View view) {

        sendJson();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    protected void sendJson() {
        Thread t = new Thread() {

            public void run() {
                Looper.prepare();
                StringBuffer responseString = null;
                HttpURLConnection connection = null;
                String inputLine;
                String photo = getStringPicture();
                URL postUrl = null;
                try {
                    postUrl = new URL(URL_TO_SEND);
                    connection = (HttpURLConnection) postUrl.openConnection();
                    connection.setRequestMethod(POST);
                    connection.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(photo);
                    wr.flush();
                    wr.close();
                    int responseCode = connection.getResponseCode();
                    Toast.makeText(SendPhoto.this, String.valueOf(responseCode), Toast.LENGTH_LONG).show();
                    if (responseCode == 200) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        responseString = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            responseString.append(inputLine);
                        }
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    try {
                        connection.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
                Looper.loop();
            }
        };

        t.start();
    }
    private String getStringPicture() {
        myBitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.fakultetilogo);
        myBase64Image= encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);
        return  myBase64Image;
    }


    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
}
