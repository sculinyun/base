package damowang.base.exception;

import lombok.extern.slf4j.Slf4j;
import damowang.base.response.Result;
import damowang.base.response.ResultCode;
import damowang.base.response.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 描述:
 * 全局异常处理类
 *
 * @author sculi
 * @create 2019-11-27 11:30
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHander {

    @ExceptionHandler
    public Result processException(Exception e) {
        log.info("底层框架异常:" + errInfo(e));
        if (e instanceof BusinessException) {
            BusinessException ee = (BusinessException) e;
            if (ee.getCode() == ExceptionEnum.COMMON_ERROR_10002.getCode()) {
                return ResultUtils.buildLoginFailResult(ee.getMsg(), null);
            }
            return ResultUtils.buildFailResult(ee.getMsg(), null);
        } else {
            return ResultUtils.buildFailResult(ResultCode.SERVER_ERROR.getMsg(), null);
        }
    }

    private String errInfo(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }

}
