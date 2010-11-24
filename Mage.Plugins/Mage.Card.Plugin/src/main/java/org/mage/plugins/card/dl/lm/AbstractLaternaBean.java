/**
 * AbstractLaternaBean.java
 * 
 * Created on 25.08.2010
 */

package org.mage.plugins.card.dl.lm;


import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.beans.AbstractBoundBean;
import org.mage.plugins.card.dl.beans.EventListenerList;
import org.mage.plugins.card.dl.beans.properties.Properties;
import org.mage.plugins.card.dl.beans.properties.bound.BoundProperties;


/**
 * The class AbstractLaternaBean.
 * 
 * @version V0.0 25.08.2010
 * @author Clemens Koza
 */
public class AbstractLaternaBean extends AbstractBoundBean {
	protected static final Logger log = Logger.getLogger(AbstractLaternaBean.class);
    protected Properties        properties = new BoundProperties(s);
    protected EventListenerList listeners  = new EventListenerList();
}
