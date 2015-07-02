package org.mage.network.messages.responses;

import mage.view.TableView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class TableViewResponse extends ClientMessage {
    
    private TableView view;
    
    public TableViewResponse(TableView view) {
        this.view = view;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveTableView(view);
    }
    
}
