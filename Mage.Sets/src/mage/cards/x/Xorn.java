package mage.cards.x;

import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author weirddan455
 */
public final class Xorn extends CardImpl {

    public Xorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // If you would create one or more Treasure tokens, instead create those tokens plus an additional Treasure token.
        this.addAbility(new SimpleStaticAbility(new XornReplacementEffect()));
    }

    private Xorn(final Xorn card) {
        super(card);
    }

    @Override
    public Xorn copy() {
        return new Xorn(this);
    }
}

class XornReplacementEffect extends ReplacementEffectImpl {

    public XornReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If you would create one or more Treasure tokens, instead create those tokens plus an additional Treasure token";
    }

    private XornReplacementEffect(final XornReplacementEffect effect) {
        super(effect);
    }

    @Override
    public XornReplacementEffect copy() {
        return new XornReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent && source.isControlledBy(event.getPlayerId())) {
            for (Token token : ((CreateTokenEvent) event).getTokens().keySet()) {
                if (token.hasSubtype(SubType.TREASURE, game)) {
                    return true;
                }
            }
        }
        return false;
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
