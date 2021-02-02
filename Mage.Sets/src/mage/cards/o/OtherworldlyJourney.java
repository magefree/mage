
package mage.cards.o;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

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

    OtherworldlyJourneyEffect(OtherworldlyJourneyEffect effect) {
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
                        DelayedTriggeredAbility delayedAbility
                                = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new OtherworldlyJourneyReturnFromExileEffect(new MageObjectReference(card, game)));
                        game.addDelayedTriggeredAbility(delayedAbility, source);
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

class OtherworldlyJourneyReturnFromExileEffect extends OneShotEffect {

    MageObjectReference objectToReturn;

    public OtherworldlyJourneyReturnFromExileEffect(MageObjectReference objectToReturn) {
        super(Outcome.PutCardInPlay);
        this.objectToReturn = objectToReturn;
        staticText = "return that card to the battlefield under its owner's control with a +1/+1 counter on it";
    }

    public OtherworldlyJourneyReturnFromExileEffect(final OtherworldlyJourneyReturnFromExileEffect effect) {
        super(effect);
        this.objectToReturn = effect.objectToReturn;
    }

    @Override
    public OtherworldlyJourneyReturnFromExileEffect copy() {
        return new OtherworldlyJourneyReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(objectToReturn.getSourceId());
        if (card != null && objectToReturn.refersTo(card, game)) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner != null) {
                game.addEffect(new OtherworldlyJourneyEntersBattlefieldEffect(objectToReturn), source);
                owner.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
        }
        return true;
    }
}

class OtherworldlyJourneyEntersBattlefieldEffect extends ReplacementEffectImpl {

    MageObjectReference objectToReturn;

    public OtherworldlyJourneyEntersBattlefieldEffect(MageObjectReference objectToReturn) {
        super(Duration.Custom, Outcome.BoostCreature);
        this.objectToReturn = objectToReturn;
        staticText = "that card returns to the battlefield with a +1/+1 counter on it";
    }

    public OtherworldlyJourneyEntersBattlefieldEffect(OtherworldlyJourneyEntersBattlefieldEffect effect) {
        super(effect);
        this.objectToReturn = effect.objectToReturn;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.ENTERS_THE_BATTLEFIELD == event.getType();
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
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
            discard(); // use only once
        }
        return false;
    }

    @Override
    public OtherworldlyJourneyEntersBattlefieldEffect copy() {
        return new OtherworldlyJourneyEntersBattlefieldEffect(this);
    }
}
