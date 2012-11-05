package mage.client.deckeditor.collection.viewer;

import mage.client.plugins.impl.Plugins;

import javax.swing.*;
import mage.cards.repository.CardScanner;
import org.mage.card.arcane.ManaSymbols;

/**
 * @author nantuko
 */
public class TestMageBook extends JFrame {
    public static void main(String[] args) {
        Plugins.getInstance().loadPlugins();
        ManaSymbols.loadImages();
        CardScanner.scan();
        JFrame frame = new TestMageBook();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MageBook(null));
        frame.pack();
        frame.setVisible(true);
    }
}
