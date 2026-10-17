package mage.cards.c;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloakwoodSwarmkeeper extends CardImpl {

    public CloakwoodSwarmkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Gathered Swarm — Whenever one or more tokens enter the battlefield under your control, put a +1/+1 counter on Cloakwood Swarmkeeper.
        this.addAbility(new CloakwoodSwarmkeeperTriggeredAbility());
    }

    private CloakwoodSwarmkeeper(final CloakwoodSwarmkeeper card) {
        super(card);
    }

    @Override
    public CloakwoodSwarmkeeper copy() {
        return new CloakwoodSwarmkeeper(this);
    }
}

class CloakwoodSwarmkeeperTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    CloakwoodSwarmkeeperTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
        this.withFlavorWord("Gathered Swarm");
        setTriggerPhrase("Whenever one or more tokens enter the battlefield under your control, ");
    }

    private CloakwoodSwarmkeeperTriggeredAbility(final CloakwoodSwarmkeeperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getToZone() != Zone.BATTLEFIELD) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent instanceof PermanentToken && permanent.isControlledBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((ZoneChangeBatchEvent) event, game).isEmpty();
    }

    @Override
    public CloakwoodSwarmkeeperTriggeredAbility copy() {
        return new CloakwoodSwarmkeeperTriggeredAbility(this);
    }
}
