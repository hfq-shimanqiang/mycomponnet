package com.huifenqi.jedi.kryo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by t3tiger on 2017/7/5.
 */
public class KryoSerialization implements Serialization {
    @Override
    public byte getContentTypeId() {
        return 15;
    }

    @Override
    public String getContentType() {
        return "x-application/kryo2";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream outputStream) throws IOException {
        return new KryoObjectOutput(outputStream);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream inputStream) throws IOException {
        return new KryoObjectInput(inputStream);
    }
}
