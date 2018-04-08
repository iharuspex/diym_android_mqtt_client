package com.harus.mqttclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Switch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import helpers.MqttHelper;

public class MainActivity extends AppCompatActivity {
    MqttHelper  mqttHelper;

    TextView    dataReceived;
    Switch      switchManualControl;
    Switch      switchAutomaticControl;
    Switch      switchIgnoreAll;
    Switch      switchAllowAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataReceived             = (TextView) findViewById(R.id.dataReceived);
        switchManualControl      = (Switch) findViewById(R.id.switchManualControl);
        switchAutomaticControl   = (Switch) findViewById(R.id.switchAutomaticControl);
        switchIgnoreAll          = (Switch) findViewById(R.id.switchIgnoreAll);
        switchAllowAll           = (Switch) findViewById(R.id.switchAllowAll);

        switchManualControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //if (switchManualControl.isChecked()){
                    Log.w("Debug", "I'm checked");
                    MqttMessage m = new MqttMessage();
                    String msg = "";
                    m.setPayload(msg.getBytes());
                //}
            }
        });

        startMqtt();


    }

    private void startMqtt() {
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", mqttMessage.toString());
                dataReceived.setText(mqttMessage.toString());

                switch (mqttMessage.toString()){
                    case "wait_call":
                        //Log.w("Debug", "I'm recived");
                        break;
                    case "call":
                        String msg = "user_notify";
                        mqttHelper.publishMessage(mqttHelper.controlPubTopic, msg, 2);
                        break;
                    case "wait_user":

                        break;
                    case "up_phone":

                        break;
                    case "down_phone":

                        break;
                    case "open_dor":

                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}