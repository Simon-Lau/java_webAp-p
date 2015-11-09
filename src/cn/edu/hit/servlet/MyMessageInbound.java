package cn.edu.hit.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.json.JSONArray;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.struts2.interceptor.SessionAware;

import cn.edu.hit.dao.RecordDAO;
import cn.edu.hit.dao.UserDAO;

public class MyMessageInbound extends MessageInbound
{
    private String userId = null;
    private String friendId = null;
    private Logger logger = Logger.getLogger(MyMessageInbound.class.getName());
    
	public String getUserId(){
    	return userId;
    }
    
	public String getFriendId(){
		return friendId;
	}
    protected void onOpen(WsOutbound outbound)
    {
        super.onOpen(outbound);
        //userIdName = outbound.hashCode();
        EchoServlet.getSocketList().add(this);
        logger.info("Server onOpen");
    }
    
    protected void onClose(int status)
    {
        EchoServlet.getSocketList().remove(this);
        super.onClose(status);
        logger.info("Server onClose");
    }
    
    // 二进制流，用户传输文件或是图片
    @Override
    protected void onBinaryMessage(ByteBuffer buffer)
        throws IOException
    {
        logger.info("Binary Message Receive: " + buffer.remaining());
    }
    
    @Override
    protected void onTextMessage(CharBuffer buffer)
        throws IOException
    {
        String msgOriginal = buffer.toString();
        
        String[] data  = msgOriginal.split("#");
        userId = data[0];
        friendId = data[1];
        String textMsg = data[2];
        
        String nikeName = getUserName(userId);
        // 将字符数组包装到缓冲区中
        // 给定的字符数组将支持新缓冲区；即缓冲区修改将导致数组修改，反之亦然
        
        String countMsg = EchoServlet.getSocketList().size() + "人同时在线";
        logger.info("Server onTextMessage: " + countMsg + nikeName + ":" + textMsg);
        
        String msg1 = nikeName + ": " + textMsg;
        String msg2 = nikeName + ": " + textMsg;
        System.out.println("textMsg="+textMsg);
        //注册friendId和UserId
        if(textMsg.equals("$")){
        	System.out.println("注册friendId,userId");
        }else{
	        for (MyMessageInbound messageInbound : EchoServlet.getSocketList())
	        {
	            CharBuffer msgBuffer1 = CharBuffer.wrap(msg1);
	            CharBuffer msgBuffer2 = CharBuffer.wrap(msg2);
	            WsOutbound outbound = messageInbound.getWsOutbound();
	            System.out.println("friendID="+friendId);
	            System.out.println("userId="+messageInbound.getUserId());
	            if (friendId != null &&friendId.equals(messageInbound.getUserId()))
	            {
	                outbound.writeTextMessage(msgBuffer1);
	            }
	            //给自己发一份信息
	            //给特定好友发一份信息
	            else if(messageInbound.getUserId() == this.userId)
	            {
	                outbound.writeTextMessage(msgBuffer2);
	                addRecord(userId,friendId,textMsg);
	            }
	            outbound.flush();
	        }
        }
    }
    
    public String getUserName(String id){
    	JSONArray jsonArray = new UserDAO().getUserById(id);	
		String username = jsonArray.getJSONObject(0).getString("username");
		return username;
    }
    
    public void addRecord(String uid,String fid,String content){
    	if(new RecordDAO().insertRecord(uid, fid, content) == 1){
    		System.out.println("成功插入Record");
    	}else{
    		System.out.println("插入record失败");
    	}
    }
    
    
}