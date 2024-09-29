

package mage.cards.d;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DeathsPresence extends CardImpl {

    public DeathsPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{5}{G}");

        
        // Whenever a creature you control dies, put X +1/+1 counters on target creature you control, where X is the power of the creature that died.
        this.addAbility(new DeathsPresenceTriggeredAbility());
    }

    private DeathsPresence(final DeathsPresence card) {
        super(card);
    }

    @Override
    public DeathsPresence copy() {
        return new DeathsPresence(this);
    }
}

class DeathsPresenceTriggeredAbility extends TriggeredAbilityImpl {

    public DeathsPresenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private DeathsPresenceTriggeredAbility(final DeathsPresenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DeathsPresenceTriggeredAbility copy() {
        return new DeathsPresenceTriggeredAbility(this);
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
            if (permanent != null && permanent.isControlledBy(this.getControllerId()) && permanent.isCreature(game)) {
                this.getTargets().clear();
                this.addTarget(new TargetControlledCreaturePermanent());
                this.getEffects().clear();
                this.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(permanent.getPower().getValue())));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control dies, put X +1/+1 counters on target creature you control, where X is the power of the creature that died.";
    }
}
