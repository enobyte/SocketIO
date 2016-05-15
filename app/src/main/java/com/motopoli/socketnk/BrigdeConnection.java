package com.motopoli.socketnk;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.thread.EventThread;

/**
 * Created by Eno on 5/11/2016.
 */
public class BrigdeConnection extends Connection {
    private Socket socket;
    private static Context context;
    private Activity activity;

    {
        try {
            socket = IO.socket(Connection.url);
            Log.d("Koneski", "Koneksi berhasil" + String.valueOf(socket));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public BrigdeConnection(Activity activity) {
        this.activity = activity;
    }

    @Override
    BrigdeConnection send(final String channel, final Object param) {
        EventThread.exec(new Runnable() {
            @Override
            public void run() {
                socket.emit(channel, param);
            }
        });
        return this;
    }

    @Override
    String get(String channel) {
        socket.on(channel, getOn);
        return "";
    }

    @Override
    void runConnect(Boolean connect) {
        if (connect) {
            socket.connect();
            Log.d("Koneski", "Connect");
        } else {
            socket.disconnect();
            Log.d("Koneski", "Disconnect");
        }

    }

    private Emitter.Listener getOn = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("Koneksi", args[0].toString());
                    Toast.makeText(activity, args[0].toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    };
}
