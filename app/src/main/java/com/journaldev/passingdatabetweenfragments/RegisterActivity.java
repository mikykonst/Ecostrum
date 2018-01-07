package com.journaldev.passingdatabetweenfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private Button regBtn;
    private Button goToLogin;
    private Context context;
    private EditText nameReg;
    private EditText passReg;
    private EditText passReg2;
    private TextView warningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;

        regBtn = (Button) findViewById(R.id.reg_btn);
        goToLogin = (Button) findViewById(R.id.href_to_login_page);
        nameReg = (EditText) findViewById(R.id.reg_name);
        passReg = (EditText) findViewById(R.id.reg_pass_1);
        passReg2 = (EditText) findViewById(R.id.reg_pass2);
        warningText = (TextView) findViewById(R.id.warning);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameReg.getText().toString().equalsIgnoreCase("") ||
                        passReg.getText().toString().equalsIgnoreCase("") ||
                        passReg2.getText().toString().equalsIgnoreCase("")) {
                    warningText.setText("Заполните все поля!");
                } else if (!passReg.getText().toString().equals(passReg2.getText().toString())) {
                    warningText.setText("Пароли не совпадают!");
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
