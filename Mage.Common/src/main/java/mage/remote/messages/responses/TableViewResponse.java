package mage.remote.messages.responses;

import mage.view.TableView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class TableViewResponse extends ClientMessage {
    
    private final TableView view;
    
    public TableViewResponse(TableView view) {
        this.view = view;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveTableView(view);
    }
    
}
