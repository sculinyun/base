package damowang.base.aop;

import damowang.base.annotation.Login;
import damowang.base.exception.BusinessException;
import damowang.base.exception.ExceptionEnum;
import damowang.base.jwt.JwtUtils;
import damowang.base.util.GsonUtil;
import damowang.base.constant.BaseConst;
import damowang.base.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 描述:
 * 日志的aop拦截
 *
 * @author sculi
 * @create 2019-11-25 16:28
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class WebAop {
    //免登录白名单
    private static final String[] NOT_LOGIN_URL = {"/login"};
    ThreadLocal<Long> costTime = new ThreadLocal<>();

    /**
     * 指定切点
     * 匹配 com.linyun.step01.controller包及其子包下的所有类的所有方法
     * 包含Restcontroller和controller
     */
    @Pointcut("execution(public * com.damowang.*.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * 环绕通知，方法执行前后调用
     *
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        costTime.set(System.currentTimeMillis());
        printRequest(joinPoint);
    }


    /**
     * 处理完请求返回内容
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("方法的返回值 : " + GsonUtil.BeanToJson(ret));
        log.info("接口响应时间 : " + (System.currentTimeMillis() - costTime.get()) + " ms");
    }

    /**
     * 后置异常通知
     *
     * @param jp
     */
    @AfterThrowing("webLog()")
    public void throwsExp(JoinPoint jp) {
        log.info("方法异常时执行.....");
    }

    /**
     * 处理jwt登陆这块的业务逻辑
     */
    void parseLogin(HttpServletRequest request) {
        //等到请求头信息authorization信息
        String authHeader = request.getHeader("authorization");
        String url = request.getRequestURI();
        //登陆接口不拦截 或者其他接口 白名单接口
        for (String urlStr : NOT_LOGIN_URL) {
            if (!StringUtils.isEmpty(url) && url.contains(urlStr)) {
                return;
            }
        }
        //验证jwt逻辑
        if (StringUtils.isEmpty(authHeader)) {
            throw new BusinessException(ExceptionEnum.COMMON_ERROR_10001);
        } else {
            Map tokenMap = JwtUtils.parseToken(authHeader);
            int status=(int)tokenMap.get("status");
            if(status!=0){
                throw new BusinessException(ExceptionEnum.COMMON_ERROR_10002);
            }
            request.setAttribute(BaseConst.TOKEN_MAP, tokenMap);
        }
    }

    /*
      拦截404的业务逻辑
     */
    void checkMethod(String methodName) {
        if (!StringUtils.isEmpty(methodName) && methodName.equals("handleError")) {
            return;
        }
    }

    /*
     打印日志请求参数,并且初步校验
    */
    void printRequest(JoinPoint joinPoint) {
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        log.info("方法：" + signature.getName());
        checkMethod(signature.getName());
        //AOP代理类的名字
        log.info("方法所在包:" + signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
        signature.getDeclaringType();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        //返回直接存在于此元素上的所有注解,不包含父类的注解
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            //如果需要登录的话,走jwt验证逻辑
            if (annotation instanceof Login) {
                parseLogin(req);
            }
        }
        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                continue;
            }
            arguments[i] = args[i];
        }
        log.info("参数值ARGS : " + GsonUtil.BeanToJson(arguments));

        // 记录下请求内容
        log.info("请求URL : " + req.getRequestURL().toString());
        log.info("HTTP_METHOD : " + req.getMethod());
        log.info("IP : " + IpUtil.getIpAddr(req));
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }
}
