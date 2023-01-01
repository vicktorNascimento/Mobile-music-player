package com.example.trabajov3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //autor Vicktor Nascimento Rodrigues 2Dam


        //esto llama al fragmento splahs cuando se inicia la app
        Intent i = new Intent(MainActivity.this, Splash.class);
        startActivity(i);

        //esto es la creacion de variables
        EditText etxUser,etxPass;
        Button btnConfirmar;

        //aqui inicializamos las variables con los datos de la app
        etxUser= (EditText)findViewById(R.id.editTextTextUser);
        etxPass= (EditText)findViewById(R.id.editTextTextPassword);
        btnConfirmar= (Button)findViewById(R.id.button);

        //una vez tenemos las varables inicializadas creamos el metodo onClick para el botn
        //metodo que consiste en llamar al acttiviti de splash error o al fragment en caso de que sea correcto
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cojemos el texto de nuestras varables para comparar
                String usuario = etxUser.getText().toString();
                String contrasena = etxPass.getText().toString();

                //las comparamos al usuario para ver si estan correctas.
                if (usuario.equals("admin") && contrasena.equals("1234")) {

                    //antes de iniciar el fragmento me aseguro que lo datos se borren por si quirer salir y despuses volver a entrar y otra pessona
                    //no se encuentre los datos dispuestos.
                        etxUser.setText("");
                        etxPass.setText("");

                            //en caso correcto llama al fragmento
                    //lo llamo asi para evitar bugss
                        try {
                            //esto es como se usa un fragmento.
                            fragmentoReproductor();



                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //en caso incorrecto llamara la splash errorr
                }else{
                    Intent i = new Intent(MainActivity.this, ErrorActivity.class);
                    startActivity(i);
                }
            }

        });


    }
            //este es el metodo para llamar al fragmento
    protected void fragmentoReproductor(){

        FragmentTransaction ft= MainActivity.this.getSupportFragmentManager().beginTransaction();
        RepreductorFragment rf= new RepreductorFragment();
       ft.replace(android.R.id.content, rf)
               .addToBackStack(null)
              .commit();


    }

}