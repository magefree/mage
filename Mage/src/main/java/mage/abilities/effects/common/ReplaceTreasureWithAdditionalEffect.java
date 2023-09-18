package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;

import java.util.Map;

/**
 *
 * @author weirddan455
 */
public class ReplaceTreasureWithAdditionalEffect extends ReplacementEffectImpl {
    public ReplaceTreasureWithAdditionalEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If you would create one or more Treasure tokens, instead create those tokens plus an additional Treasure token";
    }

    private ReplaceTreasureWithAdditionalEffect(final ReplaceTreasureWithAdditionalEffect effect) {
        super(effect);
    }

    @Override
    public ReplaceTreasureWithAdditionalEffect copy() {
        return new ReplaceTreasureWithAdditionalEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof CreateTokenEvent) || !source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        return ((CreateTokenEvent) event).getTokens().keySet().stream()
                .anyMatch(token -> token.hasSubtype(SubType.TREASURE, game));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent) {
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            TreasureToken treasureToken = null;
            Map<Token, Integer> tokens = tokenEvent.getTokens();
            for (Token token : tokens.keySet()) {
                if (token instanceof TreasureToken) {
                    treasureToken = (TreasureToken) token;
                    break;
                }
            }
            if (treasureToken == null) {
                treasureToken = new TreasureToken();
            }
            tokens.put(treasureToken, tokens.getOrDefault(treasureToken, 0) + 1);
        }
        return false;
    }
}
