
package mage.cards.t;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class TerritorialGorger extends CardImpl {

    public TerritorialGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GREMLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever you get one or more {E}, Territorial Gorger gets +2/+2 until end of turn.
        this.addAbility(new TerritorialGorgerTriggeredAbility());
    }

    private TerritorialGorger(final TerritorialGorger card) {
        super(card);
    }

    @Override
    public TerritorialGorger copy() {
        return new TerritorialGorger(this);
    }
}

class TerritorialGorgerTriggeredAbility extends TriggeredAbilityImpl {

    TerritorialGorgerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), false);
        setTriggerPhrase("Whenever you get one or more {E} <i>(energy counters)</i>, ");
    }

    TerritorialGorgerTriggeredAbility(final TerritorialGorgerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TerritorialGorgerTriggeredAbility copy() {
        return new TerritorialGorgerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.ENERGY.getName())) {
            return Objects.equals(event.getTargetId(), this.getControllerId());
        }
        return false;
    }
}
