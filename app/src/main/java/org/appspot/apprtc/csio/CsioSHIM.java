package org.appspot.apprtc.csio;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.webrtc.Logging;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import io.callstats.Callstats;
import io.callstats.CallstatsConfig;
import io.callstats.OnIceConnectionChange;
import io.callstats.OnIceGatheringChange;
import io.callstats.OnSignalingChange;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CsioSHIM {
    private static final String TAG = "CsioSHIM";

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    private CsioUtils csioUtils;
    private Callstats callstats;
    private String peerId;

    // Constructor
    public CsioSHIM() {
        csioUtils = new CsioUtils();
    }

    public void Initialize(String alias, Context context) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        if (alias == null) {
            throw new RuntimeException("DEVICE ID CANNOT BE NULL");
        }
        if (context == null) {
            throw new RuntimeException("CONTEXT CANNOT BE NULL");
        }
        String appId = "callstats application id";
        String keyId = "callstats key id";
        String localId = csioUtils.getLocalId();

        String jwtToken = getJwtToken(appId, keyId, localId);
        String deviceId = csioUtils.getDeviceId(context);

        String clientVersion = "0.1.1";
        CallstatsConfig callstatsConfig = new CallstatsConfig();


        // create a peer ID
        peerId = getPeerId(localId, deviceId);

        callstats = new Callstats(
                context,
                appId,
                localId,
                deviceId,
                jwtToken,
                alias,
                clientVersion,
                callstatsConfig);
    }

    public void StartSession(String roomId) {
        if (roomId == null) {
            throw new RuntimeException("ROOM, OR CONFERENCE ID CANNOT BE NULL");
        }
        if (callstats == null) {
            throw new RuntimeException("CALLSTATS OBJECT CANNOT BE NULL");
        }
        EventBus.getDefault().register(this);
        callstats.startSession(roomId);
    }

    public void Dispose() {
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception err) {
            Logging.w(TAG, err.getMessage());
        }
        if (callstats == null) {
            return;
        }
        callstats.stopSession();
    }

    private String getPeerId(String localId, String deviceId) {
        StringBuilder sb = new StringBuilder();
        sb.append(localId).append("-").append(deviceId);
        return sb.toString();
    }

    private String getJwtToken(String appId, String keyId, String userId) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        String key = "YOUR_KEY";
        KeyFactory factory = KeyFactory.getInstance("ECDSA", BouncyCastleProvider.PROVIDER_NAME);
        PrivateKey keypem = factory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(key, Base64.DEFAULT)));

        Map<String, Object> claims = new HashMap<>();
        claims.put("appID", appId);
        claims.put("keyID", keyId);
        claims.put("userID", userId);

        String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.ES256, keypem).compact();
        return jwtToken;
    }

    // PeerConnection related events

    @Subscribe
    public void OnIceConnectionChange(CSIOEvents.OnICEConnectionChange state) {
        Logging.d(TAG, "OnIceConnectionChange " + state.state);
        if (callstats != null) {
            callstats.reportEvent(peerId, new OnIceConnectionChange(state.state));
        }
    }

    @Subscribe
    public void OnICEGatheringChanges(CSIOEvents.OnICEGatheringChanges state) {
        Logging.d(TAG, "OnICEGatheringChanges " + state.state);
        if (callstats != null) {
            callstats.reportEvent(peerId, new OnIceGatheringChange(state.state));
        }
    }

    @Subscribe
    public void OnICESignalingChange(CSIOEvents.OnICESignalingChange state) {
        Logging.d(TAG, "OnICESignalingChange " + state.state);
        if (callstats != null) {
            callstats.reportEvent(peerId, new OnSignalingChange(state.state));
        }
    }

}
