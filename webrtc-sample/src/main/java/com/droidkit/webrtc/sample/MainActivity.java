package com.droidkit.webrtc.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import org.webrtc.*;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "WEBCHAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeerConnectionFactory.initializeAndroidGlobals(this, true, false);
        PeerConnectionFactory factory = new PeerConnectionFactory();

        MediaConstraints constraints = new MediaConstraints();
        ArrayList<PeerConnection.IceServer> servers = new ArrayList<PeerConnection.IceServer>();
        servers.add(new PeerConnection.IceServer("turn:numb.viagenie.ca", "me@ex3ndr.com", "Yjhdtrjv-2005"));

        PeerObserver observer = new PeerObserver();
        PeerConnection peerConnection = factory.createPeerConnection(servers, constraints, observer);

        MediaStream mediaStream = factory.createLocalMediaStream("ARDAMS");
        AudioTrack track = factory.createAudioTrack("ARDAMSa0", factory.createAudioSource(constraints));
        mediaStream.addTrack(track);
        peerConnection.addStream(mediaStream, constraints);

        peerConnection.createOffer(new SdpObserver() {
            @Override
            public void onCreateSuccess(SessionDescription sdp) {
                Log.d(TAG, "onCreateSuccess");
            }

            @Override
            public void onSetSuccess() {
                Log.d(TAG, "onSetSuccess");
            }

            @Override
            public void onCreateFailure(String error) {
                Log.d(TAG, "onCreateFailure");
            }

            @Override
            public void onSetFailure(String error) {
                Log.d(TAG, "onSetFailure");
            }
        }, constraints);

        // peerConnection.addStream(factory.createAudioSource(new MediaConstraints()))
    }

    private class PeerObserver implements PeerConnection.Observer {

        @Override
        public void onSignalingChange(PeerConnection.SignalingState newState) {
            Log.d(TAG, "onSignalingChange");
        }

        @Override
        public void onIceConnectionChange(PeerConnection.IceConnectionState newState) {
            Log.d(TAG, "onIceConnectionChange");
        }

        @Override
        public void onIceGatheringChange(PeerConnection.IceGatheringState newState) {
            Log.d(TAG, "onIceGatheringChange");
        }

        @Override
        public void onIceCandidate(IceCandidate candidate) {
            Log.d(TAG, "onIceCandidate");
        }

        @Override
        public void onError() {
            Log.d(TAG, "onError");
        }

        @Override
        public void onAddStream(MediaStream stream) {
            Log.d(TAG, "onAddStream");
        }

        @Override
        public void onRemoveStream(MediaStream stream) {
            Log.d(TAG, "onRemoveStream");
        }

        @Override
        public void onDataChannel(DataChannel dataChannel) {
            Log.d(TAG, "onDataChannel");
        }

        @Override
        public void onRenegotiationNeeded() {
            Log.d(TAG, "onRenegotiationNeeded");
        }
    }
}
