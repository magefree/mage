package mage.client.dialog;

import mage.client.MageFrame;
import mage.client.util.gui.FastSearchUtil;
import mage.client.util.gui.MageDialogState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * App GUI: download card images window
 *
 * @author JayDi85
 */
public class DownloadImagesDialog extends MageDialog {

    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;

    private final Dimension sizeModeMessageOnly;
    private final Dimension sizeModeMessageAndControls;
    private final Map<Component, Boolean> actionsControlStates = new HashMap<>();


    /**
     * Creates new form DownloadImagesDialog
     */
    public DownloadImagesDialog() {
        initComponents();
        this.setModal(true);

        // fix for panelInfo (it's resets aligmentX after netbeans designer opened)
        panelInfo.setAlignmentX(CENTER_ALIGNMENT);

        // save default sizes (WARNING, you must sync it manually with designer sizes)
        //
        this.sizeModeMessageAndControls = new Dimension(716, 329); // dialog -> properties -> code tab -> designer size
        //
        this.sizeModeMessageOnly = new Dimension(this.sizeModeMessageAndControls.getSize());
        sizeModeMessageOnly.height = 25 * 4;
        sizeModeMessageOnly.width = sizeModeMessageOnly.width / 2;

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    public void setWindowSize(int width, int heigth) {
        this.setSize(new Dimension(width, heigth));
    }

    public void showDialog() {
        showDialog(null);
    }

    public void showDialog(MageDialogState mageDialogState) {

        // window settings
        MageFrame.getDesktop().remove(this);
        if (this.isModal()) {
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        } else {
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        }
        if (mageDialogState != null) mageDialogState.setStateToDialog(this);
        else this.makeWindowCentered();

        showDownloadControls(false); // call to change window size

        this.setVisible(true);
    }

    public void setGlobalInfo(String info) {
        this.labelGlobal.setText(info);
    }

    public void setCurrentInfo(String info) {
        this.labelInfo.setText(info);
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
        // 2 modes:
        //  - only message;
        //  - message + download controls and buttons        
        this.panelGlobal.setVisible(true);
        this.buttonStop.setVisible(!needToShow); // stop button only for loading mode
        this.tabsList.setVisible(needToShow);
        this.panelCommands.setVisible(needToShow);

        // auto-size form
        if (needToShow) {
            this.setWindowSize(this.sizeModeMessageAndControls.width, this.sizeModeMessageAndControls.height);
        } else {
            this.setWindowSize(this.sizeModeMessageOnly.width, this.sizeModeMessageOnly.height);
        }
        this.makeWindowCentered();
        //this.setLocationRelativeTo(null); // center screen //FIX

        // icons on tabs left side
        setTabTitle(0, "Standard download", "/buttons/card_panel.png");
        setTabTitle(1, "Custom download", "/buttons/list_panel.png");

        // TODO: add manual mode as tab
        this.tabsList.getTabComponentAt(1).setEnabled(false);
        this.tabsList.setEnabledAt(1, false);
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
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
        setPreferredSize(new java.awt.Dimension(600, 400));
        getContentPane().setLayout(new java.awt.BorderLayout());

        panelGlobal.setLayout(new javax.swing.BoxLayout(panelGlobal, javax.swing.BoxLayout.Y_AXIS));
        panelGlobal.add(fillerGlobal1);

        labelGlobal.setText("Initializing image download...");
        labelGlobal.setAlignmentX(0.5F);
        panelGlobal.add(labelGlobal);

        buttonStop.setText("Cancel");
        buttonStop.setAlignmentX(0.5F);
        buttonStop.setPreferredSize(new java.awt.Dimension(65, 30));
        buttonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopActionPerformed(evt);
            }
        });
        panelGlobal.add(buttonStop);
        panelGlobal.add(fillerglobal2);

        getContentPane().add(panelGlobal, java.awt.BorderLayout.NORTH);

        tabsList.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        tabMain.setLayout(new javax.swing.BoxLayout(tabMain, javax.swing.BoxLayout.Y_AXIS));

        panelInfo.setLayout(new javax.swing.BoxLayout(panelInfo, javax.swing.BoxLayout.Y_AXIS));
        panelInfo.add(fillerInfo1);

        labelInfo.setText("Missing stats: 12345 card images / 789 token images");
        labelInfo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 7, 0, 0));
        panelInfo.add(labelInfo);
        panelInfo.add(fillerInfo2);

        tabMain.add(panelInfo);

        panelSource.setMaximumSize(new java.awt.Dimension(65536, 55));
        panelSource.setMinimumSize(new java.awt.Dimension(352, 55));
        panelSource.setPreferredSize(new java.awt.Dimension(593, 55));
        panelSource.setLayout(new javax.swing.BoxLayout(panelSource, javax.swing.BoxLayout.X_AXIS));

        panelSourceLeft.setMinimumSize(new java.awt.Dimension(430, 30));
        panelSourceLeft.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        labelSource.setText("Images source to download:");
        panelSourceLeft.add(labelSource);

        comboSource.setMaximumRowCount(15);
        comboSource.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        comboSource.setMinimumSize(new java.awt.Dimension(300, 20));
        comboSource.setPreferredSize(new java.awt.Dimension(400, 25));
        panelSourceLeft.add(comboSource);

        panelSource.add(panelSourceLeft);

        panelSourceCenter.setAlignmentX(0.0F);
        panelSourceCenter.setMaximumSize(new java.awt.Dimension(130, 32767));
        panelSourceCenter.setMinimumSize(new java.awt.Dimension(130, 10));
        panelSourceCenter.setPreferredSize(new java.awt.Dimension(130, 100));
        panelSourceCenter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        labelLanguage.setText("Language:");
        panelSourceCenter.add(labelLanguage);

        comboLanguage.setMaximumRowCount(15);
        comboLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        comboLanguage.setPreferredSize(new java.awt.Dimension(110, 25));
        panelSourceCenter.add(comboLanguage);

        panelSource.add(panelSourceCenter);

        panelSourceRight.setMinimumSize(new java.awt.Dimension(150, 30));
        panelSourceRight.setPreferredSize(new java.awt.Dimension(150, 35));
        panelSourceRight.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        labelDownloadThreads.setText("Download threads:");
        panelSourceRight.add(labelDownloadThreads);

        comboDownloadThreads.setMaximumRowCount(15);
        comboDownloadThreads.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        comboDownloadThreads.setPreferredSize(new java.awt.Dimension(110, 25));
        panelSourceRight.add(comboDownloadThreads);

        panelSource.add(panelSourceRight);

        tabMain.add(panelSource);

        panelMode.setMaximumSize(new java.awt.Dimension(32869, 55));
        panelMode.setMinimumSize(new java.awt.Dimension(322, 55));
        panelMode.setPreferredSize(new java.awt.Dimension(100, 55));
        panelMode.setLayout(new javax.swing.BoxLayout(panelMode, javax.swing.BoxLayout.LINE_AXIS));

        panelModeInner.setMinimumSize(new java.awt.Dimension(430, 43));
        panelModeInner.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        labelMode.setText("Sets to download:");
        labelMode.setAlignmentY(0.0F);
        panelModeInner.add(labelMode);

        panelModeSelect.setLayout(new javax.swing.BoxLayout(panelModeSelect, javax.swing.BoxLayout.X_AXIS));

        comboSets.setMaximumRowCount(15);
        comboSets.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        comboSets.setPreferredSize(new java.awt.Dimension(373, 25));
        panelModeSelect.add(comboSets);
        panelModeSelect.add(fillerMode1);

        buttonSearchSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/search_24.png"))); // NOI18N
        buttonSearchSet.setToolTipText("Fast search your flag");
        buttonSearchSet.setAlignmentX(1.0F);
        buttonSearchSet.setPreferredSize(new java.awt.Dimension(25, 25));
        buttonSearchSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchSetActionPerformed(evt);
            }
        });
        panelModeSelect.add(buttonSearchSet);

        panelModeInner.add(panelModeSelect);

        panelMode.add(panelModeInner);

        panelRedownload.setAlignmentX(0.0F);
        panelRedownload.setMaximumSize(new java.awt.Dimension(130, 32767));
        panelRedownload.setMinimumSize(new java.awt.Dimension(280, 30));
        panelRedownload.setPreferredSize(new java.awt.Dimension(280, 100));
        panelRedownload.setLayout(new java.awt.BorderLayout());

        checkboxRedownload.setText("<html>Re-download all images");
        checkboxRedownload.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        panelRedownload.add(checkboxRedownload, java.awt.BorderLayout.CENTER);
        panelRedownload.add(filler1, java.awt.BorderLayout.PAGE_END);

        panelMode.add(panelRedownload);

        tabMain.add(panelMode);
        tabMain.add(fillerMain1);

        panelProgress.setMaximumSize(new java.awt.Dimension(32777, 30));
        panelProgress.setMinimumSize(new java.awt.Dimension(20, 30));
        panelProgress.setPreferredSize(new java.awt.Dimension(564, 30));
        panelProgress.setLayout(new javax.swing.BoxLayout(panelProgress, javax.swing.BoxLayout.X_AXIS));
        panelProgress.add(fillerProgress1);

        progress.setValue(75);
        progress.setMaximumSize(new java.awt.Dimension(32767, 25));
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
        buttonOK.setPreferredSize(new java.awt.Dimension(120, 30));
        panelCommands.add(buttonOK);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.setText("Cancel");
        buttonCancel.setPreferredSize(new java.awt.Dimension(80, 30));
        panelCommands.add(buttonCancel);

        getContentPane().add(panelCommands, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void buttonSearchSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchSetActionPerformed
        FastSearchUtil.showFastSearchForStringComboBox(comboSets, FastSearchUtil.DEFAULT_EXPANSION_SEARCH_MESSAGE, 400, 500);
    }//GEN-LAST:event_buttonSearchSetActionPerformed

    private void buttonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonStopActionPerformed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
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

    private int returnStatus = RET_CANCEL;
}
