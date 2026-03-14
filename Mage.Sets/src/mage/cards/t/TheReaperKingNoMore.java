package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TheReaperKingNoMore extends CardImpl {

    public TheReaperKingNoMore (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE} ,"{2/B}{2/R}{2/G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When The Reaper enters, put a -1/-1 counter on each of up to two target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.M1M1.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // Whenever a creature an opponent controls with a -1/-1 counter on it dies, you may put that card onto the battlefield under your control. Do this only once each turn.
        this.addAbility(new TheReaperKingNoMoreTriggeredAbility());
    }

    private TheReaperKingNoMore(final TheReaperKingNoMore card) {
        super(card);
    }

    @Override
    public TheReaperKingNoMore copy() {
        return new TheReaperKingNoMore(this);
    }
}

class TheReaperKingNoMoreTriggeredAbility extends TriggeredAbilityImpl {

    public TheReaperKingNoMoreTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToBattlefieldUnderYourControlTargetEffect(), true);
        setLeavesTheBattlefieldTrigger(true);
        setDoOnlyOnceEachTurn(true);
    }

    private TheReaperKingNoMoreTriggeredAbility(final TheReaperKingNoMoreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheReaperKingNoMoreTriggeredAbility copy() {
        return new TheReaperKingNoMoreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = zEvent.getTarget();
            if (permanent != null
                    && permanent.getCounters(game).containsKey(CounterType.M1M1)
                    && game.getOpponents(controllerId).contains(permanent.getControllerId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId(), game.getState().getZoneChangeCounter(event.getTargetId())));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls with a -1/-1 counter on it dies, you may put that card onto " +
                "the battlefield under your control. Do this only once each turn.";
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}
