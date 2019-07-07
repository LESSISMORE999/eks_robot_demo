package com.eks.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data//注解在类上;提供类所有非static且非final属性的get和set方法,final属性只提供get方法,此外还提供了equals、canEqual、hashCode、toString 方法
@Accessors(chain = true)//chain=boolean值，默认false。如果设置为true，setter返回的是此对象，方便链式调用方法
public class MessagePojo {
    private String senderOrChatRoomId;
    private String chatRoomSenderId;
    private String messageString;
    //来源类型,0表示收到,1表示发出
    private String messageSourceType;
    //文本、图片等类型的标识
    private String messageType;
}
