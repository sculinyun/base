package damowang.base.response;

public enum ResultStatus {
    OK(0, "成功"),
    NOT_OK(-1, "失败"),
    NOT_LOGIN(-2, "登陆过期");
    private int code;
    private String msg;

    ResultStatus(int code, String msg) {
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
