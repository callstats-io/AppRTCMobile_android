# AppRTC Android demo 

AppRTC demo application with Callstats.io android library integration

## Getting Started

These instructions will help you run the apprtc android demo application with callstats integration. See notes on how you will get
callstats library related credentials to run the application. 

***Without the callstats credentials the application will not run.***

### Prerequisites

- Minimum SDK version 21
- Library dependency 
    - WebRTC native 
    - Callstats.io android 
    - Autobanh websocket
    - JSON WebToken
    - Spongy castle
    - EventBus
 
### Provide credentials

- Get callstats ``` application id, key id, and token ```. You can hardcoded the token, or use third party server to generate the token and authentication. More 
details of authentication related integration can be found [https://www.callstats.io/blog/2016/09/29/3rd-party-auth-jwt-tokens](https://www.callstats.io/blog/2016/09/29/3rd-party-auth-jwt-tokens) 
- Apply changes in ```CsioSHIM.java```

### Build and run the android application 

- In the apprtc demo application give some room name ( for example csio-test-1234 ), and dial
- Open browser, and try to join the same room from here - [https://appr.tc/](https://appr.tc/)
- Watch your android logcat for sample events
- Or Check your Callstats.io dashboard for given WebRTC sessions


### Events implemented in demo
- Look into ```CSIOEvents.java``` class for sample events integration in the demo application. Please note that not all the events mentioned in [https://docs.callstats.io/restapi/](https://docs.callstats.io/restapi/) is implemented. Check the library document for further reference.
- Sample events covered in demo app :
    - [x] [Febric setup event ](https://docs.callstats.io/restapi/#tag/Fabric-Events)
        - [x] Fabric setup - Should call before create and answer or offer
        - [x] Fabric hold, and resume
    - [x] [ICE events ](https://docs.callstats.io/restapi/#tag/ICE-Events)
        - [x] ICE connection, gathering, signaling change
    - [x] [Media Events](https://docs.callstats.io/restapi/#tag/Media-Events)
        - [x] Mutes/unmutes
        - [x] Video play/pause
    - [x] [Special Event - application log ](https://docs.callstats.io/restapi/#tag/special-events)
        - [x] Application specific log. Can be anything that you find should be important for your application
        - [x] Example - Toggle between front/rear camera, when user connected/disconnected/join/leave apprtc room, etc.
        
        
### Integration process

1. Get [Callstats.io](https://www.callstats.io/) WebRTC analytic library for android from [https://github.com/callstats-io/callstats-android](https://github.com/callstats-io/callstats-android). It also includes the documentation 
on how we can integrate the library with any WebRTC application along with an integration with callstats own demo application. 

2. Include it in your gradle dependency. The apprtc demo is using ```0.1.1``` version.

    ```
        implementation "io.callstats:callstats:<version>"
    ```

3. Include Google WebRTC prebuild library from JCenter. [https://webrtc.org/native-code/android/](https://webrtc.org/native-code/android/). The apprtc demo is using ```1.0.25003``` version

    ```
        implementation 'org.webrtc:google-webrtc:<version>'
    ```

4. [ Optional ] Include [EventBus](https://github.com/greenrobot/EventBus). We are using ```3.1.1``` version

    ```
        implementation 'org.greenrobot:eventbus:<version>'
    ```

5. Create Callstats object 
    
    ```
        callstats = new Callstats(
            context,
            appID, // Application ID from Callstats
            localID, // current user ID
            deviceID, // unique device ID
            jwt, // jwt from server for authentication
            alias, // user alias
            clientVersion, // user version
            callstatsConfig) // Callstats config parameter 
    ``` 

6. Start callstats session with a unique identifier. For appRTC session monitoring demo we are using appRTC room number as unique identifier

    ```
        callstats.startSession(room)
    ```

7. Initiate fabric setup event. After fabric setup is complete, we will be able to send data

    ```
        // peerConnection - of type WebRTC peer connection
        // peerId - of type string - application specifc identifier for this peer connection
        callstats.addNewFabric(peerConnection, peerId)
    ```

8. Try to send some data to AppRTC session data. For example -
  
    - WebRTC ICE Connection state change
        
        ```
        callstats.reportEvent(peerId, new OnIceConnectionChange(newState))
        ```
    - WebRTC ICE gathering state change
    
        ```
        callstats.reportEvent(peerId, new OnIceGatheringChange(newState))
        ```
        
    - WebRTC Signaling state change 
        
        ```
        callstats.reportEvent(peerId, new OnSignalingChange(newState))
        ```
    - On Add new stream 
        ```
        callstats.reportEvent(peerId, OnAddStream.INSTANCE);
        ```
    - On audio mute, and unmute
        ```
        callstats.reportEvent(peerId, new OnAudio(isMuted, deviceId))
        ```
    - On video play, and pause
        ```
        callstats.reportEvent(peerId, new OnVideo(isPaused, deviceId))
        ```
    - On fabric hold
        ```
        callstats.reportEvent(peerId, OnHold.INSTANCE);
        ```
    - On fabric resume
        ```
        callstats.reportEvent(peerId, OnResume.INSTANCE);
        ```
    - To send custom message
        ```
        // message - of type string
        // level - of type io.callstats.LoggingLevel
        // loggingType - of type io.callstats.LoggingType
        callstats.log(message, level, loggingType);
        
        ```

9. When a AppRTC peer connection session ends, stop the session monitoring

    ```
        callstats.stopSession()
    ```

 