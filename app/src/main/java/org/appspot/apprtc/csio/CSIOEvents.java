package org.appspot.apprtc.csio;

import org.webrtc.PeerConnection;

public  class CSIOEvents {
    public CSIOEvents(){

    }
    public static class OnICEConnectionChange {
        public PeerConnection.IceConnectionState state;

        public OnICEConnectionChange(PeerConnection.IceConnectionState _state) {
            state = _state;
        }
    }

    public static class OnICEGatheringChanges {
        public PeerConnection.IceGatheringState state;

        public OnICEGatheringChanges(PeerConnection.IceGatheringState _state) {
            state = _state;
        }
    }

    public static class OnICESignalingChange {
        public PeerConnection.SignalingState state;

        public OnICESignalingChange(PeerConnection.SignalingState _state) {
            state = _state;
        }
    }
}
