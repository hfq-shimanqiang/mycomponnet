package com.huifenqi.jedi.kryo.bck;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.serializers.*;
import com.huifenqi.jedi.kryo.ext.MyKryo;
import de.javakaffee.kryoserializers.*;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.lang.reflect.InvocationHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Created by t3tiger on 2017/7/5.
 */
public abstract class KryoFactory2 {

    public static final Kryo createOrGetKryo() {
        return kryoThreadLocal.get();
    }

    private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new MyKryo();
            /**
             * Objenesis 提供 StdInstantiatorStrategy，它使用JVM 特定的 API 来创建类的实例，而不会调用任何构造方法。
             */
            //kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
            /**
             * 在许多情况下，您可能希望有这样的策略：Kryo 首先尝试使用无参构造方法，如果尝试失败，
             * 再尝试使用 StdInstantiatorStrategy 作为后备方案，因为后备方案不需要调用任何构造方法。
             */
            kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

            /**
             * CompatibleFieldSerializer 扩展了 FieldSerializer 以提供向前和向后兼容性，
             * 这意味着可以添加或删除字段，而不会使先前的序列化字节无效。
             */
            kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
            /**
             * 4.0.0的api
             */
            kryo.getFieldSerializerConfig().setCachedFieldNameStrategy(FieldSerializer.CachedFieldNameStrategy.EXTENDED);

            /**
             * 设置引用
             */
            kryo.setReferences(true);
            /**
             * 当 Kryo#setRegistrationRequired 设置为true，可在遇到任何未注册的类时抛出异常。这能阻止应用程序使用类名字符串来序列化。
             */
            kryo.setRegistrationRequired(false);

            kryo.register(Arrays.asList("").getClass(), new ArraysAsListSerializer());
            kryo.register(GregorianCalendar.class, new GregorianCalendarSerializer());
            kryo.register(InvocationHandler.class, new JdkProxySerializer());
            kryo.register(BigDecimal.class, new DefaultSerializers.BigDecimalSerializer());
            kryo.register(BigInteger.class, new DefaultSerializers.BigIntegerSerializer());
            kryo.register(Pattern.class, new RegexSerializer());
            kryo.register(BitSet.class, new BitSetSerializer());
            kryo.register(URI.class, new URISerializer());
            kryo.register(UUID.class, new UUIDSerializer());
            UnmodifiableCollectionsSerializer.registerSerializers(kryo);
            SynchronizedCollectionsSerializer.registerSerializers(kryo);

            /**
             * start
             * add by smq
             */
            kryo.register(Locale.class, new DefaultSerializers.LocaleSerializer());
            kryo.register(short[].class, new DefaultSerializers.ShortSerializer());
            kryo.register(Collection.class, new CollectionSerializer());
            kryo.register(StringBuilder.class, new DefaultSerializers.StringBuilderSerializer());
            kryo.register(BitSet.class, new BitSetSerializer());
            kryo.register(UUID.class, new UUIDSerializer());
            kryo.register(float[].class, new DefaultSerializers.FloatSerializer());
            kryo.register(Double.class, new DefaultSerializers.DoubleSerializer());
            kryo.register(KryoSerializable.class, new DefaultSerializers.KryoSerializableSerializer());
            kryo.register(TimeZone.class, new DefaultSerializers.TimeZoneSerializer());
            kryo.register(URI.class, new URISerializer());
            kryo.register(byte[].class, new DefaultSerializers.ByteSerializer());
            kryo.register(Charset.class, new DefaultSerializers.CharsetSerializer());
            kryo.register(EnumSet.class, new DefaultSerializers.EnumSetSerializer());
            kryo.register(Date.class, new DefaultSerializers.DateSerializer());
            kryo.register(boolean[].class, new DefaultSerializers.BooleanSerializer());
            kryo.register(Float.class, new DefaultSerializers.FloatSerializer());
            kryo.register(Map.class, new MapSerializer());
            kryo.register(Calendar.class, new DefaultSerializers.CalendarSerializer());

            /**
             * end
             */

            // now just added some very common classes
            // TODO optimization

            kryo.register(HashMap.class);
            kryo.register(ArrayList.class);
            kryo.register(LinkedList.class);
            kryo.register(HashSet.class);
            kryo.register(TreeSet.class);
            kryo.register(Hashtable.class);
            kryo.register(Date.class);
            kryo.register(Calendar.class);
            kryo.register(ConcurrentHashMap.class);
            kryo.register(SimpleDateFormat.class);
            kryo.register(GregorianCalendar.class);
            kryo.register(Vector.class);
            kryo.register(BitSet.class);
            kryo.register(StringBuffer.class);
            kryo.register(StringBuilder.class);
            kryo.register(Object.class);
            kryo.register(Object[].class);
            kryo.register(String[].class);
            kryo.register(byte[].class);
            kryo.register(char[].class);
            kryo.register(int[].class);
            kryo.register(float[].class);
            kryo.register(double[].class);


            return kryo;
        }
    };


    /**
     kryo.register(HashMap.class, 100);
     kryo.register(ArrayList.class, 101);
     kryo.register(LinkedList.class, 102);
     kryo.register(HashSet.class, 103);
     kryo.register(TreeSet.class, 104);
     kryo.register(Hashtable.class, 105);
     kryo.register(Date.class, 106);
     kryo.register(Calendar.class, 107);
     kryo.register(ConcurrentHashMap.class, 108);
     kryo.register(SimpleDateFormat.class, 109);
     kryo.register(GregorianCalendar.class, 110);
     kryo.register(Vector.class, 111);
     kryo.register(BitSet.class, 112);
     kryo.register(StringBuffer.class, 113);
     kryo.register(StringBuilder.class, 114);
     kryo.register(Object.class, 115);
     kryo.register(Object[].class, 116);
     kryo.register(String[].class, 117);
     kryo.register(byte[].class, 118);
     kryo.register(char[].class, 119);
     kryo.register(int[].class, 120);
     kryo.register(float[].class, 121);
     kryo.register(double[].class, 122);
     */

}
