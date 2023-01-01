package com.example.trabajov3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.media.MediaPlayer;

/*
        en cuanto al tema del idioma lo he hecho siguendo tus apuntes pero me da errores ya que no me lo traduce
        y peta to el aplicativo.
        la idea del diseño del proyecto es que este tuviera un fragment que ocupara to el dispositivo
        como perfil del cantante pero no me dio tiemppo implementarlo asi que ya lo implemmentare yo mi cuenta
        la progres barra funciona de verdad es que al principio va un poco lento. Despues se pone normal.
        espero que se pueda enteder la variables tambien.

        Para el funcionamiento del selecionar cancion y que suene directamente me ha parecido mejor usarla asi ya que es mas
        intuitivo y todas las apps concurrentes lo hacen. En caso de que fuera extrictamente necesario para ti solo tendira
        que en el swich donde se selecciona la cancion. Que es donde se inicia en mi caso la cancion solo tendria que quitar el
        mediaPlayer.start(); y ya estaria ya que mi funcion de pause/play ya funciona correctamente y el ejercicio quedaria como
        useted dese.

        Saludos atentamente Vicktor Nascimento Rodrigues.

     */


public class RepreductorFragment extends Fragment {


    //variabes como atributos para acceder de forma mas comoda a ellas tanto de forma continuada para ciertos momentos
    //como para otros metodos.
        protected MediaPlayer mediaPlayer;
        protected ImageButton imgBtnReproductor, imgBtnCancion;
        protected ProgressBar pgbCancion;
        protected int duration;
       // protected MyCountDownTimer myCountDownTimer;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //me creo el viw del fragment para poder trabajar con el.
        View v= inflater.inflate(R.layout.fragment_repreductor, container, false);

        //creacion de variables
        Spinner spiGenero,spiGrupos, spiCancion;
        Button bntSalir;
        TextView txvFReproductorCancion, txvFReproductorAutor;
        ImageView imgFReproductor, imgCancionFReproductor;


        //inicializacion de los diferentes tipos de variables
        //tipo Spiners
        spiGenero= (Spinner) v.findViewById(R.id.spinnerGenero);
        spiGrupos= (Spinner) v.findViewById(R.id.spinnerGrupo);
        spiCancion= (Spinner) v.findViewById(R.id.spinnerCancion);

        //tipo textView
        txvFReproductorCancion =(TextView) v.findViewById(R.id.textViewFReproductorCancion);
        txvFReproductorAutor =(TextView) v.findViewById(R.id.textViewFReproductorAutor);

        //tipo botom
        bntSalir = (Button) v.findViewById(R.id.buttonSalirFReproductor);

        //tipo progressbar
        pgbCancion= (ProgressBar) v.findViewById(R.id.progressBarFReproductor);

        //tipo imagenViw
        imgFReproductor=(ImageView) v.findViewById(R.id.imageViewFReproductor);
        imgCancionFReproductor= (ImageView) v.findViewById(R.id.imageViewCancionFReproductor);

        //tipo imagem Botom
        imgBtnCancion=(ImageButton) v.findViewById(R.id.imageButtonCancionFReproductor);
        imgBtnReproductor=(ImageButton) v.findViewById(R.id.imageButtonReproductorFReproductor);

        //Aqui pongo en no visible tanto el botom de de la derecha que para ti no tendra sentido, pero por falta de tiempo no pude implementar es parte
        //pero la quiero terminar.
        //y la imagen de la cancion. Para luego mostrarla junto con la musica y la eleccion.
        imgCancionFReproductor.setVisibility(View.INVISIBLE);
       imgBtnCancion.setVisibility(View.INVISIBLE);



        //Aqui creamos los adapters
        //el adpater del spinner principal y el primero y se lo settoe con el stilo proprio que yo mismo cree..
        ArrayAdapter adapterGender = new ArrayAdapter(this.getContext(), R.layout.spinner_style,getResources().getStringArray(R.array.genero));
        spiGenero.setAdapter(adapterGender);


