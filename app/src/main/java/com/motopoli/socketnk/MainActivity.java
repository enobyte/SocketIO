package com.motopoli.socketnk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button connect, emiter;
    /*private Socket socket;*/
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            /*socket = IO.socket("http://192.168.43.161:3000");*/
            socket = IO.socket("http://api.motopoli.com");
        } catch (Exception e) {
            e.printStackTrace();
        }

        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*JSONObject data = (JSONObject) args[0];
                        Log.d("data", data.toString());*/
                    }
                });
                /*socket.emit("message_to_client", "OK");*/
                //socket.disconnect();
            }
        }).on("message_to_client", new Emitter.Listener() {

            @Override
            public void call(Object... args) {

            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });
        socket.connect();

        emiter = (Button) findViewById(R.id.emit);
        connect.setOnClickListener(this);
        emiter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.emit:
                /*socket.emit("serverMsg", "OK", "OK", "ok");*/
                JSONObject obj = new JSONObject();
                try {
                    obj.put("satu", "value1");
                    obj.put("dua", "value2");
                    obj.put("tiga", "value3");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("send_broadcast", "ENO GANTENG");
                socket.on("receive_broadcast", msng);
                break;
        }
    }

    private Emitter.Listener msng = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                       /*JSONObject object = new JSONObject(args[0].toString());
                       System.out.println(object);*/
                        Toast.makeText(MainActivity.this, args[0].toString(), Toast.LENGTH_SHORT).show();
                        Log.d("Koneksi", args[0].toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                   /*JSONObject json = new JSONObject()*/
                }
            });
        }
    };
}
