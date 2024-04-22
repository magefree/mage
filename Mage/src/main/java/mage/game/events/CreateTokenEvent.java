package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.token.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateTokenEvent extends GameEvent {

    private final Map<Token, Integer> tokens = new HashMap<>();

    /**
     * Multiple tokens per event (Double Season and other effects can change amount and tokens list with it)
     *
     * @param source
     * @param controllerId
     * @param amount
     * @param token
     */
    public CreateTokenEvent(Ability source, UUID controllerId, int amount, Token token) {
        super(GameEvent.EventType.CREATE_TOKEN, null, source, controllerId, amount, false);
        tokens.put(token, amount);
    }

    public Map<Token, Integer> getTokens() {
        return tokens;
    }

    public void multiplyTokens(int factor) {
        multiplyTokens(factor, null);
    }

    public interface ConditionOnToken {
        boolean apply(Token token);
    }

    public void multiplyTokens(int factor, ConditionOnToken condition) {
        for (Map.Entry<Token, Integer> entry : tokens.entrySet()) {
            if (condition == null || condition.apply(entry.getKey())) {
                entry.setValue(entry.getValue() * factor);
            }
        }
    }

    @Override
    public int getAmount() {
        int amount = 0;
        for (Integer num : tokens.values()) {
            amount += num;
        }
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        throw new UnsupportedOperationException("Do not use event.setAmount for tokens. Amount must be set individually in event.getTokens");
    }
}
