package mage.client.components;

import javax.swing.JComponent;
import javax.swing.plaf.synth.Region;
import javax.swing.plaf.synth.SynthStyle;
import javax.swing.plaf.synth.SynthStyleFactory;

/**
 * Class makes {@link JInternalFrame} translucent background possible.
 * This class provides fix that makes setOpaque(false) and setBackgroundColor(any color) working, 
 * especially for Nimbus LAF that has great problems with it.
 * 
 * @version 0.1 16.11.2010
 * @author nantuko
 */
public class MageSynthStyleFactory extends SynthStyleFactory {
    private SynthStyleFactory wrappedFactory;

    public MageSynthStyleFactory(SynthStyleFactory factory) {
        this.wrappedFactory = factory;
    }

    public SynthStyle getStyle(JComponent c, Region id) {
        SynthStyle s = wrappedFactory.getStyle(c, id);
        if (id == Region.INTERNAL_FRAME) {
            s = new TranslucentSynthSytle(s);
        }
        return s;
    }
}