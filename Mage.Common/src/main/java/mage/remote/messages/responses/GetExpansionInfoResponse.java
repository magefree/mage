/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.remote.messages.responses;

import java.util.List;
import mage.cards.repository.ExpansionInfo;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author LevelX2
 */
public class GetExpansionInfoResponse extends ClientMessage {

    private final List<ExpansionInfo> expansionInfo;

    public GetExpansionInfoResponse(List<ExpansionInfo> expansionInfo) {
        this.expansionInfo = expansionInfo;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveExpansionInfoList(expansionInfo);
    }

}
