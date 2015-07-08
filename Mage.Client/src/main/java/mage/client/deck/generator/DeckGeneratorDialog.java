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

import mage.cards.Sets;
import mage.cards.decks.Deck;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.gui.ColorsChooser;
import mage.client.util.sets.ConstructedFormats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.UUID;

/**
 *
 * @author Simown
 */
public class DeckGeneratorDialog {

    private static JDialog dlg;
    private static String selectedColors;
    private static JComboBox cbSets;
    private static JComboBox cbDeckSize;
    private static JButton btnGenerate, btnCancel;
    private static JCheckBox cArtifacts, cSingleton, cNonBasicLands;

    public DeckGeneratorDialog()
    {
        initDialog();
    }

    private void initDialog() {
        JPanel p0 = new JPanel();
        p0.setLayout(new BoxLayout(p0, BoxLayout.Y_AXIS));

        JLabel text = new JLabel("Choose color for your deck: ");
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        p0.add(text);

        p0.add(Box.createVerticalStrut(5));
        String chosen = MageFrame.getPreferences().get("genDeckColor", "u");
        final ColorsChooser colorsChooser = new ColorsChooser(chosen);
        p0.add(colorsChooser);

        p0.add(Box.createVerticalStrut(5));
        JLabel text2 = new JLabel("(X - random color)");
        text2.setAlignmentX(Component.CENTER_ALIGNMENT);
        p0.add(text2);

        p0.add(Box.createVerticalStrut(5));
        JPanel jPanel = new JPanel();
        JLabel text3 = new JLabel("Choose sets:");
        cbSets = new JComboBox(ConstructedFormats.getTypes());
        cbSets.setSelectedIndex(0);
        cbSets.setPreferredSize(new Dimension(300, 25));
        cbSets.setMaximumSize(new Dimension(300, 25));
        cbSets.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel.add(text3);
        jPanel.add(cbSets);
        p0.add(jPanel);

        String prefSet = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_SET, null);
        if (prefSet != null) {
            cbSets.setSelectedItem(prefSet);
        }

        p0.add(Box.createVerticalStrut(5));
        JPanel jPanel2 = new JPanel();
        JLabel textDeckSize = new JLabel("Deck size:");
        cbDeckSize = new JComboBox(new String[]{"40","60"});
        cbDeckSize.setSelectedIndex(0);
        cbDeckSize.setPreferredSize(new Dimension(300, 25));
        cbDeckSize.setMaximumSize(new Dimension(300, 25));
        cbDeckSize.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel2.add(textDeckSize);
        jPanel2.add(cbDeckSize);
        p0.add(jPanel2);

        String prefSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_DECK_SIZE, "60");
        if (prefSet != null) {
            cbDeckSize.setSelectedItem(prefSize);
        }

        p0.add(Box.createVerticalStrut(5));
        JPanel jCheckBoxes = new JPanel();

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

        jCheckBoxes.setPreferredSize(new Dimension(300, 25));
        jCheckBoxes.setMaximumSize(new Dimension(300, 25));
        p0.add(jCheckBoxes);

        btnGenerate = new JButton("Ok");
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGenerate.setEnabled(false);
                colorsChooser.setEnabled(false);
                selectedColors = (String) colorsChooser.getSelectedItem();
                dlg.setVisible(false);
                MageFrame.getPreferences().put("genDeckColor", selectedColors);
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
                selectedColors = null;
            }
        });
        JButton[] options = {btnGenerate, btnCancel};
        JOptionPane optionPane = new JOptionPane(p0, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[1]);
        dlg = optionPane.createDialog("Generating deck");
        dlg.setVisible(true);
        dlg.dispose();
    }

    public static void cleanUp() {
        for (ActionListener al: btnGenerate.getActionListeners()) {
            btnGenerate.removeActionListener(al);
        }
        for (ActionListener al: btnCancel.getActionListeners()) {
            btnCancel.removeActionListener(al);
        }
        //deck = null;
    }

    public String saveDeck(Deck deck) {
        try {
            File tmp = File.createTempFile("tempDeck" + UUID.randomUUID().toString(), ".dck");
            tmp.createNewFile();
            deck.setName("Generated-Deck-" + UUID.randomUUID());
            Sets.saveDeck(tmp.getAbsolutePath(), deck.getDeckCardLists());
            DeckGeneratorDialog.cleanUp();
            return tmp.getAbsolutePath();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Couldn't generate deck. Try again.");
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

    public int getDeckSize() {
        return Integer.parseInt(cbDeckSize.getSelectedItem().toString());
    }

    public String getSelectedColors() {
        if (selectedColors != null) {
            PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_DECK_SIZE, cbDeckSize.getSelectedItem().toString());
            PreferencesDialog.saveValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_SET, cbSets.getSelectedItem().toString());
        }
        return selectedColors;
    }
}
