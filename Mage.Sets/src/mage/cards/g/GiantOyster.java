package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextDrawStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapAsLongAsSourceTappedEffect;
import mage.abilities.effects.common.RemoveDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author noahg
 */
public final class GiantOyster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(new TappedPredicate());
    }

    public GiantOyster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        
        this.subtype.add(SubType.OYSTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // You may choose not to untap Giant Oyster during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {tap}: For as long as Giant Oyster remains tapped, target tapped creature doesn't untap during its controller's untap step, and at the beginning of each of your draw steps, put a -1/-1 counter on that creature. When Giant Oyster leaves the battlefield or becomes untapped, remove all -1/-1 counters from the creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GiantOysterDontUntapAsLongAsSourceTappedEffect(), new TapSourceCost());
        ability.addEffect(new GiantOysterCreateDelayedTriggerEffects());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public GiantOyster(final GiantOyster card) {
        super(card);
    }

    @Override
    public GiantOyster copy() {
        return new GiantOyster(this);
    }
}

class GiantOysterDontUntapAsLongAsSourceTappedEffect extends DontUntapAsLongAsSourceTappedEffect {

    public GiantOysterDontUntapAsLongAsSourceTappedEffect() {
        super();
        staticText = "For as long as {source} remains tapped, target tapped creature doesn't untap during its controller's untap step";
    }

    public GiantOysterDontUntapAsLongAsSourceTappedEffect(final GiantOysterDontUntapAsLongAsSourceTappedEffect effect) {
        super(effect);
    }

    @Override
    public GiantOysterDontUntapAsLongAsSourceTappedEffect copy() {
        return new GiantOysterDontUntapAsLongAsSourceTappedEffect(this);
    }
}

class GiantOysterCreateDelayedTriggerEffects extends OneShotEffect {

    public GiantOysterCreateDelayedTriggerEffects() {
        super(Outcome.Detriment);
        this.staticText = "at the beginning of each of your draw steps, put a -1/-1 counter on that creature. When {this} leaves the battlefield or becomes untapped, remove all -1/-1 counters from the creature.";
    }

    public GiantOysterCreateDelayedTriggerEffects(final GiantOysterCreateDelayedTriggerEffects effect) {
        super(effect);
    }

    @Override
    public GiantOysterCreateDelayedTriggerEffects copy() {
        return new GiantOysterCreateDelayedTriggerEffects(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent oyster = game.getPermanent(source.getSourceId());
            Permanent tappedCreature = game.getPermanent(source.getFirstTarget());
            if (oyster != null && tappedCreature != null) {
                Effect addCountersEffect = new AddCountersTargetEffect(CounterType.M1M1.createInstance(1));
                addCountersEffect.setTargetPointer(getTargetPointer().getFixedTarget(game, source));
                DelayedTriggeredAbility drawStepAbility = new AtTheBeginOfYourNextDrawStepDelayedTriggeredAbility(addCountersEffect, Duration.Custom, false);
                drawStepAbility.setSourceObject(oyster, game);
                drawStepAbility.setControllerId(source.getControllerId());
                UUID drawStepAbilityUUID = game.addDelayedTriggeredAbility(drawStepAbility, source);

                DelayedTriggeredAbility leaveUntapDelayedTriggeredAbility = new GiantOysterLeaveUntapDelayedTriggeredAbility(drawStepAbilityUUID);
                leaveUntapDelayedTriggeredAbility.getEffects().get(0).setTargetPointer(new FixedTarget(tappedCreature, game));
                game.addDelayedTriggeredAbility(leaveUntapDelayedTriggeredAbility, source);
                return true;
            }
        }
        return false;
    }
}

class GiantOysterLeaveUntapDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public GiantOysterLeaveUntapDelayedTriggeredAbility(UUID abilityToCancel) {
        super(new RemoveAllCountersTargetEffect(CounterType.M1M1), Duration.EndOfGame, true, false);
        this.addEffect(new RemoveDelayedTriggeredAbilityEffect(abilityToCancel));
    }

    public GiantOysterLeaveUntapDelayedTriggeredAbility(GiantOysterLeaveUntapDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.UNTAPPED) || event.getType().equals(GameEvent.EventType.ZONE_CHANGE);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.UNTAPPED) && event.getTargetId() != null
                && event.getTargetId().equals(getSourceId())) {
            System.out.println("Untapped");
            return true;
        }
        return event.getType().equals(GameEvent.EventType.ZONE_CHANGE) && event.getTargetId() != null
                && event.getTargetId().equals(getSourceId()) && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD;
    }

    @Override
    public GiantOysterLeaveUntapDelayedTriggeredAbility copy() {
        return new GiantOysterLeaveUntapDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} leaves the battlefield or becomes untapped, remove all -1/-1 counters from the creature.";
    }
}