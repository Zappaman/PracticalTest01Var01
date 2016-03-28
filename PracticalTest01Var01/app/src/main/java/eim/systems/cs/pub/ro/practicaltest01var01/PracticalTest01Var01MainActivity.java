package eim.systems.cs.pub.ro.practicaltest01var01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var01MainActivity extends AppCompatActivity {

    private Button add, compute;
    private EditText nextTerm;
    private EditText sumEdit;
    public static final int REQUEST = 1;
    private int sum_cache = -1;
    private boolean hasCache = false;
    private String lastAll = "";
    private boolean passedTen = false;
    private class MyClicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.add:
                    String s = nextTerm.getText().toString();
                    if (s.length() > 0) {
                        String sumS = sumEdit.getText().toString();
                        if (sumS.length() != 0) {
                            sumS = sumS + '+';
                        }
                        sumS = sumS + s;
                        sumEdit.setText(sumS);
                    }
                    break;
                case R.id.compute:
                    String sumS = sumEdit.getText().toString();
                    if (!lastAll.equals(sumS)) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01SecondaryActivity.class);
                        intent.putExtra("sumaString", sumS);
                        lastAll = sumS;
                        startActivityForResult(intent, REQUEST);
                    } else {
                        Toast.makeText(getApplicationContext(), "CACHE Suma este " + String.valueOf(sum_cache), Toast.LENGTH_LONG).show();
                    }
                    break;
            }

        }
    }
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    public MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    public IntentFilter filter = new IntentFilter();

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST) {
            String sumString = data.getStringExtra("rezultat");
            int sum = Integer.parseInt(sumString);
            Toast.makeText(getApplicationContext(), "Suma este " + String.valueOf(sum), Toast.LENGTH_LONG).show();
            hasCache = true;
            sum_cache = sum;
            if (sum_cache > 10) {
                passedTen = true;
            }
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01Service.class);
            intent.putExtra("suma", String.valueOf(sum_cache));
            startService(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Eroare! ", Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_main);
        add = (Button)(findViewById(R.id.add));
        compute = (Button)findViewById(R.id.compute);
        nextTerm = (EditText)findViewById(R.id.nextTerm);
        sumEdit = (EditText)findViewById(R.id.sumEdit);
        MyClicker c = new MyClicker();
        add.setOnClickListener(c);
        compute.setOnClickListener(c);
        filter.addAction("action");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        lastAll = savedInstanceState.getString("allTerms");
        sum_cache = savedInstanceState.getInt("sum_cache");

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("allTerms", lastAll);
        outState.putInt("sum_cache", sum_cache);
        super.onSaveInstanceState(outState);

    }
}
