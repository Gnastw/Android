package com.example.etudiant2.resto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.etudiant2.resto.R;
import com.example.etudiant2.resto.interfaces.WsInterface;
import com.example.etudiant2.resto.manager.WsManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, WsInterface{

    private EditText username;
    private EditText password;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        this.username = findViewById(R.id.iptUsername);
        this.password = findViewById(R.id.iptPassword);
        this.btnLogin = findViewById(R.id.btnLogin);
        this.btnSignup = findViewById(R.id.btnRegister);

        this.btnLogin.setOnClickListener(this);
        this.btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin){
            String dataUsername = username.getText().toString();
            String dataPassword = password.getText().toString();

            HashMap<String, String> datas = new HashMap<>();
            datas.put("login", dataUsername);
            datas.put("pass", dataPassword);

            WsManager manager = new WsManager(this);
            manager.sendRequest("ws/resto/connexion", datas);
        }

        if(view == btnSignup){
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        }


    }


    @Override
    public void error(String error) {
        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
        Log.d("ERROR", error);
    }

    @Override
    public void success(String data) {
        Log.d("success", data);

        JSONObject jObj;
        String error = null;
        String id = null;
        try {
            jObj = new JSONObject(data);
            error = jObj.getString("error");
            id = jObj.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(error != null){
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
        }
        if(id != null){
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        }


    }
}

