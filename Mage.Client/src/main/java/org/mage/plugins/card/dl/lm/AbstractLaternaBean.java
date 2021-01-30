package org.mage.plugins.card.dl.lm;

import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.beans.AbstractBoundBean;
import org.mage.plugins.card.dl.beans.EventListenerList;
import org.mage.plugins.card.dl.beans.properties.Properties;
import org.mage.plugins.card.dl.beans.properties.bound.BoundProperties;

/**
 * The class AbstractLaternaBean.
 *
 * @author Clemens Koza
 * @version V0.0 25.08.2010
 */
public class AbstractLaternaBean extends AbstractBoundBean {
    protected static final Logger log = Logger.getLogger(AbstractLaternaBean.class);
    protected final Properties properties = new BoundProperties(s);
    protected EventListenerList listeners = new EventListenerList();
}
