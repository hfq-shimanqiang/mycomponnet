package com.huifenqi.jedi.kryo;

import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.esotericsoftware.kryo.Kryo;
import com.huifenqi.jedi.kryo.ext.KryoFactory;
import com.huifenqi.jedi.kryo.ext.KryoOutput;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by t3tiger on 2017/7/5.
 */
public class KryoObjectOutput implements ObjectOutput {
    private Kryo kryo;
    private KryoOutput output;

    public KryoObjectOutput(OutputStream outputStream) {
        this.output = new KryoOutput(outputStream);
        kryo = KryoFactory.createOrGetKryo();
    }

    public void writeBool(boolean v) throws IOException {
        output.writeBoolean(v);
    }

    public void writeByte(byte v) throws IOException {
        output.writeByte(v);
    }

    public void writeShort(short v) throws IOException {
        output.writeShort(v);
    }

    public void writeInt(int v) throws IOException {
        output.writeInt(v);
    }

    public void writeLong(long v) throws IOException {
        output.writeLong(v);
    }

    public void writeFloat(float v) throws IOException {
        output.writeFloat(v);
    }

    public void writeDouble(double v) throws IOException {
        output.writeDouble(v);
    }

    public void writeBytes(byte[] v) throws IOException {
        if (v == null) {
            output.writeInt(-1);
        } else {
            writeBytes(v, 0, v.length);
        }
    }

    public void writeBytes(byte[] v, int off, int len) throws IOException {
        if (v == null) {
            output.writeInt(-1);
        } else {
            output.writeInt(len);
            output.write(v, off, len);
        }
    }

    public void writeUTF(String v) throws IOException {
        output.writeString(v);
    }

    public void writeObject(Object v) throws IOException {
        if (kryo == null) {
            kryo = KryoFactory.createOrGetKryo();
        }
        kryo.writeClassAndObject(output, v);
    }

    public void flushBuffer() throws IOException {
        output.flush();
    }

    public void cleanup() {
        if (kryo != null) {
            kryo = null;
        }
    }
}
