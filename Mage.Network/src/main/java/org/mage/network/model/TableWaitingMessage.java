package org.mage.network.model;

import java.io.Serializable;
import mage.view.TableView;

/**
 *
 * @author BetaSteward
 */
public class TableWaitingMessage implements Serializable {
    
    private TableView table;
    
    public TableWaitingMessage(TableView table) {
        this.table = table;
    }
    
    public TableView getRoom() {
        return table;
    }
    
}
