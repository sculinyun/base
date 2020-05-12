package damowang.base.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * jwt工具类 主要是加密和解密
 *
 * @author sculi
 * @create 2019-11-27 10:25
 */
public class JwtUtils {
    /**
     * 1.创建一个32-byte的密匙
     */

    private static final byte[] secret = "lykj2019wodiangasfdjsikolkjiko==".getBytes();

    public static String createDefaultToken(Map<String, Object> map){
        map.put("sta", System.currentTimeMillis());
        //过期时间
        map.put("exp", System.currentTimeMillis()+86400000L);
        map.put("iss","lykj");
        return creatToken(map);
    }

    //生成一个token
    public static String creatToken(Map<String, Object> payloadMap) {
        try{
            //3.先建立一个头部Header
            /**
             * JWSHeader参数：1.加密算法法则,2.类型，3.。。。。。。。
             * 一般只需要传入加密算法法则就可以。
             * 这里则采用HS256
             *
             * JWSAlgorithm类里面有所有的加密算法法则，直接调用。
             */
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
            //建立一个载荷Payload
            Payload payload = new Payload(new JSONObject(payloadMap));
            //将头部和载荷结合在一起
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            //建立一个密匙
            JWSSigner jwsSigner = new MACSigner(secret);
            //签名
            jwsObject.sign(jwsSigner);
            //生成token
            return jwsObject.serialize();
        }catch (Exception e){
            return null;
        }
    }

    //解析一个token
    public static Map<String, Object> parseToken(String token){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            //解析token
            JWSObject jwsObject = JWSObject.parse(token);
            //获取到载荷
            Payload payload = jwsObject.getPayload();
            //建立一个解锁密匙
            JWSVerifier jwsVerifier = new MACVerifier(secret);
            //判断token
            if (jwsObject.verify(jwsVerifier)) {
                resultMap.put("status", 0);
                //载荷的数据解析成json对象。
                JSONObject jsonObject = payload.toJSONObject();
                resultMap.put("data", jsonObject);
                //判断token是否过期
                if (jsonObject.containsKey("exp")) {
                    Long expTime = Long.valueOf(jsonObject.get("exp").toString());
                    Long nowTime = System.currentTimeMillis();
                    //判断是否过期
                    if (nowTime > expTime) {
                        //已经过期
                        resultMap.clear();
                        resultMap.put("status", 2);
                    }
                }
            } else {
                resultMap.put("status", 1);
            }
        }catch (Exception e){
            resultMap.put("status", -1);
        }
        return resultMap;
    }
}
