package com.example.proyectilulu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.proyectilulu.Json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {
    private List<MyInfo> list;
    public static String TAG = "mensaje";
    String json = null;
    public static String usr,passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button registro = findViewById(R.id.registro);
        Button Acceso = findViewById(R.id.Acceso);
        Button olvido = findViewById(R.id.olvido);
        EditText usuario = findViewById(R.id.User);
        EditText passws = findViewById(R.id.Password);
        Read();
        json2List(json);


        Acceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr = String.valueOf(usuario.getText());
                passw = String.valueOf(passws.getText());
                acceso();
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Registro.class);
                startActivity(i);
            }
        });

        olvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Olvido.class);
                startActivity(i);
            }
        });
    }
    public boolean Read(){
        if(!isFileExits()){
            return false;
        }
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)file.length()];
        try{
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json = new String(bytes);
            Log.d(TAG, json);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public void json2List(String json){
        Gson gson = null;
        String mensaje = null;
        if(json == null || json.length() == 0){
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if(list == null || list.size() == 0){
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }
    private File getFile(){return new File(getDataDir() , Registro.archivo);}

    private boolean isFileExits(){
        File file = getFile();
        if(file == null){
            return false;
        }
        return file.isFile() && file.exists();
    }
    public void acceso(){
        int i = 0;
        for(MyInfo myInfo : list){
            if (myInfo.getUsuario().equals(usr)&&myInfo.getContraseña().equals(passw)){
                Intent intent = new Intent(Login.this, welcome_to_the_jungle.class);
                intent.putExtra("Objeto", myInfo);
                startActivity(intent);
                i = 1;

            }
        }
        if (i == 0){
            Toast.makeText(getApplicationContext(), "El usuario o contraseña son incorrectos ",Toast.LENGTH_LONG).show();
        }
    }
}