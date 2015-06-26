package org.mage.network.messages.responses;

import mage.view.TableView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class CreateTableMessage extends ClientMessage {
    
    private TableView table;
    
    public CreateTableMessage(TableView table) {
        this.table = table;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveTableView(table);
    }
    
}
