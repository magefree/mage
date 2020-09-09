package mage.client.dialog;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import mage.choices.Choice;
import mage.client.MageFrame;
import mage.client.util.gui.MageDialogState;

/**
 * @author JayDi85
 */
public class PickChoiceDialog extends MageDialog {

    Choice choice;
    java.util.List<KeyValueItem> allItems = new ArrayList<>();
    DefaultListModel<KeyValueItem> dataModel = new DefaultListModel<>();

    final private static String HTML_TEMPLATE = "<html><div style='text-align: center;'>%s</div></html>";

    public void showDialog(Choice choice) {
        showDialog(choice, null, null, null);
    }

    public void showDialog(Choice choice, String startSelectionValue) {
        showDialog(choice, null, null, startSelectionValue);
    }

    public void showDialog(Choice choice, UUID objectId, MageDialogState mageDialogState) {
        showDialog(choice, objectId, mageDialogState, null);
    }

    public void showDialog(Choice choice, UUID objectId, MageDialogState mageDialogState, String startSelectionValue) {
        this.choice = choice;

        setLabelText(this.labelMessage, choice.getMessage());
        setLabelText(this.labelSubMessage, choice.getSubMessage());

        btCancel.setEnabled(!choice.isRequired());

        // 2 modes: string or key-values
        // sore data in allItems for inremental filtering
        // http://logicbig.com/tutorials/core-java-tutorial/swing/list-filter/
        this.allItems.clear();
        if (choice.isKeyChoice()) {
            for (Map.Entry<String, String> entry : choice.getKeyChoices().entrySet()) {
                this.allItems.add(new KeyValueItem(entry.getKey(), entry.getValue()));
            }
        } else {
            for (String value : choice.getChoices()) {
                this.allItems.add(new KeyValueItem(value, value));
            }
        }

        // sorting
        if (choice.isSortEnabled()) {
            this.allItems.sort((o1, o2) -> {
                Integer n1 = choice.getSortData().get(o1.Key);
                Integer n2 = choice.getSortData().get(o2.Key);
                return n1.compareTo(n2);
            });
        }

        // search
        if (choice.isSearchEnabled()) {
            panelSearch.setVisible(true);
            this.editSearch.setText(choice.getSearchText());
        } else {
            panelSearch.setVisible(false);
            this.editSearch.setText("");
        }

        // listeners for inremental filtering
        editSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                choice.setSearchText(editSearch.getText());
                loadData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                choice.setSearchText(editSearch.getText());
                loadData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                choice.setSearchText(editSearch.getText());
                loadData();
            }
        });

        // listeners for select up and down without edit focus lost
        editSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //System.out.println("types");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    doPrevSelect();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    doNextSelect();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("released");
            }
        });

        // listeners double click choose
        listChoices.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    doChoose();
                }
            }
        });

        // listeners for ESC close
        if (!choice.isRequired()) {
            String cancelName = "cancel";
            InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
            ActionMap actionMap = getRootPane().getActionMap();
            actionMap.put(cancelName, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    doCancel();
                }
            });
        }

        // window settings
        MageFrame.getDesktop().remove(this);
        if (this.isModal()) {
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        } else {
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        }
        if (mageDialogState != null) {
            mageDialogState.setStateToDialog(this);
        } else {
            this.makeWindowCentered();
        }

        // final load
        loadData();

        // start selection
        if ((startSelectionValue != null)) {
            int selectIndex = -1;
            for (int i = 0; i < this.listChoices.getModel().getSize(); i++) {
                KeyValueItem listItem = (KeyValueItem) this.listChoices.getModel().getElementAt(i);
                if (listItem.Key.equals(startSelectionValue)) {
                    selectIndex = i;
                    break;
                }
            }

            if (selectIndex >= 0) {
                this.listChoices.setSelectedIndex(selectIndex);
                this.listChoices.ensureIndexIsVisible(selectIndex);
            }
        }

        this.setVisible(true);
    }

    public void setWindowSize(int width, int height) {
        this.setSize(new Dimension(width, height));
    }

    private void loadData() {
        // load data to datamodel after filter or on startup
        String filter = choice.getSearchText();
        if (filter == null) {
            filter = "";
        }
        filter = filter.toLowerCase(Locale.ENGLISH);

        this.dataModel.clear();
        for (KeyValueItem item : this.allItems) {
            if (!choice.isSearchEnabled() || item.Value.toLowerCase(Locale.ENGLISH).contains(filter)) {
                this.dataModel.addElement(item);
            }
        }
    }

    private void setLabelText(JLabel label, String text) {
        if ((text != null) && !text.equals("")) {
            label.setText(String.format(HTML_TEMPLATE, text));
            label.setVisible(true);
        } else {
            label.setText("");
            label.setVisible(false);
        }
    }

    private void doNextSelect() {
        int newSel = this.listChoices.getSelectedIndex() + 1;
        int maxSel = this.listChoices.getModel().getSize() - 1;
        if (newSel <= maxSel) {
            this.listChoices.setSelectedIndex(newSel);
            this.listChoices.ensureIndexIsVisible(newSel);
        }
    }

    private void doPrevSelect() {
        int newSel = this.listChoices.getSelectedIndex() - 1;
        if (newSel >= 0) {
            this.listChoices.setSelectedIndex(newSel);
            this.listChoices.ensureIndexIsVisible(newSel);
        }
    }

    private void doChoose() {
        if (setChoice()) {
            this.hideDialog();
        }
    }

    private void doCancel() {
        this.listChoices.clearSelection();
        this.choice.clearChoice();
        hideDialog();
    }

    /**
     * Creates new form PickChoiceDialog
     */
    public PickChoiceDialog() {
        initComponents();
        this.listChoices.setModel(dataModel);
        this.setModal(true);
    }

    public boolean setChoice() {
        KeyValueItem item = (KeyValueItem) this.listChoices.getSelectedValue();

        // auto select one item (after incemental filtering)
        if ((item == null) && (this.listChoices.getModel().getSize() == 1)) {
            this.listChoices.setSelectedIndex(0);
            item = (KeyValueItem) this.listChoices.getSelectedValue();
        }

        if (item != null) {
            if (choice.isKeyChoice()) {
                choice.setChoiceByKey(item.getKey());
            } else {
                choice.setChoice(item.getKey());
            }
            return true;
        } else {
            choice.clearChoice();
            return false;
        }
    }

    class KeyValueItem {

        private final String Key;
        private final String Value;

        public KeyValueItem(String value, String label) {
            this.Key = value;
            this.Value = label;
        }

        public String getKey() {
            return this.Key;
        }

        public String getValue() {
            return this.Value;
        }

        @Override
        public String toString() {
            return this.Value;
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

        panelHeader = new javax.swing.JPanel();
        labelMessage = new javax.swing.JLabel();
        labelSubMessage = new javax.swing.JLabel();
        panelSearch = new javax.swing.JPanel();
        labelSearch = new javax.swing.JLabel();
        editSearch = new javax.swing.JTextField();
        scrollList = new javax.swing.JScrollPane();
        listChoices = new javax.swing.JList();
        panelCommands = new javax.swing.JPanel();
        btOK = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();

        setResizable(true);

        labelMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMessage.setText("<html><div style='text-align: center;'>example long message example long message example long message example long message example long message</div></html>");

        labelSubMessage.setFont(labelSubMessage.getFont().deriveFont((labelSubMessage.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD));
        labelSubMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSubMessage.setText("<html><div style='text-align: center;'>example long message example long</div></html>");

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                    .addComponent(labelSubMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(labelMessage)
                .addGap(0, 0, 0)
                .addComponent(labelSubMessage))
        );

        labelSearch.setText("Search:");

        editSearch.setText("sample search text");

        javax.swing.GroupLayout panelSearchLayout = new javax.swing.GroupLayout(panelSearch);
        panelSearch.setLayout(panelSearchLayout);
        panelSearchLayout.setHorizontalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSearchLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(labelSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editSearch)
                .addGap(0, 0, 0))
        );
        panelSearchLayout.setVerticalGroup(
            panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSearchLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(panelSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSearch)
                    .addComponent(editSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        listChoices.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "item1", "item2", "item3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        scrollList.setViewportView(listChoices);

        btOK.setText("Choose");
        btOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOKActionPerformed(evt);
            }
        });

        btCancel.setText("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCommandsLayout = new javax.swing.GroupLayout(panelCommands);
        panelCommands.setLayout(panelCommandsLayout);
        panelCommandsLayout.setHorizontalGroup(
            panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCommandsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btOK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelCommandsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, btCancel, btOK);

        panelCommandsLayout.setVerticalGroup(
            panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCommandsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelCommandsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancel)
                    .addComponent(btOK))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(btOK);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollList, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelCommands, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollList, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCommands, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOKActionPerformed
        doChoose();
    }//GEN-LAST:event_btOKActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        doCancel();
    }//GEN-LAST:event_btCancelActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doCancel();
    }//GEN-LAST:event_closeDialog

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btOK;
    private javax.swing.JTextField editSearch;
    private javax.swing.JLabel labelMessage;
    private javax.swing.JLabel labelSearch;
    private javax.swing.JLabel labelSubMessage;
    private javax.swing.JList listChoices;
    private javax.swing.JPanel panelCommands;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelSearch;
    private javax.swing.JScrollPane scrollList;
    // End of variables declaration//GEN-END:variables
}
