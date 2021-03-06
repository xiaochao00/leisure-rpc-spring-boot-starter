package child.leisure.rpc.common.protocol;

import java.io.*;

/**
 * 序列化消息协议
 * Java自身的序列化机制性能不好，可采用其他序列化方式
 *
 * @author shichao
 * @since 1.0.0
 * 2021/3/6 16:23
 */
public class JavaSerializeMessageProtocol implements MessageProtocol {
    private byte[] serialize(Object obj) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(obj);
        return outputStream.toByteArray();

    }

    private Object deserialize(byte[] data) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return objectInputStream.readObject();
    }


    @Override
    public byte[] marshallingRequest(LeisureRequest request) throws Exception {
        return this.serialize(request);
    }

    @Override
    public LeisureRequest unmarshallingRequest(byte[] data) throws Exception {
        return (LeisureRequest) deserialize(data);
    }

    @Override
    public byte[] marshallingResponse(LeisureResponse response) throws Exception {
        return this.serialize(response);
    }

    @Override
    public LeisureResponse unmarshallingResponse(byte[] data) throws Exception {
        return (LeisureResponse) this.deserialize(data);
    }
}
