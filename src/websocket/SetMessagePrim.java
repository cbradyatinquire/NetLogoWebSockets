package websocket;

import org.nlogo.api.Argument;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;


public class SetMessagePrim extends DefaultCommand {


	@Override
	public void perform(Argument[] arg0, org.nlogo.api.Context arg1)
			throws ExtensionException, LogoException {
		
		String blah = arg0[0].getString();
		if (blah == null) { blah = "<h1>Hi There World</h1>"; }
		if ( blah.equals(".") ) {blah = null; }
		HelloWorld.setMessage(blah);	
	}

	
	public Syntax getSyntax() {
        return Syntax.commandSyntax( new int[] {Syntax.StringType()} );
    }
	
}
