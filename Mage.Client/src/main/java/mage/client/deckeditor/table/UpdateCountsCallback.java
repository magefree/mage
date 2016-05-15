package mage.client.deckeditor.table;

import javax.swing.JLabel;

/**
 * Updates counts in deck editor.
 * @author nantuko
 */
public class UpdateCountsCallback {

    private final javax.swing.JLabel lblCount;
    private final javax.swing.JLabel lblCreatureCount;
    private final javax.swing.JLabel lblLandCount;
    private final javax.swing.JLabel lblSoerceryCount;
    private final javax.swing.JLabel lblInstantCount;
    private final javax.swing.JLabel lblEnchantmentCount;
    private final javax.swing.JLabel lblArtifactCount;

    public UpdateCountsCallback(JLabel count, JLabel creatures, JLabel lands, JLabel sorceries, JLabel instants, JLabel enchantments, JLabel artifacts) {
        this.lblCount = count;
        this.lblCreatureCount = creatures;
        this.lblLandCount = lands;
        this.lblSoerceryCount = sorceries;
        this.lblInstantCount = instants;
        this.lblEnchantmentCount = enchantments;
        this.lblArtifactCount = artifacts;
    }

    public void update(int count, int creatures, int lands, int sorceries, int instants, int enchantments, int artifacts) {
        this.lblCount.setText(Integer.toString(count));
        this.lblCreatureCount.setText(Integer.toString(creatures));
        this.lblLandCount.setText(Integer.toString(lands));
        this.lblSoerceryCount.setText(Integer.toString(sorceries));
        this.lblInstantCount.setText(Integer.toString(instants));
        this.lblEnchantmentCount.setText(Integer.toString(enchantments));
        this.lblArtifactCount.setText(Integer.toString(artifacts));
    }
}
