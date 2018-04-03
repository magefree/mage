/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.dialog;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
//import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import mage.choices.Choice;
import mage.client.MageFrame;
import mage.client.util.SettingsManager;
import mage.client.util.gui.GuiDisplayUtil;
import mage.client.util.gui.MageDialogState;
import org.apache.log4j.helpers.LogLog;
import mage.client.dialog.CheckBoxList;
/**
 *
 * @author JayDi85
 */

public class PickChoiceDialog_1<T> extends MageDialog {

    Choice choice;
    ArrayList<KeyValueItem> allItems = new ArrayList<>();
    //ArrayList<?> allItems = new ArrayList<>();
    DefaultListModel<KeyValueItem> dataModel =new DefaultListModel();
    CheckBoxList.CheckBoxListModel m_dataModel;
   
    CheckBoxList tList;
    T tListChoices;
    
    final private static String HTML_TEMPLATE = "<html><div style='text-align: center;'>%s</div></html>";

    public void setList(T t) { this.tList = (CheckBoxList)t; }
    public T getList() { return (T)tList; }
    
    private void setFocus(T obj){
        //((JList)obj).setModel(dataModel);
        if (!(obj instanceof java.awt.Component)) {
        throw new IllegalArgumentException("Must be a java.awt.Component!");
      }
    this.scrollList.setViewportView((java.awt.Component)obj);
    }
    private javax.swing.JList castAsJList(Object anObject){
        return ((javax.swing.JList)anObject);
    }
    private javax.swing.JList get_a_Jlist_from_ScrollListView(){
        return ((javax.swing.JList)this.scrollList.getViewport().getView());
    }
    private void backupData(Object model){
        KeyValueItem item;
        int indexInTList; 
        String name;
        
        int maxLenght = this.allItems.size();//(((CheckBoxList.CheckBoxListModel)model).size());
        for(int index=0;index < maxLenght;index++){
            item= this.allItems.get(index);
            name=item.getKey();//((CheckBoxList.CheckBoxListModel)model).getElementAt(index).toString();
            indexInTList = ((CheckBoxList.CheckBoxListModel)model).indexOf(item.getKey());
            
            if(indexInTList>=0){
                
               
                //if(item.getKey().equals(((CheckBoxList.CheckBoxListModel)model).get(index).toString())){
                    if( !(item.getObjectValue().equals(((CheckBoxList.CheckBoxListModel)model).getElementAt(indexInTList)))){
                        LogLog.warn(String.format("%s change",((CheckBoxList.CheckBoxListModel)model).getElementAt(indexInTList).toString()));
                        //this.allItems.get(indexInTList)
                    }
                //}
            }
        }
    }
    private void restoreData(Object dataFrom){
       /* KeyValueItem item;
        int maxLenght = this.allItems.size();//((CheckBoxList.CheckBoxListModel)dataFrom).size());
        for(int index=0;index < maxLenght;index++){
            item= this.allItems.get(index);
            if(item.getKey().equals(((CheckBoxList.CheckBoxListModel)dataFrom).get(index).toString())){                
                if( !(item.getObjectValue().equals(((CheckBoxList.CheckBoxListModel)dataFrom).getElementAt(index)))){
                    LogLog.warn(String.format("%s change",((CheckBoxList.CheckBoxListModel)dataFrom).getElementAt(index).toString()));
                }
            }
        }
        */
        for(KeyValueItem item: this.allItems){
            ((CheckBoxList.CheckBoxListModel)dataFrom).addElement(item.getObjectValue());
        }
    }

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
        KeyValueItem tempKeyValue;
        int indexInTList;
                
