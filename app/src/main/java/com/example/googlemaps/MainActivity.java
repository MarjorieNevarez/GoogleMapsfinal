package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
                        .findFragmentById(R.id.facultad);
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
                        , "  facultadci@uteq.edu.ec", "fci"},

                {"Instituto informática"
                        ,"El Ingeniero Telemático puede desarrollarse en Empresas de Telecomunicaciones, Empresas Financieras, Instituciones gubernamentales, Redes de comunicaciones privadas, Consultorías independientes etc."
                        , "carreratelematica@uteq.edu.ec", "instituto"},

                {"Biblioteca"
                        ,"Campus Central Av. Quito km. 11/2 vía a Santo Domingo de los Tsáchilas"
                        ,"biblioteca@uteq.edu.ec", "biblioteca"},

                {"Rectorado"
                        , "Edificio de rectorado y administrativos."
                        , "info@uteq.edu.ec", "rectorado"}
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
                View infoLayoutFacultad = getLayoutInflater().inflate(R.id.facultad, null);
                TextView edificiofacultad = infoLayoutFacultad.findViewById(R.id.informacion);
                TextView cordenada = infoLayoutFacultad.findViewById(R.id.coordenada);
                TextView correo = infoLayoutFacultad.findViewById(R.id.correo);
                ImageView logo = infoLayoutFacultad.findViewById(R.id.facultad);

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