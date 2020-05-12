package damowang.base.response;

/**
 * 描述:
 * 对外响应包装类
 *
 * @author sculi
 * @create 2019-11-27 11:57
 */
public class ResultUtils {
    public static Result buildSuccessResult(String msg, Object data) {
        Result result = new Result();
        result.setData(data);
        result.setMsg(msg);
        result.setCode(ResultCode.OK.getCode());
        result.setStatus(ResultStatus.OK.getCode());
        return result;
    }

    public static Result buildSuccessResult(Object data) {
        Result result = new Result();
        result.setData(data);
        result.setCode(ResultCode.OK.getCode());
        result.setStatus(ResultStatus.OK.getCode());
        return result;
    }

    public static Result buildFailResult(String msg, Object data) {
        Result result = new Result();
        result.setMsg(msg);
        result.setData(data);
        result.setCode(ResultCode.OK.getCode());
        result.setStatus(ResultStatus.NOT_OK.getCode());
        return result;
    }

    public static Result buildLoginFailResult(String msg, Object data) {
        Result result = new Result();
        result.setMsg(msg);
        result.setData(data);
        result.setCode(ResultCode.OK.getCode());
        result.setStatus(ResultStatus.NOT_LOGIN.getCode());
        return result;
    }

    public static Result buildFailResult(String msg) {
        Result result = new Result();
        result.setMsg(msg);
        result.setData(null);
        result.setCode(ResultCode.OK.getCode());
        result.setStatus(ResultStatus.NOT_OK.getCode());
        return result;
    }
}
