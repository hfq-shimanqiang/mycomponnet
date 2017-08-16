package com.huifenqi.jedi.securitykey.service;

public interface SecurityKeyService {

    byte[] loadKey(String path, boolean forceLoad);
}
