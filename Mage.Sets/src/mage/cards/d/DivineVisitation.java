package mage.cards.d;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.AngelVigilanceToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author TheElk801
 */
public final class DivineVisitation extends CardImpl {

    public DivineVisitation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // If one or more creature tokens would be created under your control, 
        // that many 4/4 white Angel creature tokens with flying and 
        // vigilance are created instead.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new DivineVisitationEffect()
        ));
    }

    private DivineVisitation(final DivineVisitation card) {
        super(card);
    }

    @Override
    public DivineVisitation copy() {
        return new DivineVisitation(this);
    }
}

class DivineVisitationEffect extends ReplacementEffectImpl {

    public DivineVisitationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        staticText = "If one or more creature tokens would be created "
                + "under your control, that many 4/4 white Angel creature "
                + "tokens with flying and vigilance are created instead.";
    }

    private DivineVisitationEffect(final DivineVisitationEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent && event.getPlayerId().equals(source.getControllerId())) {
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            for (Token token : tokenEvent.getTokens().keySet()) {
                if (token.isCreature(game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent) {
            int amount = 0;
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            Iterator<Map.Entry<Token, Integer>> it = tokenEvent.getTokens().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Token, Integer> entry = it.next();
                if (entry.getKey().isCreature(game)) {
                    amount += entry.getValue();
                    it.remove();
                }
            }
            if (amount > 0) {
                tokenEvent.getTokens().put(new AngelVigilanceToken(), amount);
            }
        }
        return false;
    }

    @Override
    public DivineVisitationEffect copy() {
        return new DivineVisitationEffect(this);
    }

}
