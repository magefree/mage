/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mage.remote.messages.responses;

import java.util.List;
import mage.view.TableView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author dev
 */
public class GetTablesResponse extends ClientMessage {
    
    private List<TableView> tables;
    
    public GetTablesResponse(List<TableView> tables) {
        this.tables = tables;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveTableViewList(tables);
    }
    
}