        //creo el evento ItemSelected para el spinner y voy setteando las generos segun su posicion
        spiGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter adapterGender;
                switch (i){

                    //en caso de que sea posicion 0 o Trap cojo el array de genero trap y lo paso al spiner de grupos.
                    case 0:
                        adapterGender = new ArrayAdapter(view.getContext(),  R.layout.spinner_menor,getResources().getStringArray(R.array.TrapGenre));
                        spiGrupos.setAdapter(adapterGender);
                        break;

                    //en caso de que sea posicion 1 o Trap cojo el array de genero Electronico y lo paso al spiner de grupos.
                    case 1:
                        adapterGender = new ArrayAdapter(view.getContext(), R.layout.spinner_menor,getResources().getStringArray(R.array.ElectronicGenre));
                        spiGrupos.setAdapter(adapterGender);
                        break;

                    //en caso de que sea posicion 2 o Trap cojo el array de genero Urbano y lo paso al spiner de grupos.
                    case 2:
                        adapterGender = new ArrayAdapter(view.getContext(), R.layout.spinner_menor,getResources().getStringArray(R.array.urbanGenre));
                        spiGrupos.setAdapter(adapterGender);
                        break;
                }

            }
                //es el metodo onNothing selected, es automatico y no se puede quitar
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {



            }
        });



        //creamos el otro Item selected para el segundo spiner, el de grupos.
        spiGrupos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter adapterGender;

                //paral los siguientes Spiners vamos a discriminar por su texto. para ello cojemos su texto.
                String item = adapterView.getSelectedItem().toString();

                //segun su texto sera :
                //el seteo del tercer Spiner y una foto del artista para que sepan cual es.
                switch (item){

                    case "Teto":
                        adapterGender = new ArrayAdapter(view.getContext(), R.layout.spinner_menor,getResources().getStringArray(R.array.Teto));
                        spiCancion.setAdapter(adapterGender);
                        Drawable drawable1 = getResources().getDrawable(R.drawable.tetolarge);
                        imgFReproductor.setImageDrawable(drawable1);

                        break;

                    case "Wiu":
                        adapterGender = new ArrayAdapter(view.getContext(), R.layout.spinner_menor,getResources().getStringArray(R.array.Wiu));
                        spiCancion.setAdapter(adapterGender);
                        Drawable drawable2 = getResources().getDrawable(R.drawable.wiusin);
                        imgFReproductor.setImageDrawable(drawable2);
                        break;

                    case "Zed":
                        adapterGender = new ArrayAdapter(view.getContext(), R.layout.spinner_menor,getResources().getStringArray(R.array.Zed));
                        spiCancion.setAdapter(adapterGender);
                        Drawable drawable3 = getResources().getDrawable(R.drawable.zedd);
                        imgFReproductor.setImageDrawable(drawable3);
                        break;

                    case "Marshmellow":
                        adapterGender = new ArrayAdapter(view.getContext(), R.layout.spinner_menor,getResources().getStringArray(R.array.Marsmellow));
                        spiCancion.setAdapter(adapterGender);
                        Drawable drawable4 = getResources().getDrawable(R.drawable.marshmellow);
                        imgFReproductor.setImageDrawable(drawable4);
                        break;

                    case "Don Patricio":
                        adapterGender = new ArrayAdapter(view.getContext(), R.layout.spinner_menor,getResources().getStringArray(R.array.DonPatricio));
                        spiCancion.setAdapter(adapterGender);
                        Drawable drawable5 = getResources().getDrawable(R.drawable.donpa);
                        imgFReproductor.setImageDrawable(drawable5);
                        break;

                    case "Bad Bunny":
                        adapterGender = new ArrayAdapter(view.getContext(), R.layout.spinner_menor,getResources().getStringArray(R.array.BadBunny));
                        spiCancion.setAdapter(adapterGender);
                        Drawable drawable6 = getResources().getDrawable(R.drawable.badbunny);
                        imgFReproductor.setImageDrawable(drawable6);
                        break;


                }

            }
            //es el metodo onNothing selected, es automatico y no se puede quitar
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {



            }
        });
            //Este set on itme selecte es para el spiner de la canion
        spiCancion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //es el mismo procedimiento de antes para el texto del spiner.
                String item = adapterView.getSelectedItem().toString();

                //segun el texto hara:
                // pausar y poner una cancion si esta sonando o no.
                //activara el progreso de la baarra de progreso segun la cancion para indicar cuanto falta.
                //la imagen de la cancion y el bonton en sonando.
                switch (item){

                    case "M4":
                        //si esta sonando una musica distinta de esta
                        if( mediaPlayer!=MediaPlayer.create(getContext(), R.raw.m4)){
                            //y si no esta puesto ninguan. parara esa cancion .
                            if(mediaPlayer!=null){
                                mediaPlayer.stop();
                            }
                            //iniciara la barra de progreso con el tiempo que dura la cancion
                           // barrCancion(153000);

                            //activara la cancion en cuestion
                            mediaPlayer = MediaPlayer.create(getContext(), R.raw.m4);
                            mediaPlayer.start();
                            pgbSong();
                            //se pondra los textos en la barra con los noombres del artista y la cancion
                            txvFReproductorCancion.setText(R.string.Teto);
                            txvFReproductorAutor.setText(R.string.M4);
                            //cambiara el botn de play a pause por si quieres pasar y viceversa. el metodo funciona asi.
                            CambiarBotonMusica();
                            //pone la imagen del disco en visible para que se vea si no se ha seleccionado ninguna cancion anteriormente.
                            imgBtnCancion.setVisibility(View.VISIBLE);
                            imgCancionFReproductor.setVisibility(View.VISIBLE);

                        }
                        //una vez la cancion este y la imagen este en visible se pone la foto
                        Drawable drawable1 = getResources().getDrawable(R.drawable.m4);
                        imgCancionFReproductor.setImageDrawable(drawable1);

                        break;

                    case "Brinca Demais":
                        //si esta sonando una musica distinta de esta
                        if( mediaPlayer!=MediaPlayer.create(getContext(), R.raw.brinca)){
                            //y si no esta puesto ninguan. parara esa cancion .
                            if(mediaPlayer!=null){
                                mediaPlayer.stop();
                            }
                            //iniciara la barra de progreso con el tiempo que dura la cancion
                            //barrCancion(192000);
                            //activara la cancion en cuestion
                            mediaPlayer = MediaPlayer.create(getContext(), R.raw.brinca);
                            mediaPlayer.start();
                            pgbSong();
                            //se pondra los textos en la barra con los noombres del artista y la cancion
                            txvFReproductorCancion.setText(R.string.Wiu);
                            txvFReproductorAutor.setText(R.string.brinca);
                            //cambiara el botn de play a pause por si quieres pasar y viceversa. el metodo funciona asi.
                            CambiarBotonMusica();
                            imgBtnCancion.setVisibility(View.VISIBLE);
                            imgCancionFReproductor.setVisibility(View.VISIBLE);
                        }
                        //una vez la cancion este y la imagen este en visible se pone la foto
                        Drawable drawable2 = getResources().getDrawable(R.drawable.wiu);
                        imgCancionFReproductor.setImageDrawable(drawable2);

                        break;

                    case "Scare to be lonle":
                        //si esta sonando una musica distinta de esta
                        if( mediaPlayer!=MediaPlayer.create(getContext(), R.raw.scaredtobelonely)){
                            //y si no esta puesto ninguan. parara esa cancion .
                            if(mediaPlayer!=null){
                                mediaPlayer.stop();
                            }
                            //iniciara la barra de progreso con el tiempo que dura la cancion
                            //barrCancion(207000);

                            //activara la cancion en cuestion
                            mediaPlayer = MediaPlayer.create(getContext(), R.raw.scaredtobelonely);
                            mediaPlayer.start();
                            pgbSong();
                            //se pondra los textos en la barra con los noombres del artista y la cancion
                            txvFReproductorCancion.setText(R.string.Zedd);
                            txvFReproductorAutor.setText(R.string.ScareTo);
                            //cambiara el botn de play a pause por si quieres pasar y viceversa. el metodo funciona asi.
                            CambiarBotonMusica();
                            //pone la imagen del disco en visible para que se vea si no se ha seleccionado ninguna cancion anteriormente.
                            imgBtnCancion.setVisibility(View.VISIBLE);
                            imgCancionFReproductor.setVisibility(View.VISIBLE);
                        }
                        //una vez la cancion este y la imagen este en visible se pone la foto
                        Drawable drawable3 = getResources().getDrawable(R.drawable.scareto);
                        imgCancionFReproductor.setImageDrawable(drawable3);

                        break;



                    case "Spotlight":
                        //si esta sonando una musica distinta de esta
                        if( mediaPlayer!=MediaPlayer.create(getContext(), R.raw.spotlight)){
                            //y si no esta puesto ninguan. parara esa cancion .
                            if(mediaPlayer!=null){
                                mediaPlayer.stop();
                            }
                            //iniciara la barra de progreso con el tiempo que dura la cancion
                            //barrCancion(186000);
                            //activara la cancion en cuestion
                            mediaPlayer = MediaPlayer.create(getContext(), R.raw.spotlight);
                            mediaPlayer.start();
                            pgbSong();
                            //se pondra los textos en la barra con los noombres del artista y la cancion
                            txvFReproductorCancion.setText(R.string.Marsmellow);
                            txvFReproductorAutor.setText(R.string.Spotlight);
                            //cambiara el botn de play a pause por si quieres pasar y viceversa. el metodo funciona asi.
                            CambiarBotonMusica();
                            //pone la imagen del disco en visible para que se vea si no se ha seleccionado ninguna cancion anteriormente.
                            imgBtnCancion.setVisibility(View.VISIBLE);
                            imgCancionFReproductor.setVisibility(View.VISIBLE);
                        }
                        //una vez la cancion este y la imagen este en visible se pone la foto
                        Drawable drawable4 = getResources().getDrawable(R.drawable.spotlight);
                        imgCancionFReproductor.setImageDrawable(drawable4);

                        break;

                    case "Porrito en paris":
                        //si esta sonando una musica distinta de esta
                        if( mediaPlayer!=MediaPlayer.create(getContext(), R.raw.porrito)){
                            //y si no esta puesto ninguan. parara esa cancion .
                            if(mediaPlayer!=null){
                                mediaPlayer.stop();
                            }
                            //iniciara la barra de progreso con el tiempo que dura la cancion
                           // barrCancion(330000);
                            mediaPlayer = MediaPlayer.create(getContext(), R.raw.porrito);
                            mediaPlayer.start();
                            pgbSong();
                            //se pondra los textos en la barra con los noombres del artista y la cancion
                            txvFReproductorCancion.setText(R.string.DonPa);
                            txvFReproductorAutor.setText(R.string.Porrito);
                            //cambiara el botn de play a pause por si quieres pasar y viceversa. el metodo funciona asi.
                            CambiarBotonMusica();
                            //pone la imagen del disco en visible para que se vea si no se ha seleccionado ninguna cancion anteriormente.
                            imgBtnCancion.setVisibility(View.VISIBLE);
                            imgCancionFReproductor.setVisibility(View.VISIBLE);
                        }
                        //una vez la cancion este y la imagen este en visible se pone la foto
                        Drawable drawable5 = getResources().getDrawable(R.drawable.porritoparris);
                        imgCancionFReproductor.setImageDrawable(drawable5);

                        break;

                    case "Yo no soy celoso":
                        //si esta sonando una musica distinta de esta
                        if( mediaPlayer!=MediaPlayer.create(getContext(), R.raw.yonosoyceloso)){
                            //y si no esta puesto ninguan. parara esa cancion .
                            if(mediaPlayer!=null){
                                mediaPlayer.stop();
                            }
                            //iniciara la barra de progreso con el tiempo que dura la cancion
                            //barrCancion(213000);
                            //activara la cancion en cuestion
                            mediaPlayer = MediaPlayer.create(getContext(), R.raw.yonosoyceloso);
                            mediaPlayer.start();
                            pgbSong();
                            //se pondra los textos en la barra con los noombres del artista y la cancion
                            txvFReproductorCancion.setText(R.string.BadBuny);
                            txvFReproductorAutor.setText(R.string.Yonosoy);
                            //cambiara el botn de play a pause por si quieres pasar y viceversa. el metodo funciona asi.
                            CambiarBotonMusica();
                            //pone la imagen del disco en visible para que se vea si no se ha seleccionado ninguna cancion anteriormente.
                            imgBtnCancion.setVisibility(View.VISIBLE);
                            imgCancionFReproductor.setVisibility(View.VISIBLE);
                        }
                        //una vez la cancion este y la imagen este en visible se pone la foto
                        Drawable drawable6 = getResources().getDrawable(R.drawable.yonosoycelos);
                        imgCancionFReproductor.setImageDrawable(drawable6);

                        break;

                    case "120":
                        //si esta sonando una musica distinta de esta
                        if( mediaPlayer!=MediaPlayer.create(getContext(), R.raw.cientovente)){
                            //y si no esta puesto ninguan. parara esa cancion .
                            if(mediaPlayer!=null){
                                mediaPlayer.stop();
                            }
                            //iniciara la barra de progreso con el tiempo que dura la cancion
                            //barrCancion(141000);
                            //activara la cancion en cuestion
                            mediaPlayer = MediaPlayer.create(getContext(), R.raw.cientovente);
                            mediaPlayer.start();
                            pgbSong();
                            //se pondra los textos en la barra con los noombres del artista y la cancion
                            txvFReproductorCancion.setText(R.string.BadBuny);
                            txvFReproductorAutor.setText(R.string.cientovente);
                            //cambiara el botn de play a pause por si quieres pasar y viceversa. el metodo funciona asi.
                            CambiarBotonMusica();
                            //pone la imagen del disco en visible para que se vea si no se ha seleccionado ninguna cancion anteriormente.
                            imgBtnCancion.setVisibility(View.VISIBLE);
                            imgCancionFReproductor.setVisibility(View.VISIBLE);

                        }
                        //una vez la cancion este y la imagen este en visible se pone la foto
                        Drawable drawable7 = getResources().getDrawable(R.drawable.cientovent);
                        imgCancionFReproductor.setImageDrawable(drawable7);

                        break;

                    default:
                        break;
                }

            }
            //es el metodo onNothing selected, es automatico y no se puede quitar
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {



            }
        });

        //metodo para salir del usuario pulsando el botm de salir
        bntSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //me aseguro de que haya una cancion en mediaPlayer para poder actuar sin que perte
                if(mediaPlayer!=null){
                    //si hay musica se pone la cancion en stop. este sonando o no. Al final se va ha ir.
                   mediaPlayer.stop();
                }
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //el metodo onClinc de la imagen  botom de pausa y reproducir
        imgBtnReproductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //si se reproduce musica
                if(mediaPlayer!=null)
                    //si esta sonando la pone en pausa y ppone el boton en play para que le den a volver a continuar
                if(mediaPlayer.isPlaying()==true){

                    mediaPlayer.pause();
                    CambiarBotonMusica();
                    //myCountDownTimer.cancel();


                    //en caso de que no este sonando pone la cancion a sonar de nuevo y pone la barra progresiva otra vez
                    //tambien pone el boton de pausa por si quieren pausar de nuevo.
                }else{
                    mediaPlayer.start();
                  //  myCountDownTimer.start();
                    pgbSong();
                    CambiarBotonMusica();
                }

            }
        });


        return v;
    }

    public void CambiarBotonMusica(){
        if(mediaPlayer!=null)
            if(mediaPlayer.isPlaying()==true){
            Drawable drawable1 = getResources().getDrawable(android.R.drawable.ic_media_pause);
            imgBtnReproductor.setImageDrawable(drawable1);
        }else{
            Drawable drawable1 = getResources().getDrawable(android.R.drawable.ic_media_play);
            imgBtnReproductor.setImageDrawable(drawable1);

        }
    }

    @SuppressLint("NewApi")
    public void pgbSong(){
        duration = mediaPlayer.getDuration();
        // Crea un hilo que se ejecutará cada 500 milisegundos para actualizar la barra de progreso
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //tenemos que pillar el activity por que el runon no se que, corre en el hilo principal y este es un fragmento.
                    Activity activity = getActivity();
                    if (activity != null) {
                        // Ejecuta código en el hilo principal utilizando activiti. runOnUiThread()

                         activity. runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Obtiene la posición actual de la canción en milisegundos
                            int currentPosition = mediaPlayer.getCurrentPosition();
                            // Calcula el porcentaje de progreso
                            int progress = (currentPosition * 100) / duration;
                            // Actualiza la barra de progreso
                            pgbCancion.setProgress(progress);
                        }
                    });
                }
            }
        }
        }).start();
        }






        /*

        //metoso que contiene el funcionamiento de la barra progresiva, esta parte se le pasa la duracion
    public void barrCancion(int num){
        if(myCountDownTimer!=null ){
            myCountDownTimer.cancel();
        }
        myCountDownTimer = new MyCountDownTimer(num, 1000);
        //en esta otra se iniia con el metoso de la clase que se ve mas abajo
        myCountDownTimer.start();
    }


    //metodo que contienee el funcionamiento de la progres barr
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        //se le paso lo minutos que se acaba
        public void onTick(long millisUntilFinished) {
           // el int se va llenando hasta llegar al maximo de la barra de rpogresa
            int progress = (int) (millisUntilFinished / 1000);
            pgbCancion.setProgress(pgbCancion.getMax() - progress);
        }

        //el metodo me obliga a tener un onFinish por que lo estoy sobrescriviendo.
        @Override
        public void onFinish() {

        }

        }

  */


    }


