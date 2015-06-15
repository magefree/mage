package org.mage.network.model;

import mage.view.TableView;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public class TableWaitingMessage extends ClientMessage {
    
    private TableView table;
    
    public TableWaitingMessage(TableView table) {
        this.table = table;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveTableView(table);
    }
    
}
