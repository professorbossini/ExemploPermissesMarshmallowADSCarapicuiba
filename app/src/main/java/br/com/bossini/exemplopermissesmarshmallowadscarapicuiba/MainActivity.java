package br.com.bossini.exemplopermissesmarshmallowadscarapicuiba;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    private LocationManager locationManager;
    private TextView locationTextView;
    private static final int REQ_PERMISSAO_GPS = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationTextView = (TextView)
                findViewById(R.id.locationTextView);
        cronometro = (Chronometer)
                findViewById(R.id.cronometro);
    }

    private MeuObservadorDeLocalizacoes observer
            = new MeuObservadorDeLocalizacoes();

    private class MeuObservadorDeLocalizacoes
                    implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            double latitude =
                    location.getLatitude();
            double longitude =
                    location.getLongitude();
            String texto =
                    String.format ("Lat: %f, Long: %f",
                            latitude, longitude);
            locationTextView.
                    setText(texto);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //verificar se o usuário ja deu permissao
        //se ja deu permissao
            //ligue o gps
        //senao
            //explicar para ele pq o gps é necessário
            //pedir permissão
        if (ActivityCompat.
                checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    observer

            );
        }
        else{
            Toast.makeText(this,
                    getString(R.string.explicacao_gps),
                    Toast.LENGTH_SHORT).show();
            ActivityCompat.
                    requestPermissions(this,
                           new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                           }, REQ_PERMISSAO_GPS );
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_PERMISSAO_GPS){
            if (grantResults.length > 0
                    && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.
                        checkSelfPermission(this,
                                Manifest.
                                        permission.
                                        ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED){
                    locationManager.
                            requestLocationUpdates(
                                    LocationManager.
                                            GPS_PROVIDER,
                                    0,
                                    0,
                                    observer
                            );
                }
            }
            else{
                Toast.makeText(this,
                        getString(R.string.explicacao_gps),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.
                removeUpdates(observer);
    }

    private Chronometer cronometro;

}
