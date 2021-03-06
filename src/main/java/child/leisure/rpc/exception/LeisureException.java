package child.leisure.rpc.exception;

import org.omg.SendingContext.RunTime;

/**
 * 自定义异常类
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 15:51
 */
public class LeisureException extends RuntimeException {
    public LeisureException(String msg) {
        super(msg);
    }
}
