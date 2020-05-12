package damowang.base.response;

/**
 * 描述:
 * 对外包装结果
 *
 * @author sculi
 * @create 2019-11-27 10:51
 */
public class Result {
    private String msg;
    private int status;
    private int code;
    private Object data;

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", status=" + status +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
