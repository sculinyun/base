package damowang.base.exception;

public enum ExceptionEnum {
    //10000-10100 系统公共错误 其他类似100100开始以各模块开始
    COMMON_ERROR_10000(10000, "错误1"),
    COMMON_ERROR_10001(10001, "token不存在"),
    COMMON_ERROR_10002(10002, "token错误"),
    ORDER_ERROR_10100(10100, "订单错误1");

    private int code;
    private String msg;

    ExceptionEnum(int code, String msg) {
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
