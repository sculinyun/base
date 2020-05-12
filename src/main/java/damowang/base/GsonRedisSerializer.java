package damowang.base;

import damowang.base.util.GsonUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;

/**
 * 描述:
 * fastjson序列化
 *
 * @author sculi
 * @create 2019-11-25 10:44
 */
public class GsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    public GsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Nullable
    @Override
    public byte[] serialize(@Nullable T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return GsonUtil.BeanToJson(t).getBytes(DEFAULT_CHARSET);
    }

    @Nullable
    @Override
    public T deserialize(@Nullable byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return GsonUtil.GsonToBean(str, clazz);
    }
}
