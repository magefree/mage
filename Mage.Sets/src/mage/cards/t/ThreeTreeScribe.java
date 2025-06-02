package mage.cards.t;

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
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThreeTreeScribe extends CardImpl {

    public ThreeTreeScribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Three Tree Scribe or another creature you control leaves the battlefield without dying, put a +1/+1 counter on target creature you control.
        this.addAbility(new ThreeTreeScribeTriggeredAbility());
    }

    private ThreeTreeScribe(final ThreeTreeScribe card) {
        super(card);
    }

    @Override
    public ThreeTreeScribe copy() {
        return new ThreeTreeScribe(this);
    }
}

class ThreeTreeScribeTriggeredAbility extends TriggeredAbilityImpl {

    ThreeTreeScribeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.setTriggerPhrase("Whenever {this} or another creature you control leaves the battlefield without dying, ");
        this.addTarget(new TargetControlledCreaturePermanent());
        setLeavesTheBattlefieldTrigger(true);
    }

    private ThreeTreeScribeTriggeredAbility(final ThreeTreeScribeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThreeTreeScribeTriggeredAbility copy() {
        return new ThreeTreeScribeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!Zone.BATTLEFIELD.match(zEvent.getFromZone())
                || Zone.GRAVEYARD.match(zEvent.getToZone())) {
            return false;
        }
        Permanent permanent = zEvent.getTarget();
        if (permanent == null) {
            return false;
        }
        if (permanent.getId().equals(getSourceId())) {
            return true; // {this} or another
        }
        return permanent.isCreature(game) && permanent.isControlledBy(getControllerId());
    }
}
