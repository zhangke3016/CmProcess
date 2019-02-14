# CmProcess [![platform](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)

A more convenient solution for cross-process communication in Android.

## Use

1. Init in your application:
```
@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        VCore.init(base);
    }
```
2. Register/Unregister your service at any time, anywhere
```
  VCore.getCore().registerService(IPayManager.class, this);
  VCore.getCore().unregisterService(IPayManager.class);
```
3. Get services at any time, anywhere, any process
```
  IPayManager service = VCore.getCore().getService(IPayManager.class);
```
