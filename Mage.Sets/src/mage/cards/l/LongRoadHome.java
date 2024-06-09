
package mage.cards.l;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LongRoadHome extends CardImpl {

    public LongRoadHome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new LongRoadHomeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LongRoadHome(final LongRoadHome card) {
        super(card);
    }

    @Override
    public LongRoadHome copy() {
        return new LongRoadHome(this);
    }
}

class LongRoadHomeEffect extends OneShotEffect {

    private static final String effectText = "Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it";

    LongRoadHomeEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    private LongRoadHomeEffect(final LongRoadHomeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Long Road Home", source, game)) {
                ExileZone exile = game.getExile().getExileZone(source.getSourceId());
                // only if permanent is in exile (tokens would be stop to exist)
                if (exile != null && !exile.isEmpty()) {
                    Card card = game.getCard(permanent.getId());
                    if (card != null) {
                        //create delayed triggered ability
                        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                                new ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect(
                                        new MageObjectReference(card, game),
                                        CounterType.P1P1.createInstance(),
                                        "a +1/+1 counter"
                                )
                        ), source);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public LongRoadHomeEffect copy() {
        return new LongRoadHomeEffect(this);
    }

}