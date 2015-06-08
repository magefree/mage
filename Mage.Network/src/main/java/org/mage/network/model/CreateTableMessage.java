package org.mage.network.model;

import java.io.Serializable;
import mage.view.TableView;

/**
 *
 * @author BetaSteward
 */
public class CreateTableMessage implements Serializable {
    
    private TableView table;
    
    public CreateTableMessage(TableView table) {
        this.table = table;
    }
    
    public TableView getTable() {
        return table;
    }
    
}
