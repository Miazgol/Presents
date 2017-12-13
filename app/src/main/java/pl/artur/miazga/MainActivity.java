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

    EditText name;
    EditText number;
    Button sendSmsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        sendSmsButton = (Button) findViewById(R.id.send_button);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendSmsButton.setEnabled(true);
        } else {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkPermissionClick(View view) {
        String permission = Manifest.permission.SEND_SMS;
        int REQUEST_CODE = 111;

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            sendSmsButton.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, REQUEST_CODE);
        }
    }

    public void addPerson(View view) {
        persons.add(new Person(name.getText(), number.getText()));
    }

    public void sendSmsButton(View view) {

        Collections.shuffle(persons);
        for (int i = 0; i < persons.size(); i++) {
            try {
                String number = persons.get(i).getNumber();
                String name = persons.get(i + 1).getName();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, name, null, null);
            } catch (Exception ignore) {
                String number = persons.get(persons.size() - 1).getNumber();
                String name = persons.get(0).getName();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, name, null, null);
            }

        }
    }
}
