
package mage.cards.r;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class RakishHeir extends CardImpl {

    public RakishHeir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Vampire you control deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new RakishHeirTriggeredAbility());
    }

    private RakishHeir(final RakishHeir card) {
        super(card);
    }

    @Override
    public RakishHeir copy() {
        return new RakishHeir(this);
    }
}

class RakishHeirTriggeredAbility extends TriggeredAbilityImpl {

    public RakishHeirTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private RakishHeirTriggeredAbility(final RakishHeirTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RakishHeirTriggeredAbility copy() {
        return new RakishHeirTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && permanent != null
                && permanent.hasSubtype(SubType.VAMPIRE, game) && permanent.isControlledBy(controllerId)) {
            this.getEffects().clear();
            AddCountersTargetEffect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            this.addEffect(effect);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Vampire you control deals combat damage to a player, put a +1/+1 counter on it.";
    }
}
