package mage.interfaces;

import mage.MageException;

/**
 * Exception thrown by plugin on errors.
 * 
 * @author nantuko
 * 
 */
public class PluginException extends MageException {

	private static final long serialVersionUID = 5528005696138392272L;

	public PluginException(String message) {
		super(message);
	}
	
	public PluginException(Throwable t) {
		super(t);
	}
}
