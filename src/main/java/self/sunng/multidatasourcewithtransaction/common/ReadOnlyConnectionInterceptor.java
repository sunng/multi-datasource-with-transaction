package self.sunng.multidatasourcewithtransaction.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Created by sunxiaodong on 2016/11/12.
 */
@Aspect
@Component
@Slf4j
public class ReadOnlyConnectionInterceptor implements Ordered {

    @Around("@annotation(readOnlyConnection)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, ReadOnlyConnection readOnlyConnection) throws Throwable {
        try {
            log.info("set database connection to read only");
            DbContextHolder.setDbType(DbContextHolder.DbType.SLAVE);
            return proceedingJoinPoint.proceed();
        } finally {
            DbContextHolder.clearDbType();
            log.info("restore database connection");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
