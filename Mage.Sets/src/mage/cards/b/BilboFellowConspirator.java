package mage.cards.b;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class BilboFellowConspirator extends CardImpl {

    public BilboFellowConspirator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // If you would create a Food token, instead create a Food token and a Treasure token.
        this.addAbility(new SimpleStaticAbility(new BilboFellowConspiratorEffect()));
    }

    private BilboFellowConspirator(final BilboFellowConspirator card) {
        super(card);
    }

    @Override
    public BilboFellowConspirator copy() {
        return new BilboFellowConspirator(this);
    }
}

class BilboFellowConspiratorEffect extends ReplacementEffectImpl {

    BilboFellowConspiratorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If you would create a Food token, instead create a Food token and a Treasure token.";
    }

    private BilboFellowConspiratorEffect(final BilboFellowConspiratorEffect effect) {
        super(effect);
    }

    @Override
    public BilboFellowConspiratorEffect copy() {
        return new BilboFellowConspiratorEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof CreateTokenEvent) || !event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        for (Token token : ((CreateTokenEvent) event).getTokens().keySet()) {
            if (token.hasSubtype(SubType.FOOD, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = 0;
        Map<Token, Integer> tokens = ((CreateTokenEvent) event).getTokens();
        for (Iterator<Map.Entry<Token, Integer>> iter = tokens.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<Token, Integer> entry = iter.next();
            Token token = entry.getKey();
            if (token.hasSubtype(SubType.FOOD, game)) {
                amount += entry.getValue();
                iter.remove();
            }
        }

        tokens.put(new FoodToken(), amount);
        tokens.put(new TreasureToken(), amount);
        return false;
    }
}
