package mage.client.deckeditor.table;

import javax.swing.*;

/**
 * Updates counts in deck editor.
 * @author nantuko
 */
public class UpdateCountsCallback {

    private final javax.swing.JLabel lblCount;
    private final javax.swing.JLabel lblCreatureCount;
    private final javax.swing.JLabel lblLandCount;

    public UpdateCountsCallback(JLabel count, JLabel creatures, JLabel lands) {
        this.lblCount = count;
        this.lblCreatureCount = creatures;
        this.lblLandCount = lands;
    }

    public void update(int count, int creatures, int lands) {
        this.lblCount.setText("Count: " + Integer.toString(count));
        this.lblCreatureCount.setText("Creatures: " + Integer.toString(creatures));
        this.lblLandCount.setText("Lands: " + Integer.toString(lands));
    }
}
