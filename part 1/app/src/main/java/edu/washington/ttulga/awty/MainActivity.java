package edu.washington.ttulga.awty;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlarmManager;
import android.content.Context;
import android.app.PendingIntent;
import android.content.Intent;


public class MainActivity extends ActionBarActivity {
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);

        final EditText phone = (EditText) findViewById(R.id.editPhone);
        final EditText min = (EditText) findViewById(R.id.editMinutes);
        final EditText message = (EditText) findViewById(R.id.editMsg);
        final Button button = (Button) findViewById(R.id.startButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().equals("Start")) {
                    if (!message.getText().toString().trim().equals("") &&
                            !phone.getText().toString().trim().equals("") &&
                            Integer.parseInt(min.getText().toString()) > 0) {

                        button.setText("Stop");

                        alarmIntent.putExtra("phone", phone.getText().toString());
                        alarmIntent.putExtra("message", message.getText().toString());
                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

                        start();
                    }
                } else {
                    button.setText("Start");
                    stop();
                }
            }
        });
    }

    public void stop() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();

        Toast.makeText(this, "Alarm stopped", Toast.LENGTH_SHORT).show();
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        EditText min = (EditText) findViewById(R.id.editMinutes);
        int interval = Integer.parseInt(min.getText().toString()) * 1000 * 60;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm started", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