        if (choice.isKeyChoice()){            
            for (Map.Entry<String, String> entry: choice.getKeyChoices().entrySet()) {
                
                if(tList != null){
                    /*for(int index=0;index<this.allItems.size();index++)
                    {
                        
                    }*/
                    indexInTList = m_dataModel.indexOf(entry.getKey());
                    tempKeyValue=new KeyValueItem(entry.getKey(), entry.getValue(),(CheckBoxList.CheckBoxListItem) this.tList.getModel().getElementAt(indexInTList));
                }
                else{
                    tempKeyValue=new KeyValueItem(entry.getKey(), entry.getValue());
                }
                this.allItems.add(tempKeyValue);  
                
            }
        } else {
            for (String value: choice.getChoices()){
                if(tList != null){
                    indexInTList = m_dataModel.indexOf(value);
                    tempKeyValue=new KeyValueItem(value, value,(CheckBoxList.CheckBoxListItem) tList.getModel().getElementAt(indexInTList));
                }
                else{
                    tempKeyValue=new KeyValueItem(value, value);
                }
                this.allItems.add(tempKeyValue);                
            }
        }

        // sorting
        if(choice.isSortEnabled()){
            Collections.sort(this.allItems, new Comparator<KeyValueItem>() {
                @Override
                public int compare(KeyValueItem o1, KeyValueItem o2) {
                    Integer n1 = choice.getSortData().get(o1.Key);
                    Integer n2 = choice.getSortData().get(o2.Key);
                    return n1.compareTo(n2);
                }
            });
        }
        
