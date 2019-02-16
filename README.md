# CmProcess [![platform](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)

A more convenient solution for cross-process communication in Android.No need to use aidl.

[中文文档](CHINESE_README.md)

## Add dependency

##### 1. Add it in your root build.gradle at the end of repositories:
```groovy
    allprojects {
                repositories {
                    ......
                    maven { url 'https://jitpack.io' }
        }
    }
```
##### 2. Add the dependency:
```groovy
    dependencies {
        implementation 'com.github.zhangke3016:CmProcess:1.0.3'
    }
```

## Use

##### 1. Init in your application:
```java
  @Override
  protected void attachBaseContext(Context base) {
      super.attachBaseContext(base);
      VCore.init(base);
  }
```
##### 2. Define interfaces and implement ,the interface parameter type must be a primitive data type or a serializable/Parcelable type.eg:
```java
  public interface IPayManager {
     String pay(int count);
     //if use remote service, the callback interface must be the provided 'IPCCallback`
     String pay(int count, IPCCallback callBack);
  }
```
##### 3. Register/Unregister your service at any time, anywhere
```java
  //register local + remote service
  VCore.getCore().registerService(IPayManager.class, this);
  //unregister local + remote service
  VCore.getCore().unregisterService(IPayManager.class);
  //register local service
  VCore.getCore().registerLocalService(IPayManager.class, this);
  //unregister local service
  VCore.getCore().unregisterLocalService(IPayManager.class);
```
##### 4. Get services at any time, anywhere, any process
```java
  IPayManager service = VCore.getCore().getService(IPayManager.class);
  //get local service
  //IPayManager service = VCore.getCore().getLocalService(IPayManager.class);
  //note service may be null if no service found
  if(service != null){
    //Synchronous call.
    String message = service.pay(5000);
    //Asynchronous call. note: use remote service, the callback interface must be the provided 'IPCCallback`
    //recommended use 'BaseCallback'
    service.pay(5000, new BaseCallback() {
         @Override
         public void onSucceed(Bundle result) {
             //Main thread
         }
    
         @Override
         public void onFailed(String reason) {
             //Main thread
         }
     });
     //or
     service.pay(5000, new IPCCallback.Stub() {
          @Override
          public void onSuccess(Bundle bundle) throws RemoteException {
              // binder thread
          }
          
          @Override
          public void onFail(String s) throws RemoteException {
          
          }
     })
  }
```
##### 5. Event subscription and post
```java
    VCore.getCore().subscribe("key", new EventCallback() {
        @Override
        public void onEventCallBack(Bundle event) {
            //main thread
            String name = event.getString("name");
            Log.e("TAG", "onEventCallBack: " + name + " " + (Looper.myLooper() == Looper.getMainLooper()));
        }
    });
    
    //post:
    Bundle bundle = new Bundle();
    bundle.putString("name", "DoDo");
    VCore.getCore().post("key",bundle);
    
    //unsubscribe 
    VCore.getCore().unsubscribe("key");
```



