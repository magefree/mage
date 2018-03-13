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
    private final javax.swing.JLabel lblSorceryCount;
    private final javax.swing.JLabel lblInstantCount;
    private final javax.swing.JLabel lblEnchantmentCount;
    private final javax.swing.JLabel lblArtifactCount;

    public UpdateCountsCallback(JLabel count, JLabel creatures, JLabel lands, JLabel sorceries, JLabel instants, JLabel enchantments, JLabel artifacts) {
        this.lblCount = count;
        this.lblCreatureCount = creatures;
        this.lblLandCount = lands;
        this.lblSorceryCount = sorceries;
        this.lblInstantCount = instants;
        this.lblEnchantmentCount = enchantments;
        this.lblArtifactCount = artifacts;
    }

    public void update(int count, int creatures, int lands, int sorceries, int instants, int enchantments, int artifacts) {
        if (this.lblCount != null)
            this.lblCount.setText(Integer.toString(count));
        if (this.lblCreatureCount != null)
            this.lblCreatureCount.setText(Integer.toString(creatures));
        if (this.lblLandCount != null)
            this.lblLandCount.setText(Integer.toString(lands));
        if (this.lblSorceryCount != null)
            this.lblSorceryCount.setText(Integer.toString(sorceries));
        if (this.lblInstantCount != null)
            this.lblInstantCount.setText(Integer.toString(instants));
        if (this.lblEnchantmentCount != null)
            this.lblEnchantmentCount.setText(Integer.toString(enchantments));
        if (this.lblArtifactCount != null)
            this.lblArtifactCount.setText(Integer.toString(artifacts));
    }
}
