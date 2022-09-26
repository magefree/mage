/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.remote.messages.responses;

import java.util.List;
import mage.cards.repository.CardInfo;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author LevelX2
 */
public class GetCardInfoResponse extends ClientMessage {

    private final List<CardInfo> cardInfo;

    public GetCardInfoResponse(List<CardInfo> cardInfo) {
        this.cardInfo = cardInfo;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveCardInfoList(cardInfo);
    }

}
