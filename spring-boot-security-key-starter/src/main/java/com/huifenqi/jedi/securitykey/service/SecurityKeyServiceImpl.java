package com.huifenqi.jedi.securitykey.service;

import com.huifenqi.jedi.securitykey.MyConf;
import com.huifenqi.jedi.securitykey.shell.ShellClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityKeyServiceImpl implements SecurityKeyService {
    private static final Map<String, byte[]> cacheMap = new ConcurrentHashMap<>();

    private MyConf myConf;

    public SecurityKeyServiceImpl(MyConf myConf) {
        this.myConf = myConf;
    }

    @Override
    public byte[] loadKey(String path, boolean forceLoad) {
        byte[] cacheData = cacheMap.get(path);
        if (forceLoad || null == cacheData || cacheData.length == 0) {
            String host = myConf.getHost();
            int port = myConf.getPort();
            String userName = myConf.getUserName();
            String privateKey = myConf.getPrivateKey();

            ShellClient client = new ShellClient(host, port, userName, privateKey);
            try {
                client.connect();
                cacheData = client.execSftpDownload2ByteArray(path, host, port, userName, privateKey);
                cacheMap.put(path, cacheData);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                client.close();
            }
        }

        return cacheData;
    }
}
