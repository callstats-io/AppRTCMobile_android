### AppRTC Android demo application with Callstats library integration

##### Integration
- Requirement
    - WebRTC library 
    - Callstats.io library 
    - Autobanh Library
    - JSON WebToken library
    - Spongy castle library 
    - Minimum SDK version 21 
    
    
Load static library for Key/Token generation
```java
static {
    Security.insertProviderAt(new BouncyCastleProvider(), 1);
}

```
