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
package mage.client.util.gui.countryBox;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;


/**
 * Editor for JComboBox
 * @author wwww.codejava.net, JayDi85
 *
 */
public class CountryItemEditor extends BasicComboBoxEditor {
    private final JPanel panel = new JPanel();
    private final JLabel labelItem = new JLabel();
    private String[] editValue = new String[2];
     
    public CountryItemEditor() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(0, 100,190, 255));

        panel.add(labelItem);
        labelItem.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelItem.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        labelItem.setBorder(new EmptyBorder(0, 5, 0, 0));
        labelItem.setOpaque(false);
        labelItem.setForeground(Color.WHITE);

        editValue = null;
    }
     
    @Override
    public Component getEditorComponent() {
        return this.panel;
    }
     
    @Override
    public Object getItem() {
        return this.editValue;
    }
     
    public String getImageItem() {
        return this.editValue[1];
    }

    @Override
    public void setItem(Object item) {
        if (item == null || !(item instanceof String[])) {
            return;
        }

        String[] newItem = (String[])item;

        editValue = new String[2];
        editValue[0] = newItem[0];
        editValue[1] = newItem[1];

        labelItem.setText(editValue[0]);
        labelItem.setIcon(new ImageIcon(getClass().getResource("/flags/"+ editValue[1] + ".png")));
    }  
}
