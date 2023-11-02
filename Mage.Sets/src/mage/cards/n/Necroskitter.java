
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class Necroskitter extends CardImpl {

    public Necroskitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Wither (This deals damage to creatures in the form of -1/-1 counters.)
        this.addAbility(WitherAbility.getInstance());

        // Whenever a creature an opponent controls with a -1/-1 counter on it dies, you may return that card to the battlefield under your control.
        this.addAbility(new NecroskitterTriggeredAbility());

    }

    private Necroskitter(final Necroskitter card) {
        super(card);
    }

    @Override
    public Necroskitter copy() {
        return new Necroskitter(this);
    }
}

class NecroskitterTriggeredAbility extends TriggeredAbilityImpl {

    public NecroskitterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToBattlefieldUnderYourControlTargetEffect(), true);
    }

    private NecroskitterTriggeredAbility(final NecroskitterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NecroskitterTriggeredAbility copy() {
        return new NecroskitterTriggeredAbility(this);
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
        return "Whenever a creature an opponent controls with a -1/-1 counter on it dies, you may return that card to the battlefield under your control.";
    }
}
