# AppRTC Android demo 

AppRTC demo application with Callstats.io android library integration

## Getting Started

These instructions will help you run the apprtc android demo application with csio integration. See notes on how you will get
callstarts library related credentials to run the application. 

***Without the csio credentials the application will not run.***

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
- Or Check your Callstats dashboard for given WebRTC sessions


### Events implemented in demo
- Look into ```CSIOEvents.java``` class for sample events integration in the demo application. Please note that not all the events mentioned in [https://docs.callstats.io/restapi/](https://docs.callstats.io/restapi/) is implemented. Check the library document for further reference.
- Sample events covered in demo app :
    - [ x ] [Febric setup event ](https://docs.callstats.io/restapi/#tag/Fabric-Events)
        - [ x ] Fabric setup - Should call before create and answer or offer
        - [ x ] Fabric hold, and resume
    - [ x ] [ICE events ](https://docs.callstats.io/restapi/#tag/ICE-Events)
        - [ x ] ICE connection, gathering, signaling change
    - [ x ] [Media Events](https://docs.callstats.io/restapi/#tag/Media-Events)
        - [ x ] Mutes/unmutes
        - [ x ] Video play/pause
    - [ x ] [Special Event - application log ](https://docs.callstats.io/restapi/#tag/special-events)
        - [ x ] Application specific log. Can be anything that you find should be important for your application
        - [ x ] Example - Toggle between front/rear camera, when user connected/disconnected/join/leave apprtc room, etc.
    
 