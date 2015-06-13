/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */

package mage.client.util.gui.countryBox;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import mage.client.chat.ChatPanel;
import org.apache.log4j.Logger;

/**
 *
 * @author LevelX2
 */
public class CountryCellRenderer extends DefaultTableCellRenderer {
    
    private static final Logger logger = Logger.getLogger(CountryCellRenderer.class);
    private final Map<String, ImageIcon> flagIconCache = new HashMap<>();

    private final Map<String, String> countryMap = new HashMap<>();
    
    public CountryCellRenderer() {        
        for( int i = 0; i <= CountryComboBox.countryList.length - 1; i++) {
            countryMap.put(CountryComboBox.countryList[i][1],CountryComboBox.countryList[i][0]);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	if(column == 0) {
            label.setToolTipText(countryMap.get((String)value));
            label.setIcon(getCountryFlagIcon((String)value));  
            label.setText("");
    	}
    	return label;
    }
    
    private ImageIcon getCountryFlagIcon(String countryCode) {
        ImageIcon flagIcon = flagIconCache.get(countryCode);
        if (flagIcon == null) {
            flagIcon = new javax.swing.ImageIcon(getClass().getResource("/flags/" + countryCode +".png"));
            if (flagIcon.getImage() == null) {
                logger.warn("Country flag resource not found: " + countryCode);
            } else {
                flagIconCache.put(countryCode, flagIcon);
            }
        }
        return flagIcon;
    }    
}
