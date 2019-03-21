package mage.client.deck.generator;

import mage.cards.decks.Deck;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.gui.ColorsChooser;
import mage.client.util.gui.FastSearchUtil;
import mage.client.util.sets.ConstructedFormats;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static mage.cards.decks.DeckFormats.XMAGE;

/**
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

        // Format/set dropdown with search button
        JPanel setPanel = new JPanel();
        setPanel.setLayout(new javax.swing.BoxLayout(setPanel, javax.swing.BoxLayout.LINE_AXIS));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 30;
        c.insets = new Insets(5, 10, 0, 10);
        c.weightx = 0.80;
        mainPanel.add(setPanel, c);

        cbSets = new JComboBox<>(ConstructedFormats.getTypes());
        cbSets.setSelectedIndex(0);
        cbSets.setAlignmentX(0.0F);
        setPanel.add(cbSets);

        String prefSet = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_DECK_GENERATOR_SET, null);
        if (prefSet != null) {
            cbSets.setSelectedItem(prefSet);
        }

        JButton btn = new JButton();
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/search_32.png")));
        btn.setToolTipText(FastSearchUtil.DEFAULT_EXPANSION_TOOLTIP_MESSAGE);
        btn.setAlignmentX(1.0F);
        btn.setPreferredSize(new java.awt.Dimension(32, 32));
        btn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FastSearchUtil.showFastSearchForStringComboBox(cbSets, FastSearchUtil.DEFAULT_EXPANSION_SEARCH_MESSAGE);
            }
        });
        //setPanel.add(btn, c); // TODO: can't show pickdialog here... need to replace standard modal dialog (JOptionPane) to internal mage dialog

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
            tmp.getParentFile().mkdirs();
            tmp.createNewFile();
            deck.setName(deckName);
            XMAGE.getExporter().writeDeck(tmp.getAbsolutePath(), deck.getDeckCardLists());
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
