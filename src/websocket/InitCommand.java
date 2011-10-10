package websocket;

import org.eclipse.jetty.server.Server;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;

public class InitCommand extends DefaultCommand {

	private boolean going = false;
	
	@Override
	public void perform(Argument[] arg0, Context arg1)
			throws ExtensionException, LogoException 
	{

		if (!going)
		{
			CommandThread ct = new CommandThread();
			ct.start();
			going = true;
		}
		else
		{
			HelloWorld.setMessage(null);
		}
			
	}
		
	
	
	
	public class CommandThread extends Thread 
	{   
        @Override
        public void run() 
        {
        	try 
        	{
				//Server server2 = new Server(8080);
				WebSockServer server = new WebSockServer(8080);
				//HelloWorld myHello = new HelloWorld();
		       // server2.setHandler(myHello);
		        server.start();
		        server.join();
		        //server2.start();
		        //server2.join();
		    }
        	catch (Exception e)
        	{
		        e.printStackTrace();
        	}
        }
    }

}
