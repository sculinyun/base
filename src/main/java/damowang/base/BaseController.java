package damowang.base;

import damowang.base.response.ResultUtils;
import damowang.base.constant.BaseConst;
import damowang.base.response.Result;
import net.minidev.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 描述:
 * restful的一些接口处理
 *
 * @author sculi
 * @create 2019-12-04 14:53
 */
public class BaseController {

    public Map tokenMap() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object tokenMap = request.getAttribute(BaseConst.TOKEN_MAP);
        if (null == tokenMap) {
            return null;
        } else {
            return (Map) tokenMap;
        }
    }

    public Long getUserId(){
        Map map=tokenMap();
        JSONObject object=(JSONObject)map.get("data");
        return (Long) object.get("userId");
    }

    public Result bulidSuccessResult(Object data) {
        return ResultUtils.buildSuccessResult(data);
    }

    public Result bulidFailResult(String msg) {
        return ResultUtils.buildFailResult(msg);
    }
}
