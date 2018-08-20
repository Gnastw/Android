package com.example.etudiant2.resto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.etudiant2.resto.interfaces.WsInterface;
import com.example.etudiant2.resto.manager.WsManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, WsInterface{

    private EditText lastname;
    private EditText firstname;
    private EditText username;
    private EditText password;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        this.lastname = findViewById(R.id.lastname);
        this.firstname = findViewById(R.id.firstname);
        this.username = findViewById(R.id.iptUsername);
        this.password = findViewById(R.id.iptPassword);
        this.btnCreate = findViewById(R.id.btnRegister);

        this.btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnCreate){
            String dataFirstname = firstname.getText().toString();
            String dataLastname = lastname.getText().toString();
            String dataUsername = username.getText().toString();
            String dataPassword = password.getText().toString();

            HashMap<String, String> datas = new HashMap<>();
            datas.put("login", dataUsername);
            datas.put("pass", dataPassword);
            datas.put("nom", dataFirstname);
            datas.put("prenom", dataLastname);

            WsManager manager = new WsManager(this);
            manager.sendRequest("ws/resto/addCompte", datas);
        }


    }


    @Override
    public void error(String error) {
        Toast.makeText(SignupActivity.this, error, Toast.LENGTH_LONG).show();
        Log.d("ERROR", error);
    }

    @Override
    public void success(String data) {
        Log.d("success", data);
        finish();
    }
}

