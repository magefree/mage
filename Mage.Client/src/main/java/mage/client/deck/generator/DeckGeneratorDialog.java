/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.client.deck.generator;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import mage.cards.Sets;
import mage.cards.decks.Deck;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.gui.ColorsChooser;
import mage.client.util.sets.ConstructedFormats;

/**
 *
 * @author Simown
 */
public class DeckGeneratorDialog {

    private static JDialog dlg;
    private static String selectedColors;
    private static JComboBox cbSets, cbDeckSize, cbCMC;
    private static JButton btnGenerate, btnCancel, btnReset;
    private static JCheckBox cArtifacts, cSingleton, cNonBasicLands, cColorless, cAdvanced;
    private static JLabel averageCMCLabel;
    private static SimpleDateFormat dateFormat;
    private static RatioAdjustingSliderPanel adjustingSliderPanel;

    public DeckGeneratorDialog() {
        initDialog();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-SSS");
    }

    private void initDialog() {

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 15, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.10;
        JLabel text = new JLabel("Choose color for your deck:");
        mainPanel.add(text, c);

        // Color selector dropdown
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.80;
        c.ipadx = 30;
        c.insets = new Insets(5, 10, 0, 10);
        c.gridx = 1;
        c.gridy = 0;
        String chosen = MageFrame.getPreferences().get("genDeckColor", "u");
        final ColorsChooser colorsChooser = new ColorsChooser(chosen);
        mainPanel.add(colorsChooser, c);

        c.insets = new Insets(0, 15, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.10;
        c.gridx = 2;
        c.gridy = 0;
        c.ipadx = 0;
        JLabel text2 = new JLabel("(X = random color)");
        mainPanel.add(text2);

        // Format/set label
        JLabel formatSetText = new JLabel("Choose format/set for your deck:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.10;
        mainPanel.add(formatSetText, c);

        // Format/set dropdown
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 30;
        c.insets = new Insets(5, 10, 0, 10);
        c.weightx = 0.90;
        cbSets = new JComboBox<>(ConstructedFormats.getTypes());
        cbSets.setSelectedIndex(0);
        mainPanel.add(cbSets, c);

        String prefSet = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_SET, null);
        if (prefSet != null) {
            cbSets.setSelectedItem(prefSet);
        }

        // Deck size label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 15, 0, 0);
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.10;
        JLabel textDeckSize = new JLabel("Deck size:");
        mainPanel.add(textDeckSize, c);

        // Deck size dropdown
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 30;
        c.insets = new Insets(5, 10, 0, 10);
        c.weightx = 0.90;
        cbDeckSize = new JComboBox<>(new String[]{"40", "60"});
        cbDeckSize.setSelectedIndex(0);
        cbDeckSize.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(cbDeckSize, c);

        String prefSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_DECK_SIZE, "60");
        if (prefSet != null) {
            cbDeckSize.setSelectedItem(prefSize);
        }

        JPanel jCheckBoxes = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Singletons
        cSingleton = new JCheckBox("Singleton", false);
        cSingleton.setToolTipText("Allow only a single copy of each non-land card in your deck.");
        String singletonEnabled = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_SINGLETON, "false");
        cSingleton.setSelected(Boolean.valueOf(singletonEnabled));
        jCheckBoxes.add(cSingleton);

