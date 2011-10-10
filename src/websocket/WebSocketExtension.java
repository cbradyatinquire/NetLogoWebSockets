package websocket;

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.PrimitiveManager;

public class WebSocketExtension extends DefaultClassManager {

		
	@Override
	public void load(PrimitiveManager primitiveManager) throws ExtensionException {
		
		System.err.println("STARTED EXTENSION");
       
        primitiveManager.addPrimitive( "init", new InitCommand() );
        primitiveManager.addPrimitive("set-message", new SetMessagePrim() );
        
	}
	

	
	
}
