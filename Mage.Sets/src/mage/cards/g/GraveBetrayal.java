
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class GraveBetrayal extends CardImpl {

    public GraveBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");

        // Whenever a creature you don't control dies, return it to the battlefield under
        // your control with an additional +1/+1 counter on it at the beginning of the
        // next end step. That creature is a black Zombie in addition to its other colors and types.
        this.addAbility(new GraveBetrayalTriggeredAbility());
    }

    private GraveBetrayal(final GraveBetrayal card) {
        super(card);
    }

    @Override
    public GraveBetrayal copy() {
        return new GraveBetrayal(this);
    }
}

class GraveBetrayalTriggeredAbility extends TriggeredAbilityImpl {

    public GraveBetrayalTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public GraveBetrayalTriggeredAbility(final GraveBetrayalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GraveBetrayalTriggeredAbility copy() {
        return new GraveBetrayalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zoneChangeEvent = (ZoneChangeEvent) event;
        if (zoneChangeEvent.isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null && !permanent.isControlledBy(this.getControllerId()) && permanent.isCreature(game)) {
                Card card = (Card) game.getObject(permanent.getId());
                if (card != null) {
                    Effect effect = new GraveBetrayalEffect();
                    effect.setTargetPointer(new FixedTarget(card, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                    game.addDelayedTriggeredAbility(delayedAbility, this);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you don't control dies, return it to the battlefield under your control with an additional +1/+1 counter on it at the beginning of the next end step. That creature is a black Zombie in addition to its other colors and types.";
    }
}

class GraveBetrayalEffect extends OneShotEffect {

    public GraveBetrayalEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = " return the creature to the battlefield under your control with an additional +1/+1 counter. That creature is a black Zombie in addition to its other colors and types";
    }

    public GraveBetrayalEffect(final GraveBetrayalEffect effect) {
        super(effect);
    }

    @Override
    public GraveBetrayalEffect copy() {
        return new GraveBetrayalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                ContinuousEffect effect = new GraveBetrayalReplacementEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }

}

class GraveBetrayalReplacementEffect extends ReplacementEffectImpl {

    GraveBetrayalReplacementEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    GraveBetrayalReplacementEffect(GraveBetrayalReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
            ContinuousEffect effect = new AddCreatureTypeAdditionEffect(SubType.ZOMBIE, true);
            effect.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game) + 1));
            game.addEffect(effect, source);
            //discard(); why?
        }
        return false;
    }

    @Override
    public GraveBetrayalReplacementEffect copy() {
        return new GraveBetrayalReplacementEffect(this);
    }
}
