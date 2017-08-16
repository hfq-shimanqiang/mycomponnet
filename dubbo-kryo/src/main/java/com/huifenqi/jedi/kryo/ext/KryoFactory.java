package com.huifenqi.jedi.kryo.ext;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * Created by t3tiger on 2017/7/5.
 */
public abstract class KryoFactory {

    public static Kryo createOrGetKryo() {
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


            return kryo;
        }
    };

}