        // Artifacts
        cArtifacts = new JCheckBox("Artifacts", false);
        cArtifacts.setToolTipText("Use artifacts and artifact creatures in your deck.");
        String artifactEnabled = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_ARTIFACTS, "false");
        cArtifacts.setSelected(Boolean.valueOf(artifactEnabled));
        jCheckBoxes.add(cArtifacts);

        // Non-basic lands
        cNonBasicLands = new JCheckBox("Non-basic Lands", false);
        cNonBasicLands.setToolTipText("Use non-basic lands in your deck (if applicable).");
        String nonBasicEnabled = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_NON_BASIC_LANDS, "false");
        cNonBasicLands.setSelected(Boolean.valueOf(nonBasicEnabled));
        jCheckBoxes.add(cNonBasicLands);

        // Colorless mana
        cColorless = new JCheckBox("Colorless mana", false);
        cColorless.setToolTipText("Allow cards with colorless mana cost.");
        String colorlessEnabled = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_COLORLESS, "false");
        cColorless.setSelected(Boolean.valueOf(colorlessEnabled));
        jCheckBoxes.add(cColorless);
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.gridwidth = 3;
        mainPanel.add(jCheckBoxes, c);

        // Create the advanced configuration panel
        JPanel advancedPanel = createAdvancedPanel();

        // Advanced checkbox (enable/disable advanced configuration)
        cAdvanced = new JCheckBox("Advanced");
        cAdvanced.setToolTipText("Enable advanced configuration options");
        cAdvanced.addItemListener(itemEvent -> {
            boolean enable = cAdvanced.isSelected();
            enableAdvancedPanel(enable);
        });

        // Advanced Checkbox
        String advancedSavedValue = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_ADVANCED, "false");
        boolean advancedEnabled = Boolean.valueOf(advancedSavedValue);
        enableAdvancedPanel(advancedEnabled);
        cAdvanced.setSelected(advancedEnabled);
        c.gridy = 4;
        c.weightx = 0;
        c.insets = new Insets(10, 15, 10, 0);
        mainPanel.add(cAdvanced, c);
        c.gridy = 5;
        c.weightx = 1;
        c.insets = new Insets(5, 10, 0, 5);
        mainPanel.add(advancedPanel, c);

        btnGenerate = new JButton("Ok");
        btnGenerate.addActionListener(e -> {
            btnGenerate.setEnabled(false);
            colorsChooser.setEnabled(false);
            selectedColors = (String) colorsChooser.getSelectedItem();
            dlg.setVisible(false);
            MageFrame.getPreferences().put("genDeckColor", selectedColors);
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> {
            dlg.setVisible(false);
            selectedColors = null;
        });
        JButton[] options = {btnGenerate, btnCancel};
        JOptionPane optionPane = new JOptionPane(mainPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[1]);
        dlg = optionPane.createDialog("Generating Deck");
        dlg.setResizable(false);
        dlg.setVisible(true);
        dlg.dispose();
    }

    private void enableAdvancedPanel(boolean enable) {
        adjustingSliderPanel.setEnabled(enable);
        btnReset.setEnabled(enable);
        cbCMC.setEnabled(enable);
        averageCMCLabel.setEnabled(enable);
    }

    private JPanel createAdvancedPanel() {

        JPanel advancedPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Average CMC Label
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.10;
        averageCMCLabel = new JLabel("Average CMC:");
        advancedPanel.add(averageCMCLabel, c);

        // CMC selection dropdown
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.90;
        c.gridx = 2;
        c.gridy = 0;
        cbCMC = new JComboBox<>(DeckGeneratorCMC.values());
        cbCMC.setSelectedItem(DeckGeneratorCMC.Default);
        String cmcSelected = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_ADVANCED_CMC, DeckGeneratorCMC.Default.name());
        cbCMC.setSelectedItem(DeckGeneratorCMC.valueOf(cmcSelected));
        advancedPanel.add(cbCMC, c);

        // Advanced percentage sliders
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.ipadx = 40;
        c.weightx = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 0, 0);
        adjustingSliderPanel = new RatioAdjustingSliderPanel();

        // Restore saved slider values
        String creaturePercentage = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_CREATURE_PERCENTAGE,
                Integer.toString(DeckGeneratorPool.DEFAULT_CREATURE_PERCENTAGE));
        adjustingSliderPanel.setCreaturePercentage(Integer.parseInt(creaturePercentage));
        String nonCreaturePercentage = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_NON_CREATURE_PERCENTAGE,
                Integer.toString(DeckGeneratorPool.DEFAULT_NON_CREATURE_PERCENTAGE));
        adjustingSliderPanel.setNonCreaturePercentage(Integer.parseInt(nonCreaturePercentage));
        String landPercentage = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_LAND_PERCENTAGE,
                Integer.toString(DeckGeneratorPool.DEFAULT_LAND_PERCENTAGE));
        adjustingSliderPanel.setLandPercentage(Integer.parseInt(landPercentage));
        advancedPanel.add(adjustingSliderPanel, c);

        // Reset
        c.fill = GridBagConstraints.NONE;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.insets = new Insets(10, 10, 0, 0);
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 2;
        btnReset = new JButton("Reset");
        btnReset.setToolTipText("Reset advanced dialog to default values");
        btnReset.addActionListener(actionEvent -> {
            cbCMC.setSelectedItem(DeckGeneratorCMC.Default);
            adjustingSliderPanel.resetValues();
        });
        advancedPanel.add(btnReset, c);

        // Add a border around the advanced bits
        CompoundBorder border = BorderFactory.createCompoundBorder(new EtchedBorder(), new EmptyBorder(10, 10, 10, 10));
        advancedPanel.setBorder(border);

        return advancedPanel;
    }

    public void cleanUp() {
        for (ActionListener al : btnGenerate.getActionListeners()) {
            btnGenerate.removeActionListener(al);
        }
        for (ActionListener al : btnCancel.getActionListeners()) {
            btnCancel.removeActionListener(al);
        }
        for (ActionListener al : btnReset.getActionListeners()) {
            btnReset.removeActionListener(al);
        }
        for (ItemListener il : cAdvanced.getItemListeners()) {
            cAdvanced.removeItemListener(il);
        }
    }

    public String saveDeck(Deck deck) {
        try {
            // Random directory through the system property to avoid random numeric string attached to temp files.
            String tempDir = System.getProperty("java.io.tmpdir");
            // Generated deck has a nice unique name which corresponds to the timestamp at which it was created.
            String deckName = "Generated-Deck-" + dateFormat.format(new Date());
            File tmp = new File(tempDir + File.separator + deckName + ".dck");
            tmp.createNewFile();
            deck.setName(deckName);
            Sets.saveDeck(tmp.getAbsolutePath(), deck.getDeckCardLists());
            cleanUp();
            return tmp.getAbsolutePath();
        } catch (Exception e) {
            MageFrame.getInstance().showError("Couldn't generate deck. Try again.");
        }
        return null;
    }

    public String getSelectedFormat() {
        return (String) cbSets.getSelectedItem();
    }

    public boolean isSingleton() {
        boolean selected = cSingleton.isSelected();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_SINGLETON, Boolean.toString(selected));
        return selected;
    }

    public boolean isColorless() {
        boolean selected = cColorless.isSelected();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_COLORLESS, Boolean.toString(selected));
        return selected;
    }

    public boolean useArtifacts() {
        boolean selected = cArtifacts.isSelected();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_ARTIFACTS, Boolean.toString(selected));
        return selected;
    }

    public boolean useNonBasicLand() {
        boolean selected = cNonBasicLands.isSelected();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_NON_BASIC_LANDS, Boolean.toString(selected));
        return selected;
    }

    public boolean isAdvanced() {
        boolean selected = cAdvanced.isSelected();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_ADVANCED, Boolean.toString(selected));
        return selected;
    }

    public int getCreaturePercentage() {
        int percentage = adjustingSliderPanel.getCreaturePercentage();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_CREATURE_PERCENTAGE, Integer.toString(percentage));
        return percentage;
    }

    public int getNonCreaturePercentage() {
        int percentage = adjustingSliderPanel.getNonCreaturePercentage();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_NON_CREATURE_PERCENTAGE, Integer.toString(percentage));
        return percentage;
    }

    public int getLandPercentage() {
        int percentage = adjustingSliderPanel.getLandPercentage();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_LAND_PERCENTAGE, Integer.toString(percentage));
        return percentage;
    }

    public int getDeckSize() {
        return Integer.parseInt(cbDeckSize.getSelectedItem().toString());
    }

    public DeckGeneratorCMC getDeckGeneratorCMC() {
        DeckGeneratorCMC selectedCMC = (DeckGeneratorCMC) cbCMC.getSelectedItem();
        PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_ADVANCED_CMC, selectedCMC.name());
        return selectedCMC;
    }

    public String getSelectedColors() {
        if (selectedColors != null) {
            PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_DECK_SIZE, cbDeckSize.getSelectedItem().toString());
            PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_SET, cbSets.getSelectedItem().toString());
        }
        return selectedColors;
    }
}
