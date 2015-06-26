package org.mage.network.messages.responses;

import mage.view.TableView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class CreateTableResponse extends ClientMessage {
    
    private TableView table;
    
    public CreateTableResponse(TableView table) {
        this.table = table;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveTableView(table);
    }
    
}
