package child.leisure.rpc.server.register;

/**
 * 服务持有对象，保存具体的服务信息
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 17:10
 */
public class ServiceObject {
    /**
     * 服务名称
     */
    private String name;

    /**
     * 具体服务的类
     */
    private Class<?> clazz;

    /**
     * 具体服务的实现对象
     */
    private Object obj;

    public ServiceObject(String name, Class<?> clazz, Object obj) {
        this.name = name;
        this.clazz = clazz;
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "ServiceObject{" +
                "'name':'" + name + '\'' +
                ", 'clazz':" + clazz +
                ", 'obj':" + obj +
                '}';
    }
}
