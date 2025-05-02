package mage.cards.d;

import mage.MageInt;
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
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DirtcowlWurm extends CardImpl {

    public DirtcowlWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever an opponent plays a land, put a +1/+1 counter on Dirtcowl Wurm.
        this.addAbility(new DirtcowlWurmTriggeredAbility());
    }

    private DirtcowlWurm(final DirtcowlWurm card) {
        super(card);
    }

    @Override
    public DirtcowlWurm copy() {
        return new DirtcowlWurm(this);
    }
}

class DirtcowlWurmTriggeredAbility extends TriggeredAbilityImpl {
    DirtcowlWurmTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)));
    }

    private DirtcowlWurmTriggeredAbility(final DirtcowlWurmTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        return land != null && game.getOpponents(controllerId).contains(land.getControllerId());
    }

    @Override
    public DirtcowlWurmTriggeredAbility copy() {
        return new DirtcowlWurmTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent plays a land, put a +1/+1 counter on {this}.";
    }
}
