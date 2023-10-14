
package mage.cards.o;

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
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class OtherworldlyJourney extends CardImpl {

    public OtherworldlyJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.subtype.add(SubType.ARCANE);

        // Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new OtherworldlyJourneyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private OtherworldlyJourney(final OtherworldlyJourney card) {
        super(card);
    }

    @Override
    public OtherworldlyJourney copy() {
        return new OtherworldlyJourney(this);
    }

}

class OtherworldlyJourneyEffect extends OneShotEffect {

    private static final String effectText = "Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it";

    OtherworldlyJourneyEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    private OtherworldlyJourneyEffect(final OtherworldlyJourneyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Otherworldly Journey", source, game)) {
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
    public OtherworldlyJourneyEffect copy() {
        return new OtherworldlyJourneyEffect(this);
    }

}