package damowang.base.response;

import damowang.base.util.GsonUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 描述:
 * 返回结果包装类
 *
 * @author sculi
 * @create 2019-11-28 18:47
 */
//@RestControllerAdvice
public class ResultWrapper implements ResponseBodyAdvice<Object> {

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (MediaType.IMAGE_JPEG.getType().equalsIgnoreCase(selectedContentType.getType())) {
            return body;
        }
        Result result = ResultUtils.buildSuccessResult(body);
        if (body == null) {
            return GsonUtil.BeanToJson(result);
        }
        if (body instanceof String) {
            return GsonUtil.BeanToJson(result);
        }
        return result;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }


}
