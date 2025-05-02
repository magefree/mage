package mage.client.dialog;

import mage.client.cards.BigCard;
import mage.client.components.MageTextArea;
import mage.constants.ColoredManaSymbol;
import mage.util.MultiAmountMessage;

import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Game GUI: dialog to distribute values between multiple items
 * (used for some cards and add lands in cheat menu, search by MultiAmountMessage)
 *
 * @author weirddan455, JayDi85
 */
public class PickMultiNumberDialog extends MageDialog {

    private boolean cancel;
    private PickMultiNumberCallback callback = null;

    private UUID gameId = null;
    private BigCard bigCard = null;
    
    private List<MageTextArea> infoList = null;
    private List<JSpinner> spinnerList = null;

    public PickMultiNumberDialog() {
        initComponents();
        this.setModal(true);
    }

    public interface PickMultiNumberCallback {
        void onChoiceDone();
    }

    public void init(UUID gameId, BigCard bigCard) {
        this.gameId = gameId;
        this.bigCard = bigCard;
    }

    public void showDialog(List<MultiAmountMessage> messages, int min, int max, Map<String, Serializable> options, PickMultiNumberCallback callback) {
        this.cancel = false;
        this.callback = callback;

        this.header.setText("<html>" + ManaSymbols.replaceSymbolsWithHTML((String) options.get("header"), ManaSymbols.Type.DIALOG));
        this.setTitle((String) options.get("title"));

        boolean canCancel = options.get("canCancel") != null && (boolean) options.get("canCancel");
        btnCancel.setVisible(canCancel);

        // clean
        if (infoList != null) {
            for (MageTextArea info : infoList) {
                jPanel1.remove(info);
            }
        }
        if (spinnerList != null) {
            for (JSpinner spinner : spinnerList) {
                jPanel1.remove(spinner);
            }
        }

        int size = messages.size();
        infoList = new ArrayList<>(size);
        spinnerList = new ArrayList<>(size);
        jPanel1.setLayout(new GridBagLayout());
        for (int i = 0; i < size; i++) {
            MageTextArea info = new MageTextArea();
            info.enableTextLabelMode();
            info.setGameData(this.gameId, this.bigCard);

            // mana mode
            String manaText = null;
            String input = messages.get(i).message;
            switch (input) {
                case "W":
                    manaText = ColoredManaSymbol.W.getColorHtmlName();
                    break;
                case "U":
                    manaText = ColoredManaSymbol.U.getColorHtmlName();
                    break;
                case "B":
                    manaText = ColoredManaSymbol.B.getColorHtmlName();
                    break;
                case "R":
                    manaText = ColoredManaSymbol.R.getColorHtmlName();
                    break;
                case "G":
                    manaText = ColoredManaSymbol.G.getColorHtmlName();
                    break;
            }
            if (manaText != null) {
                // mana mode
                info.setText("{" + input + "}" + "&nbsp;" + manaText);
            } else {
                // text mode
                info.setText(input);
            }

            GridBagConstraints infoC = new GridBagConstraints();
            infoC.weightx = 0.5;
            infoC.gridx = 0;
            infoC.gridy = i;
            jPanel1.add(info, infoC);
            infoList.add(info);

            JSpinner spinner = new JSpinner();
            spinner.setModel(new SpinnerNumberModel(messages.get(i).defaultValue, messages.get(i).min, messages.get(i).max, 1));
            GridBagConstraints spinnerC = new GridBagConstraints();
            spinnerC.weightx = 0.5;
            spinnerC.gridx = 1;
            spinnerC.gridy = i;
            spinnerC.ipadx = 20;
            spinner.addChangeListener(e -> {
                updateControls(min, max, messages);
            });
            jPanel1.add(spinner, spinnerC);
            spinnerList.add(spinner);
        }
        this.counterText.setText("0 out of 0");

        updateControls(min, max, messages);

        this.pack();
        this.makeWindowCentered();
        this.setVisible(true);
    }

    private void updateControls(int min, int max, List<MultiAmountMessage> messages) {
        int totalChosenAmount = 0;
        boolean chooseEnabled = true;

        for (int i = 0; i < spinnerList.size(); i++) {
            JSpinner jSpinner = spinnerList.get(i);
            int value = ((Number) jSpinner.getValue()).intValue();
            totalChosenAmount += value;

            chooseEnabled &= value >= messages.get(i).min && value <= messages.get(i).max;
        }
        counterText.setText(totalChosenAmount + " out of " + max);

        chooseEnabled &= totalChosenAmount >= min && totalChosenAmount <= max;
        btnOk.setEnabled(chooseEnabled);
    }

    public String getMultiAmount() {
        return spinnerList
                .stream()
                .map(spinner -> ((Number) spinner.getValue()).intValue())
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }
    
    public boolean isCancel() {
        return cancel;
    }

    private void doClose() {
        this.hideDialog();
        if (this.callback != null) {
            this.callback.onChoiceDone();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        panelCommands = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        panelHeader = new javax.swing.JPanel();
        header = new javax.swing.JLabel();
        counterText = new javax.swing.JLabel();

        getContentPane().setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 598, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 828, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        btnOk.setText("Choose");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCommandsLayout = new javax.swing.GroupLayout(panelCommands);
        panelCommands.setLayout(panelCommandsLayout);
        panelCommandsLayout.setHorizontalGroup(
            panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCommandsLayout.createSequentialGroup()
                .addContainerGap(257, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap())
        );
        panelCommandsLayout.setVerticalGroup(
            panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCommandsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(btnOk);

        getContentPane().add(panelCommands, java.awt.BorderLayout.SOUTH);

        panelHeader.setLayout(new java.awt.BorderLayout());

        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("Header 12312321312312");
        panelHeader.add(header, java.awt.BorderLayout.NORTH);

        counterText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        counterText.setText("Counter 213123 213 213123213 123 123213123123123");
        panelHeader.add(counterText, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panelHeader, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        this.cancel = false;
        doClose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.cancel = true;
        doClose();
    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel counterText;
    private javax.swing.JLabel header;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelCommands;
    private javax.swing.JPanel panelHeader;
    // End of variables declaration//GEN-END:variables
}
