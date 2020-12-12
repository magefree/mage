package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.token.Token;

import java.util.UUID;

public class CreateTokenEvent extends GameEvent {

    private Token token;

    public CreateTokenEvent(Ability source, UUID controllerId, int amount, Token token) {
        super(GameEvent.EventType.CREATE_TOKEN, null, source, controllerId, amount, false);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
