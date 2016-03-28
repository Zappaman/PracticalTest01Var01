package eim.systems.cs.pub.ro.practicaltest01var01;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Date;

public class PracticalTest01Var01Service extends Service {
    public PracticalTest01Var01Service() {
    }

    MyThread t = null;
    private class MyThread extends Thread {
        Context myC;
        boolean isRunning = true;
        int mySuma;
        MyThread(Context c, int s) {
            myC = c;
            mySuma = s;
        }

        public void run() {
            while (isRunning) {
                Intent intent = new Intent();
                intent.setAction("action");
                intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + mySuma);
                myC.sendBroadcast(intent);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void StopThread() {
            isRunning = false;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String suma = intent.getStringExtra("suma");
        int s = Integer.parseInt(suma);
        t = new MyThread(getApplicationContext(), s);
        t.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onDestroy() {
        t.StopThread();
        super.onDestroy();
    }
}
