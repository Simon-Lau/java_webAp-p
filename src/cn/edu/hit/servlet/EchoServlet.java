package cn.edu.hit.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

public class EchoServlet extends WebSocketServlet
{
    private static final long serialVersionUID = -4104682919545889813L;
    
    private Logger logger = Logger.getLogger(EchoServlet.class.getName());
    
    private static List<MyMessageInbound> socketList = new ArrayList<MyMessageInbound>();
    
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request)
    {
        // TODO Auto-generated method stub
        logger.info("receive ws request");
        return new MyMessageInbound();
    }
    
    public static synchronized List<MyMessageInbound> getSocketList()
    {
        return socketList;
    }
    
}