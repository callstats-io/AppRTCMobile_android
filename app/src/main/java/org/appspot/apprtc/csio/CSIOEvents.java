package org.appspot.apprtc.csio;

import org.webrtc.PeerConnection;

import io.callstats.LoggingLevel;

public class CSIOEvents {
    public CSIOEvents() {

    }

    public static class OnFabricSetup {
        public PeerConnection peerConnection;

        public OnFabricSetup(PeerConnection _peerConnection) {
            peerConnection = _peerConnection;
        }

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

    public static class OnAddRemoveStram {
        public boolean isAdded;

        public OnAddRemoveStram(boolean _isAdded) {
            isAdded = _isAdded;
        }
    }

    public static class OnMuteUnmuteAudio {
        public boolean isMuted;
        public String deviceId;

        public OnMuteUnmuteAudio(boolean _isMuted, String _deviceId) {
            isMuted = _isMuted;
            deviceId = _deviceId;
        }
    }

    public static class OnVideoPlayPause {
        public boolean isPaused;
        public String deviceId;

        public OnVideoPlayPause(boolean _isPaused, String _deviceId) {
            isPaused = _isPaused;
            deviceId = _deviceId;
        }
    }

    public static class OnHold {
        public OnHold() {
        }
    }

    public static class OnResume {
        public OnResume() {
        }
    }

    public static class OnLogs {
        String message;
        LoggingLevel level;

        public OnLogs(String _message, LoggingLevel _level) {
            message = _message;
            level = _level;
        }
    }

}
