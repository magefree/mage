package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MeriekeRiBerit extends CardImpl {

    public MeriekeRiBerit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Merieke Ri Berit doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));

        // {tap}: Gain control of target creature for as long as you control Merieke Ri Berit. When Merieke Ri Berit leaves the battlefield or becomes untapped, destroy that creature. It can't be regenerated.
        Ability ability = new SimpleActivatedAbility(new GainControlTargetEffect(Duration.WhileControlled), new TapSourceCost());
        ability.addTarget(new TargetPermanent(new FilterCreaturePermanent("target creature")));
        ability.addEffect(new MeriekeRiBeritCreateDelayedTriggerEffect());
        this.addAbility(ability);
    }

    private MeriekeRiBerit(final MeriekeRiBerit card) {
        super(card);
    }

    @Override
    public MeriekeRiBerit copy() {
        return new MeriekeRiBerit(this);
    }
}

class MeriekeRiBeritCreateDelayedTriggerEffect extends OneShotEffect {

    public MeriekeRiBeritCreateDelayedTriggerEffect() {
        super(Outcome.Detriment);
        this.staticText = "When {this} leaves the battlefield or becomes untapped, destroy that creature. It can't be regenerated";
    }

    public MeriekeRiBeritCreateDelayedTriggerEffect(final MeriekeRiBeritCreateDelayedTriggerEffect effect) {
        super(effect);
    }

    @Override
    public MeriekeRiBeritCreateDelayedTriggerEffect copy() {
        return new MeriekeRiBeritCreateDelayedTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent controlledCreature = game.getPermanent(source.getFirstTarget());
        if (controlledCreature != null) {
            DelayedTriggeredAbility delayedAbility = new MeriekeRiBeritDelayedTriggeredAbility();
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(controlledCreature, game));
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}

class MeriekeRiBeritDelayedTriggeredAbility extends DelayedTriggeredAbility {

    MeriekeRiBeritDelayedTriggeredAbility() {
        super(new DestroyTargetEffect(true), Duration.Custom, true);
    }

    MeriekeRiBeritDelayedTriggeredAbility(MeriekeRiBeritDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE
                || event.getType() == GameEvent.EventType.UNTAPPED;
    }

    @Override
    public boolean isInactive(Game game) {
        return getSourceObjectIfItStillExists(game) == null
                && game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD, getSourceObjectZoneChangeCounter()) == null;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId() != null) {
            if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                    && event.getTargetId().equals(getSourceId())) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                return zEvent.getFromZone() == Zone.BATTLEFIELD;
            }
        }
        return EventType.UNTAPPED == event.getType()
                && event.getTargetId() != null && event.getTargetId().equals(getSourceId());
    }

    @Override
    public MeriekeRiBeritDelayedTriggeredAbility copy() {
        return new MeriekeRiBeritDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} leaves the battlefield or becomes untapped, destroy that creature. It can't be regenerated.";
    }
}
