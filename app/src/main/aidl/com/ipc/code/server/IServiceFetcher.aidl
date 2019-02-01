// IServiceFetcher.aidl
package com.ipc.code.server;

interface IServiceFetcher {
    IBinder getService(String name);
    void addService(String name,in IBinder service);
    void removeService(String name);
}