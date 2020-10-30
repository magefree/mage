package mage.game.events;

import mage.game.permanent.token.Token;

import java.util.UUID;

public class CreateTokenEvent extends GameEvent {
    private Token token;

    public CreateTokenEvent(UUID sourceId, UUID controllerId, int amount, Token token) {
        super(EventType.CREATE_TOKEN, null, sourceId, controllerId, amount, false);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
