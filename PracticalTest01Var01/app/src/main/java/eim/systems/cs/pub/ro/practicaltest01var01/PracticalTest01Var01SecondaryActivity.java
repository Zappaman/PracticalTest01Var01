package eim.systems.cs.pub.ro.practicaltest01var01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PracticalTest01Var01SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_secondary);
        Intent intent = getIntent();
        if (intent != null) {
            String sumaString = intent.getStringExtra("sumaString");
            String[] tokens = sumaString.split("\\+");
            int sum = 0;
            for (int i = 0; i < tokens.length; ++i) {
                int num = Integer.parseInt(tokens[i]);
                sum += num;
            }

            Intent newIntent = new Intent();
            newIntent.putExtra("rezultat", String.valueOf(sum));
            setResult(RESULT_OK, newIntent);
        }
        finish();
    }
}
