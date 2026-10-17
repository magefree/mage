package mage.client.dialog;

import mage.client.MageFrame;
import mage.client.util.gui.FastSearchUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * App GUI: control dialog for card images downloader service
 *
 * @author JayDi85
 */
public class DownloadImagesDialog extends MageDialog {

    private final Map<Component, Boolean> actionsControlStates = new HashMap<>();
    private DownloadImagesCallback callback = null;

    public DownloadImagesDialog() {
        initComponents();
        this.setModal(true);

        // fix for panelInfo (it's resets aligmentX after netbeans designer opened)
        panelInfo.setAlignmentX(CENTER_ALIGNMENT);

        // Keep each label above its control; FlowLayout cannot measure wrapped rows when packing.
        for (JPanel column : new JPanel[]{panelSourceLeft, panelSourceCenter, panelSourceRight, panelModeInner}) {
            column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
            column.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            for (Component control : column.getComponents()) {
                ((JComponent) control).setAlignmentX(LEFT_ALIGNMENT);
            }
        }
        comboSource.setPrototypeDisplayValue("Scryfall images (English)");
        comboSets.setPrototypeDisplayValue("All sets with missing images");
        comboLanguage.setPrototypeDisplayValue("English");
        comboDownloadThreads.setPrototypeDisplayValue("100");

        setTabTitle(0, "Standard download", "/buttons/card_panel.png");
        setTabTitle(1, "Custom download", "/buttons/list_panel.png");
        // TODO: add manual mode as tab
        tabsList.getTabComponentAt(1).setEnabled(false);
        tabsList.setEnabledAt(1, false);

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    public interface DownloadImagesCallback {
        void onDownloadDone();
    }

    public void showDialog(DownloadImagesCallback callback) {
        // window settings
        this.callback = callback;
        MageFrame.getDesktop().remove(this);
        MageFrame.getDesktop().add(this, this.isModal() ? JLayeredPane.MODAL_LAYER : JLayeredPane.PALETTE_LAYER);
        showDownloadControls(false); // call to change window size and view mode

        this.setVisible(true);
    }

    public void setGlobalInfo(String info) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> setGlobalInfo(info));
            return;
        }
        labelGlobal.setText(info);
        pack();
        makeWindowCentered();
    }

    public void setCurrentInfo(String info) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> setCurrentInfo(info));
            return;
        }
        labelInfo.setText(info);
        pack();
        makeWindowCentered();
    }

    public JComboBox getSourcesCombo() {
        return this.comboSource;
    }

    public JComboBox getLaunguagesCombo() {
        return this.comboLanguage;
    }

    public JComboBox getDownloadThreadsCombo() {
        return this.comboDownloadThreads;
    }

    public JComboBox getSetsCombo() {
        return this.comboSets;
    }

    public JButton getStartButton() {
        return this.buttonOK;
    }

    public JButton getCancelButton() {
        return this.buttonCancel;
    }

    public JButton getStopButton() {
        return this.buttonStop;
    }

    public JProgressBar getProgressBar() {
        return this.progress;
    }

    public JCheckBox getRedownloadCheckbox() {
        return this.checkboxRedownload;
    }

    public void showLanguagesSupport(boolean haveSupport) {
        labelLanguage.setEnabled(haveSupport);
        comboLanguage.setEnabled(haveSupport);
    }

    private void enableActionControl(boolean enable, Component comp) {
        if (enable) {
            // restore last enable state
            comp.setEnabled(actionsControlStates.getOrDefault(comp, true));
        } else {
            // save enable state and disable it
            actionsControlStates.putIfAbsent(comp, comp.isEnabled());
            comp.setEnabled(false);
        }
    }

    public void enableActionControls(boolean enable) {
        // restrict user actions while downloading/processing (all buttons, comboboxes and edits)
        enableActionControl(enable, tabsList);
        enableActionControl(enable, comboSource);
        enableActionControl(enable, comboSets);
        enableActionControl(enable, buttonSearchSet);
        enableActionControl(enable, comboLanguage);
        enableActionControl(enable, comboDownloadThreads);
        enableActionControl(enable, checkboxRedownload);
    }

    private void setTabTitle(int tabIndex, String title, String iconResourceName) {
        // tab caption with left sided icon
        // https://stackoverflow.com/questions/1782224/jtabbedpane-icon-on-left-side-of-tabs
        JLabel lbl = new JLabel(title);
        Icon icon = new ImageIcon(getClass().getResource(iconResourceName));
        lbl.setIcon(icon);
        lbl.setIconTextGap(5);
        lbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        tabsList.setTabComponentAt(tabIndex, lbl);
    }

    public void showDownloadControls(boolean needToShow) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> showDownloadControls(needToShow));
            return;
        }
        // 2 modes:
        //  - only message;
        //  - message + download controls and buttons        
        this.panelGlobal.setVisible(true);
        this.buttonStop.setVisible(!needToShow); // stop button only for loading mode
        this.tabsList.setVisible(needToShow);
        this.panelCommands.setVisible(needToShow);

        pack();
        this.makeWindowCentered();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGlobal = new javax.swing.JPanel();
        fillerGlobal1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        labelGlobal = new javax.swing.JLabel();
        buttonStop = new javax.swing.JButton();
        fillerglobal2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        tabsList = new javax.swing.JTabbedPane();
        tabMain = new javax.swing.JPanel();
        panelInfo = new javax.swing.JPanel();
        fillerInfo1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        labelInfo = new javax.swing.JLabel();
        fillerInfo2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        panelSource = new javax.swing.JPanel();
        panelSourceLeft = new javax.swing.JPanel();
        labelSource = new javax.swing.JLabel();
        comboSource = new javax.swing.JComboBox<>();
        panelSourceCenter = new javax.swing.JPanel();
        labelLanguage = new javax.swing.JLabel();
        comboLanguage = new javax.swing.JComboBox<>();
        panelSourceRight = new javax.swing.JPanel();
        labelDownloadThreads = new javax.swing.JLabel();
        comboDownloadThreads = new javax.swing.JComboBox<>();
        panelMode = new javax.swing.JPanel();
        panelModeInner = new javax.swing.JPanel();
        labelMode = new javax.swing.JLabel();
        panelModeSelect = new javax.swing.JPanel();
        comboSets = new javax.swing.JComboBox<>();
        fillerMode1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        buttonSearchSet = new javax.swing.JButton();
        panelRedownload = new javax.swing.JPanel();
        checkboxRedownload = new javax.swing.JCheckBox();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 3), new java.awt.Dimension(32767, 5));
        fillerMain1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        panelProgress = new javax.swing.JPanel();
        fillerProgress1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        progress = new javax.swing.JProgressBar();
        fillerProgress2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        fillerMain2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        tabCustom = new javax.swing.JPanel();
        panelCommands = new javax.swing.JPanel();
        buttonOK = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setTitle("Downloading images");
        getContentPane().setLayout(new java.awt.BorderLayout());

        panelGlobal.setLayout(new javax.swing.BoxLayout(panelGlobal, javax.swing.BoxLayout.Y_AXIS));
        panelGlobal.add(fillerGlobal1);

        labelGlobal.setText("Initializing image download...");
        labelGlobal.setAlignmentX(0.5F);
        panelGlobal.add(labelGlobal);

        buttonStop.setText("Cancel");
        buttonStop.setAlignmentX(0.5F);
        buttonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopActionPerformed(evt);
            }
        });
        panelGlobal.add(buttonStop);
        panelGlobal.add(fillerglobal2);

        getContentPane().add(panelGlobal, java.awt.BorderLayout.NORTH);

        tabMain.setLayout(new javax.swing.BoxLayout(tabMain, javax.swing.BoxLayout.Y_AXIS));

        panelInfo.setLayout(new javax.swing.BoxLayout(panelInfo, javax.swing.BoxLayout.Y_AXIS));
        panelInfo.add(fillerInfo1);

        labelInfo.setText("Missing stats: 12345 card images / 789 token images");
        labelInfo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 7, 0, 0));
        panelInfo.add(labelInfo);
        panelInfo.add(fillerInfo2);

        tabMain.add(panelInfo);

        panelSource.setLayout(new javax.swing.BoxLayout(panelSource, javax.swing.BoxLayout.X_AXIS));

        panelSourceLeft.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        labelSource.setText("Images source to download:");
        panelSourceLeft.add(labelSource);

        comboSource.setMaximumRowCount(15);
        comboSource.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        panelSourceLeft.add(comboSource);

        panelSource.add(panelSourceLeft);

        panelSourceCenter.setAlignmentX(0.0F);
        panelSourceCenter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        labelLanguage.setText("Language:");
        panelSourceCenter.add(labelLanguage);

        comboLanguage.setMaximumRowCount(15);
        comboLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        panelSourceCenter.add(comboLanguage);

        panelSource.add(panelSourceCenter);

        panelSourceRight.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        labelDownloadThreads.setText("Download threads:");
        panelSourceRight.add(labelDownloadThreads);

        comboDownloadThreads.setMaximumRowCount(15);
        comboDownloadThreads.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        panelSourceRight.add(comboDownloadThreads);

        panelSource.add(panelSourceRight);

        tabMain.add(panelSource);

        panelMode.setLayout(new javax.swing.BoxLayout(panelMode, javax.swing.BoxLayout.LINE_AXIS));

        panelModeInner.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        labelMode.setText("Sets to download:");
        labelMode.setAlignmentY(0.0F);
        panelModeInner.add(labelMode);

        panelModeSelect.setLayout(new javax.swing.BoxLayout(panelModeSelect, javax.swing.BoxLayout.X_AXIS));

        comboSets.setMaximumRowCount(15);
        comboSets.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        panelModeSelect.add(comboSets);
        panelModeSelect.add(fillerMode1);

        buttonSearchSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/search_24.png"))); // NOI18N
        buttonSearchSet.setToolTipText("Search set to download");
        buttonSearchSet.setAlignmentX(1.0F);
        buttonSearchSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchSetActionPerformed(evt);
            }
        });
        panelModeSelect.add(buttonSearchSet);

        panelModeInner.add(panelModeSelect);

        panelMode.add(panelModeInner);

        panelRedownload.setAlignmentX(0.0F);
        panelRedownload.setLayout(new java.awt.BorderLayout());

        checkboxRedownload.setText("<html>Re-download all selected images");
        checkboxRedownload.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        panelRedownload.add(checkboxRedownload, java.awt.BorderLayout.CENTER);
        panelRedownload.add(filler1, java.awt.BorderLayout.PAGE_END);

        panelMode.add(panelRedownload);

        tabMain.add(panelMode);
        tabMain.add(fillerMain1);

        panelProgress.setLayout(new javax.swing.BoxLayout(panelProgress, javax.swing.BoxLayout.X_AXIS));
        panelProgress.add(fillerProgress1);

        progress.setValue(75);
        progress.setString("123 of 12313 (120 cards/546 tokens) image downloads finished! Please wait! [123 Mb]");
        progress.setStringPainted(true);
        panelProgress.add(progress);
        panelProgress.add(fillerProgress2);

        tabMain.add(panelProgress);
        tabMain.add(fillerMain2);

        tabsList.addTab("Standard download", new javax.swing.ImageIcon(getClass().getResource("/buttons/card_panel.png")), tabMain); // NOI18N

        tabCustom.setLayout(new javax.swing.BoxLayout(tabCustom, javax.swing.BoxLayout.Y_AXIS));
        tabsList.addTab("Custom download", new javax.swing.ImageIcon(getClass().getResource("/buttons/list_panel.png")), tabCustom); // NOI18N

        getContentPane().add(tabsList, java.awt.BorderLayout.CENTER);

        panelCommands.setAlignmentX(0.0F);
        panelCommands.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING));

        buttonOK.setText("Start download");
        panelCommands.add(buttonOK);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.setText("Cancel");
        panelCommands.add(buttonCancel);

        getContentPane().add(panelCommands, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        onCancel();
    }//GEN-LAST:event_closeDialog

    private void buttonSearchSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchSetActionPerformed
        FastSearchUtil.showFastSearchForStringComboBox(comboSets, FastSearchUtil.DEFAULT_EXPANSION_SEARCH_MESSAGE, 400, 500);
    }//GEN-LAST:event_buttonSearchSetActionPerformed

    private void buttonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopActionPerformed
        // TODO implement stop feature for cancel button
    }//GEN-LAST:event_buttonStopActionPerformed

    private void onCancel() {
        // it's only control GUI, all works in download service
        hideDialog();
        if (this.callback != null) {
            this.callback.onDownloadDone();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOK;
    private javax.swing.JButton buttonSearchSet;
    private javax.swing.JButton buttonStop;
    private javax.swing.JCheckBox checkboxRedownload;
    private javax.swing.JComboBox<String> comboDownloadThreads;
    private javax.swing.JComboBox<String> comboLanguage;
    private javax.swing.JComboBox<String> comboSets;
    private javax.swing.JComboBox<String> comboSource;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler fillerGlobal1;
    private javax.swing.Box.Filler fillerInfo1;
    private javax.swing.Box.Filler fillerInfo2;
    private javax.swing.Box.Filler fillerMain1;
    private javax.swing.Box.Filler fillerMain2;
    private javax.swing.Box.Filler fillerMode1;
    private javax.swing.Box.Filler fillerProgress1;
    private javax.swing.Box.Filler fillerProgress2;
    private javax.swing.Box.Filler fillerglobal2;
    private javax.swing.JLabel labelDownloadThreads;
    private javax.swing.JLabel labelGlobal;
    private javax.swing.JLabel labelInfo;
    private javax.swing.JLabel labelLanguage;
    private javax.swing.JLabel labelMode;
    private javax.swing.JLabel labelSource;
    private javax.swing.JPanel panelCommands;
    private javax.swing.JPanel panelGlobal;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelMode;
    private javax.swing.JPanel panelModeInner;
    private javax.swing.JPanel panelModeSelect;
    private javax.swing.JPanel panelProgress;
    private javax.swing.JPanel panelRedownload;
    private javax.swing.JPanel panelSource;
    private javax.swing.JPanel panelSourceCenter;
    private javax.swing.JPanel panelSourceLeft;
    private javax.swing.JPanel panelSourceRight;
    private javax.swing.JProgressBar progress;
    private javax.swing.JPanel tabCustom;
    private javax.swing.JPanel tabMain;
    private javax.swing.JTabbedPane tabsList;
    // End of variables declaration//GEN-END:variables
}
