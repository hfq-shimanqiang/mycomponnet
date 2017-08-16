package com.huifenqi.jedi.kryo;

import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.huifenqi.jedi.kryo.ext.KryoFactory;
import com.huifenqi.jedi.kryo.ext.KryoInput;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by t3tiger on 2017/7/5.
 */
public class KryoObjectInput implements ObjectInput {
    private Kryo kryo;
    private KryoInput input;

    public KryoObjectInput(InputStream inputStream) {
        this.input = new KryoInput(inputStream);
        kryo = KryoFactory.createOrGetKryo();
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        try {
            if (kryo == null) {
                kryo = KryoFactory.createOrGetKryo();
            }
            return kryo.readClassAndObject(input);
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return (T) readObject();
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        return readObject(cls);
    }

    @Override
    public boolean readBool() throws IOException {
        try {
            return input.readBoolean();
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }

    @Override
    public byte readByte() throws IOException {
        try {
            return input.readByte();
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }

    @Override
    public short readShort() throws IOException {
        try {
            return input.readShort();
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }

    @Override
    public int readInt() throws IOException {
        try {
            return input.readInt();
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }

    @Override
    public long readLong() throws IOException {
        try {
            return input.readLong();
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }

    @Override
    public float readFloat() throws IOException {
        try {
            return input.readFloat();
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }

    @Override
    public double readDouble() throws IOException {
        try {
            return input.readDouble();
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }

    @Override
    public String readUTF() throws IOException {
        try {
            return input.readString();
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }

    @Override
    public byte[] readBytes() throws IOException {
        try {
            int len = input.readInt();
            if (len < 0) {
                return null;
            } else if (len == 0) {
                return new byte[]{};
            } else {
                return input.readBytes(len);
            }
        } catch (KryoException e) {
            throw new IOException(e);
        }
    }
}
