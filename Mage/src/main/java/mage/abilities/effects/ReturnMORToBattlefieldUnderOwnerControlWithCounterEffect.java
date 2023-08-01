package mage.abilities.effects;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.MeldCard;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;

public class ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect extends OneShotEffect {

    private final MageObjectReference objectToReturn;
    private final Set<Counter> counters;
    private final String counterText;

    public ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect(
            MageObjectReference objectToReturn,
            Set<Counter> counters,
            String counterText
    ) {
        super(Outcome.PutCardInPlay);
        this.objectToReturn = objectToReturn;
        this.counters = new HashSet<>(counters);
        this.counterText = counterText;
        this.staticText = "return that card to the battlefield with " + counterText + " on it";
    }

    private ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect(
            final ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect effect
    ) {
        super(effect);
        this.objectToReturn = effect.objectToReturn;
        this.counters = new HashSet<>(effect.counters);
        this.counterText = effect.counterText;
    }

    @Override
    public ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect copy() {
        return new ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(objectToReturn.getSourceId());
        if (card != null && objectToReturn.refersTo(card, game)) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner != null) {
                if (card instanceof MeldCard) {
                    MeldCard meldCard = (MeldCard) card;
                    game.addEffect(new ReturnMOREntersBattlefieldEffect(
                            new MageObjectReference(meldCard.getTopHalfCard(), game),
                            counters,
                            counterText
                    ), source);
                    game.addEffect(new ReturnMOREntersBattlefieldEffect(
                            new MageObjectReference(meldCard.getBottomHalfCard(), game),
                            counters,
                            counterText
                    ), source);
                } else {
                    game.addEffect(new ReturnMOREntersBattlefieldEffect(objectToReturn, counters, counterText), source);
                }
                owner.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
        }
        return true;
    }
}

class ReturnMOREntersBattlefieldEffect extends ReplacementEffectImpl {

    private final MageObjectReference objectToReturn;
    private final Set<Counter> counters;

    public ReturnMOREntersBattlefieldEffect(MageObjectReference objectToReturn, Set<Counter> counters, String counterText) {
        super(Duration.EndOfStep, Outcome.BoostCreature);
        this.objectToReturn = objectToReturn;
        this.counters = new HashSet<>(counters);
        this.staticText = "that card to the battlefield with " + counterText + " on it";
    }

    private ReturnMOREntersBattlefieldEffect(final ReturnMOREntersBattlefieldEffect effect) {
        super(effect);
        this.objectToReturn = effect.objectToReturn;
        this.counters = new HashSet<>(effect.counters);
    }

    @Override
    public ReturnMOREntersBattlefieldEffect copy() {
        return new ReturnMOREntersBattlefieldEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return event.getTargetId().equals(objectToReturn.getSourceId());
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            counters.forEach(counter ->
                    permanent.addCounters(counter, source.getControllerId(), source, game, event.getAppliedEffects())
            );
            discard(); // use only once
        }
        return false;
    }
}

