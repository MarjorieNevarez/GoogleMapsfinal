package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity
        extends  FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.mapaFacultad);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        TextView Latitud =  findViewById(R.id.lblatitud);
        Latitud.setText("Latitud: "+ String.format("%.4f",latLng.latitude));
        TextView Longitud =  findViewById(R.id.lblongitud);
        Longitud.setText("Longitud: "+ String.format("%.4f",latLng.longitude));
        mapa.addMarker(new MarkerOptions().position(latLng)
                .title("Marcador"));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.getUiSettings().setZoomControlsEnabled(true);

        LatLng[] coordenadaFacultad = {
                new LatLng(-1.0126176861580891, -79.47058517109298),//Facultad de ingeniería
                new LatLng(-1.0122036473056713, -79.46981078739267),//Instituto informática
                new LatLng(-1.0124617696225804, -79.4684361552728),//Biblioteca
                new LatLng(-1.0127232441569385, -79.46863732094916)//Rectorado
        };
        CameraUpdate camUpd1 =
                CameraUpdateFactory
                        .newLatLngZoom(coordenadaFacultad[2], 15);
        mapa.moveCamera(camUpd1);
        mapa.setOnMapClickListener(this);

        String[][] infoMarcadores = {
                {"Facultad de Ciencias de la Ingenieria"
                        , "Dotar de una sólida preparación científica y tecnológica, con profundos conocimientos de las ciencias y de los procesos tecnológicos proporcionando los fundamentos, herramientas y habilidades necesarias para hacer ingeniería, incluyendo la enseñanza de normas nacionales e internacionales, procesos administrativos, planeación estratégica, control de calidad y liderazgo sin olvidar la responsabilidad con la ecología y el entorno social, procurando el avance y transmisión del saber universal, adaptándolo para el servicio de la comunidad en pos del desarrollo del país a través de su programa de educación formal."
                        , "  facultadci@uteq.edu.ec", "@drawable/fci"},

                {"Instituto informática"
                        ,"Se  constituye en un referente de sostenibilidad académica, reconocida regionalmente por formar profesionales cualificados"
                        ,"Formar íntegramente profesionales de la salud con sólidas bases científicas, humanísticas que le permitan actuar ante las necesidades de salud",
                        "fcs@uteq.edu.ec", "@drawable/salud"},

                {"Biblioteca"
                        , "Su función principal es liderar y dirigir la universidad, estableciendo objetivos, gestionando recursos, promoviendo la excelencia académica y representando a la institución ante diversas entidades. "
                        , "Además, supervisa la gestión administrativa y académica"
                        , "info@uteq.edu.ec", "@drawable/edirectorado"},

                {"Rectorado"
                        , "Es el centro neurálgico donde se coordinan y ejecutan las políticas, procedimientos y decisiones administrativas que son fundamentales para el funcionamiento adecuado de la universidad."
                        , "Donde se llevan a cabo las actividades relacionadas con la gestión y administración de la institución."
                        , "info@uteq.edu.ec", "@drawable/administrativo"}
        };
        //Indice de colores
        float[] coloresMarcadores = {
                BitmapDescriptorFactory.HUE_BLUE,
                BitmapDescriptorFactory.HUE_CYAN,
                BitmapDescriptorFactory.HUE_ORANGE,
                BitmapDescriptorFactory.HUE_MAGENTA
        };

        for (int i=0; i<infoMarcadores.length; i++) {
            int indiceColor = i % coloresMarcadores.length;
            marcadorFacultad(coordenadaFacultad[i], infoMarcadores[i][0], coloresMarcadores[indiceColor]);
        }
        mapa.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                // referencias de layout_facultad
                View infoLayoutFacultad = getLayoutInflater().inflate(R.id.imgFacultad, null);
                TextView edificiofacultad = infoLayoutFacultad.findViewById(R.id.infoFacultad);
                TextView vision = infoLayoutFacultad.findViewById(R.id.infoVision);
                TextView cordenada = infoLayoutFacultad.findViewById(R.id.infoCoordenada);
                TextView mision = infoLayoutFacultad.findViewById(R.id.infoMision);
                TextView correo = infoLayoutFacultad.findViewById(R.id.infoCorreo);
                ImageView logo = infoLayoutFacultad.findViewById(R.id.imgFacultad);

                TextView Latitud =  findViewById(R.id.lblatitud);
                TextView Longitud =  findViewById(R.id.lblongitud);

                // Marcador de vistas
                String info = marker.getTitle();
                for (int p=0; p<infoMarcadores.length; p++){
                    if(info.equals(infoMarcadores[p][0])){

                        Latitud.setText(String.valueOf(coordenadaFacultad[p]));
                        Longitud.setText("");

                        edificiofacultad.setText(infoMarcadores[p][0]);
                        cordenada.setText(String.valueOf(coordenadaFacultad[p]));
                        vision.setText(infoMarcadores[p][1]);
                        mision.setText(infoMarcadores[p][2]);
                        correo.setText(infoMarcadores[p][3]);
                        String urlLogo = String.valueOf(infoMarcadores[p][4]);
                        int resourceId = getResources().getIdentifier(urlLogo, "drawable", getPackageName());
                        logo.setImageResource(resourceId);
                    }
                }
                return infoLayoutFacultad;
            }

            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null;
            }
        });
    }
    public void marcadorFacultad(LatLng coordenadas, String edificiofacultad, float color){
        mapa.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(edificiofacultad)
                .icon(BitmapDescriptorFactory.defaultMarker(color))
        );
    }
}