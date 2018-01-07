package com.journaldev.passingdatabetweenfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

import static com.journaldev.passingdatabetweenfragments.R.id.login_btn;

public class LoginActivity extends AppCompatActivity {


    private Context context;

    private TextView warningText;
    private EditText loginName;
    private EditText loginPass;
    private Button loginBtn;
    private Button devBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        context = this;

        loginName = (EditText) findViewById(R.id.login_name);
        loginPass = (EditText) findViewById(R.id.login_pass);
        loginBtn = (Button) findViewById(login_btn);
        devBtn = (Button) findViewById(R.id.dev_enter);
        warningText = (TextView) findViewById(R.id.login_warning);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginName.getText().toString().equalsIgnoreCase("") ||
                        loginPass.getText().toString().equalsIgnoreCase("")) {
                    warningText.setText("Заполните все поля!");
                } else {
                    try {
                        post();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        devBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void post() throws IOException, JSONException {
        JSONObject object = new JSONObject();
        object.put("username", loginName.getText().toString());
        object.put("password", loginPass.getText().toString());

        String yourData = object.toString();
        StringEntity entity = null;
        try {
            entity = new StringEntity(yourData);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (Exception e) {
//Exception
        }

        String url = "https://back-ecostrum.herokuapp.com/login";

        new AsyncHttpClient().post(null, url, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                String object = new String(responseBody);
                if (object.equalsIgnoreCase("true")) {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                } else {
                    warningText.setText("Неправильный логин или пароль!");
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
