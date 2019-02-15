// IServiceFetcher.aidl
package com.cmprocess.ipc.server;

// Declare any non-default types here with import statements

interface IServiceFetcher {
    android.os.IBinder getService(java.lang.String name);
    void addService(java.lang.String name, android.os.IBinder service);
    void removeService(java.lang.String name);
}
