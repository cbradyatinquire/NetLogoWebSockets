package websocket;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;

public class WebSockServer extends Server {
	
	boolean _verbose = true;

    WebSocket _websocket;
    SelectChannelConnector _connector;
    WebSocketHandler _wsHandler;
    ResourceHandler _rHandler;
    ConcurrentLinkedQueue<MyWebSocket> _broadcast = new ConcurrentLinkedQueue<MyWebSocket>();
    
    public void printHandlerCount()
    {
    	System.err.println( "I NOW HAVE " + getHandlers().length + " HANDLERS");
    }

    public WebSockServer(int port)
    {
    	System.err.println("GOT TO CONSTRUCTOR");
        _connector = new SelectChannelConnector();
        _connector.setPort(port);

        addConnector(_connector);
        System.err.println("ADDED CONNECTOR");
        _wsHandler = new WebSocketHandler()
        {
        	@Override
            public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol)
            {
               
                _websocket = new MyWebSocket(); 
                System.err.println("Created WEBSOCKET");

                return _websocket;
            }

        };

        setHandler(_wsHandler);
        
       // _rHandler=new ResourceHandler();
       // _rHandler.setDirectoriesListed(true);
//        _rHandler.setResourceBase("src/test/webapp");   //doesn't exist -- change if i need resources
       // _wsHandler.setHandler(_rHandler);
        
        _wsHandler.setHandler(  new HelloWorld() );
   
    }
    
    
    class MyWebSocket implements WebSocket, WebSocket.OnFrame, WebSocket.OnBinaryMessage, WebSocket.OnTextMessage, WebSocket.OnControl
    {
        protected FrameConnection _connection;
        
        public FrameConnection getConnection()
        {
            return _connection;
        }
        
        public void onOpen(Connection connection)
        {
            if (_verbose)
                System.err.printf("%s#onOpen %s\n",this.getClass().getSimpleName(),connection);
        }
        
        public void onHandshake(FrameConnection connection)
        {
            if (_verbose)
                System.err.printf("%s#onHandshake %s %s\n",this.getClass().getSimpleName(),connection,connection.getClass().getSimpleName());
            _connection = connection;
        }

        public void onClose(int code,String message)
        {
            if (_verbose)
                System.err.printf("%s#onDisonnect %d %s\n",this.getClass().getSimpleName(),code,message);
        }
        
        public boolean onFrame(byte flags, byte opcode, byte[] data, int offset, int length)
        {            
            if (_verbose)
                System.err.printf("%s#onFrame %s|%s %s\n",this.getClass().getSimpleName(),TypeUtil.toHexString(flags),TypeUtil.toHexString(opcode),TypeUtil.toHexString(data,offset,length));
            return false;
        }

        public boolean onControl(byte controlCode, byte[] data, int offset, int length)
        {
            if (_verbose)
                System.err.printf("%s#onControl  %s %s\n",this.getClass().getSimpleName(),TypeUtil.toHexString(controlCode),TypeUtil.toHexString(data,offset,length));            
            return false;
        }

        public void onMessage(String data)
        {
            if (_verbose)
                System.err.printf("%s#onMessage     %s\n",this.getClass().getSimpleName(),data);
            System.err.println("GOT MESSAGE!! IT IS ---");
            System.err.println(data);


        }

        public void onMessage(byte[] data, int offset, int length)
        {
            if (_verbose)
                System.err.printf("%s#onMessage     %s\n",this.getClass().getSimpleName(),TypeUtil.toHexString(data,offset,length));
        }
    }
    
    

}
