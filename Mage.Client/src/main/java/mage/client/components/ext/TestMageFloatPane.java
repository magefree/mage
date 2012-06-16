package mage.client.components.ext;

import mage.client.components.MageRoundPane;

import javax.swing.*;

/**
 * @author ayratn
 */
public class TestMageFloatPane {

    public static void main(String... args) {
        JFrame f = new JFrame();

        f.setSize(600, 400);
        f.setVisible(true);

        MageFloatPane fp = new MageFloatPane();
        fp.setCard("Card");

        MageRoundPane popupContainer = new MageRoundPane();
        popupContainer.setLayout(null);

        popupContainer.add(fp);
        //popupContainer.setVisible(false);
        popupContainer.setBounds(0, 0, 320 + 80, 201 + 80);


        JDialog floatOnParent = new JDialog(f, false);
        floatOnParent.setUndecorated(true);
        floatOnParent.getContentPane().add(popupContainer);

        floatOnParent.setBounds(300, 100, 300, 200);
        floatOnParent.setVisible(true);
    }
}
