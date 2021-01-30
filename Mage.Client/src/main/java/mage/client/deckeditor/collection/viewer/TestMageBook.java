package mage.client.deckeditor.collection.viewer;

import mage.cards.repository.CardScanner;
import mage.client.plugins.impl.Plugins;
import org.mage.card.arcane.ManaSymbols;
import org.mage.card.arcane.SvgUtils;

import javax.swing.*;

/**
 * @author nantuko
 */
public class TestMageBook extends JFrame {
    public static void main(String[] args) {
        Plugins.instance.loadPlugins();
        SvgUtils.checkSvgSupport();
        ManaSymbols.loadImages();
        CardScanner.scan();
        JFrame frame = new TestMageBook();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new MageBook(null));
        frame.pack();
        frame.setVisible(true);
    }
}
