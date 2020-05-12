package damowang.base.response;

public enum ResultCode {
    OK(200, "正常"),
    NOT_FOUND(404, "请求不存在"),
    UNAUTHORIZED(401, "权限不足"),
    FORBIDDEN(403, "禁止访问"),
    NOT_ALLOWED(405, "方法不允许调用"),
    SERVER_ERROR(500, "网络开小差"),
    GATEWAY_ERROR(502, "错误网关"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    BAD_REQUEST(400, "请求无效");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }
}
