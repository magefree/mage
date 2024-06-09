package mage.client.dialog;

import mage.constants.ColoredManaSymbol;
import mage.util.MultiAmountMessage;

import org.mage.card.arcane.ManaSymbols;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author weirddan455
 */
public class PickMultiNumberDialog extends MageDialog {

    private boolean cancel;
    
    private List<JLabel> labelList = null;
    private List<JSpinner> spinnerList = null;

    public PickMultiNumberDialog() {
        initComponents();
        this.setModal(true);
    }

    public void showDialog(List<MultiAmountMessage> messages, int min, int max, Map<String, Serializable> options) {
        this.header.setText("<html>" + ManaSymbols.replaceSymbolsWithHTML((String) options.get("header"), ManaSymbols.Type.DIALOG));
        this.setTitle((String) options.get("title"));

        boolean canCancel = options.get("canCancel") != null && (boolean) options.get("canCancel");
        btnCancel.setVisible(canCancel);

        if (labelList != null) {
            for (JLabel label : labelList) {
                jPanel1.remove(label);
            }
        }
        if (spinnerList != null) {
            for (JSpinner spinner : spinnerList) {
                jPanel1.remove(spinner);
            }
        }
        int size = messages.size();
        labelList = new ArrayList<>(size);
        spinnerList = new ArrayList<>(size);
        jPanel1.setLayout(new GridBagLayout());
        GridBagConstraints labelC = new GridBagConstraints();
        GridBagConstraints spinnerC = new GridBagConstraints();
        for (int i = 0; i < size; i++) {
            JLabel label = new JLabel();

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
                label.setText("<html>" + manaText);
                Image image = ManaSymbols.getSizedManaSymbol(input);
                if (image != null) {
                    label.setIcon(new ImageIcon(image));
                }
            } else {
                // text mode
                label.setText("<html>" + ManaSymbols.replaceSymbolsWithHTML(input, ManaSymbols.Type.DIALOG));
            }

            labelC.weightx = 0.5;
            labelC.gridx = 0;
            labelC.gridy = i;
            jPanel1.add(label, labelC);
            labelList.add(label);

            JSpinner spinner = new JSpinner();
            spinner.setModel(new SpinnerNumberModel(messages.get(i).defaultValue, messages.get(i).min, messages.get(i).max, 1));
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
        this.hideDialog();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.cancel = true;
        this.hideDialog();
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