        // search
        if(choice.isSearchEnabled())
        {
            panelSearch.setVisible(true);
            this.editSearch.setText(choice.getSearchText());
        }else{
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
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    doPrevSelect();
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
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
                if(e.getClickCount() == 2){
                    doChoose();
                }
            }
        });
        
        // listeners for ESC close
        if(!choice.isRequired()){
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
        if (this.isModal()){
            MageFrame.getDesktop().add(this, JLayeredPane.MODAL_LAYER);
        }else{
            MageFrame.getDesktop().add(this, JLayeredPane.PALETTE_LAYER);
        }
        if (mageDialogState != null) {
            mageDialogState.setStateToDialog(this);
            
        } else {
            Point centered = SettingsManager.instance.getComponentPosition(getWidth(), getHeight());
            this.setLocation(centered.x, centered.y);
            GuiDisplayUtil.keepComponentInsideScreen(centered.x, centered.y, this);
        }

        // final load
        loadData();

        // start selection
        if((startSelectionValue != null)){
            int selectIndex = -1;
            
            for(int i = 0; i < this.get_a_Jlist_from_ScrollListView().getModel().getSize(); i++){               
               String elementOfList = this.get_a_Jlist_from_ScrollListView().getModel().getElementAt(i).toString();                
               
                if (elementOfList.equals(startSelectionValue)){
                    selectIndex = i;
                    break;
                }
            }

            if(selectIndex >= 0){                
                this.get_a_Jlist_from_ScrollListView().setSelectedIndex(selectIndex);                
                this.get_a_Jlist_from_ScrollListView().ensureIndexIsVisible(selectIndex);
            }
        }

        this.setVisible(true);
    }
    
    public void setWindowSize(int width, int heigth){
        this.setSize(new Dimension(width, heigth));
    }
    
    private void loadData(){
        // load data to datamodel after filter or on startup
        String filter = choice.getSearchText();
        if (filter == null){ filter = ""; }
        filter = filter.toLowerCase();
        
        backupData(this.m_dataModel);
        
        this.dataModel.clear();this.m_dataModel.clear();
        
        for(KeyValueItem item: this.allItems){
            if(!choice.isSearchEnabled() || item.Value.toLowerCase().contains(filter)){
                this.dataModel.addElement(item);this.m_dataModel.addElement(item.getObjectValue());
            }
        }
        if(this.tList!=null){
            //((javax.swing.JList)this.tList).setModel(dataModel);
            //new CheckBoxListModel();
            //((javax.swing.JList)this.scrollList.getViewport().getView()).setModel(dataModel);setListData(dataModel.toArray());
        }
        
    }
    
    private void setLabelText(JLabel label, String text){
        if ((text != null) && !text.equals("")){            
            label.setText(String.format(HTML_TEMPLATE, text));
            label.setVisible(true);
        }else{
            label.setText("");
            label.setVisible(false);
        }        
    }
    
    private void doNextSelect(){
        int newSel = this.listChoices.getSelectedIndex() + 1;
        int maxSel = this.listChoices.getModel().getSize() - 1;
        if(newSel <= maxSel){
            this.listChoices.setSelectedIndex(newSel);
            this.listChoices.ensureIndexIsVisible(newSel);
        }
    }
    
    private void doPrevSelect(){
        int newSel = this.listChoices.getSelectedIndex() - 1;        
        if(newSel >= 0){
            this.listChoices.setSelectedIndex(newSel);
            this.listChoices.ensureIndexIsVisible(newSel);
        }
    }

    private void doChoose(){        
        
        if((tList != null)||(setChoice())){
            this.m_dataModel.clear();
            restoreData(this.m_dataModel);
            this.hideDialog();
        }
       
        
    }
    
    private void doCancel(){
        this.listChoices.clearSelection();
        this.choice.clearChoice();
        hideDialog();
    }

    /**
     * Creates new form PickChoiceDialog_1
     * @param list 
     */
    public PickChoiceDialog_1(CheckBoxList list) {
        this();
        setList((T)list);
         //dataModel =  ((javax.swing.JList)tList).getModel();
        
        this.listChoices.setVisible(false);
        //tList.getModel()
        //this.tListChoices = new list.getClass();
        m_dataModel= ( CheckBoxList.CheckBoxListModel )tList.getModel();
        if(this.tList instanceof javax.swing.JList){
            setFocus((T)tList);       
            //((javax.swing.JList)tList).setModel(dataModel);
        }
       
       /* else if(tList instanceof javax.swing.JComboBox){
             ((javax.swing.JComboBox)tList).setModel(dataModel);
        }*/
          
        /*if((this.panelListChoices.getViewport().getView())!=null){       
            //((T)tList.setListData(dataModel.toArray());
        }*/
       /* if ((tList instanceof javax.swing.JList)) {
        //throw new IllegalArgumentException("Must be a java.awt.Component!");
      
        ((javax.swing.JList)tList).setModel(((javax.swing.ListModel)dataModel));
      } */
    }
    public PickChoiceDialog_1() {
        initComponents();
        tList=null;
        
        
        this.listChoices.setModel(dataModel);
        /*if((this.scrollList.getViewport().getView())!=null){       
            //((CheckBoxList)this.panelListChoices.getViewport().getView()).setListData(dataModel.toArray());
            ((javax.swing.JList)this.scrollList.getViewport().getView()).setListData(dataModel.toArray());
            
        }*/
        //test.setModel(dataModel);
        //item.setModel(dataModel);
        
        this.setModal(true);
    }
    
    public boolean setChoice() {        
        KeyValueItem item = (KeyValueItem)this.listChoices.getSelectedValue();        
        
        // auto select one item (after incemental filtering)
        if((item == null) && (this.listChoices.getModel().getSize() == 1)){
            this.listChoices.setSelectedIndex(0);
            item = (KeyValueItem)this.listChoices.getSelectedValue();
        }
        
        if(item != null){
            if(choice.isKeyChoice()){
                choice.setChoiceByKey(item.getKey());
            }else{
                choice.setChoice(item.getKey());
            }
            return true;
        }else{
            choice.clearChoice();
            return false;
        }
    }
    
    class KeyValueItem
    {
        private /*final*/ String Key;
        private final String Value;
        private final CheckBoxList.CheckBoxListItem objectValue;
        
        public KeyValueItem(String value) {
            this.Key = value;
            this.Value = null;
            this.objectValue = null;
        }
        public KeyValueItem(String value, String label) {
            this.Key = value;
            this.Value = label;
            this.objectValue = null;
        }
        public KeyValueItem(String value, String label,CheckBoxList.CheckBoxListItem object) {
            this.Key = value;
            this.Value = label;
            this.objectValue = object;            
        }

        public String getKey() {
            return this.Key;
        }
        public void setKey(String value) {
            this.Key= value;
        }

        public String getValue() {
            return this.Value;
        }
        
        public  Object getObjectValue(){
            return (CheckBoxList.CheckBoxListItem)this.objectValue;
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
                    .addComponent(labelMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(labelSubMessage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
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

        panelCommandsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btCancel, btOK});

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollList)
                    .addComponent(panelCommands, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelHeader, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(scrollList, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
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
