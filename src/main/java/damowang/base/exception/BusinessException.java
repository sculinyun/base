package damowang.base.exception;

import damowang.base.response.ResultCode;

/**
 * 描述:
 * 自定义业务异常
 *
 * @author sculi
 * @create 2019-11-27 14:25
 */
public class BusinessException extends RuntimeException {
    private int code;
    private String msg;

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ExceptionEnum expenum) {
        this.code = expenum.getCode();
        this.msg = expenum.getMsg();
    }

    public BusinessException(String msg) {
        this.code = ResultCode.SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public BusinessException(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public BusinessException() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
