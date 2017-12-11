package pl.artur.miazga;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<Person> persons = new ArrayList<>();

    EditText imie;
    EditText numer;
    Button wyslijSmsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imie = (EditText) findViewById(R.id.imie);
        numer = (EditText) findViewById(R.id.numer);
        wyslijSmsButton = (Button) findViewById(R.id.wyslij_button);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            wyslijSmsButton.setEnabled(true);
        } else {
            Toast.makeText(this, "Brak uprawnień", Toast.LENGTH_SHORT).show();
        }
    }

    public void sprawdzUprawnieniaClick(View view) {
        String uprawnienie = Manifest.permission.SEND_SMS;
        int REQUEST_CODE = 111;

        if (ContextCompat.checkSelfPermission(this, uprawnienie) == PackageManager.PERMISSION_GRANTED) {
            wyslijSmsButton.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{uprawnienie}, REQUEST_CODE);
        }
    }

    public void dodajOsobe(View view) {
        persons.add(new Person(imie.getText(), numer.getText()));
    }

    public void wyslijSmsButton(View view) {

        Collections.shuffle(persons);
        for (int i = 0; i < persons.size(); i++) {
            try {
                String numer = persons.get(i).getNumber();
                String imie = persons.get(i + 1).getName();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(numer, null, imie, null, null);
            } catch (Exception ignore) {
                String numer = persons.get(persons.size()-1).getNumber();
                String imie = persons.get(0).getName();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(numer, null, imie, null, null);
            }

        }
    }
}
