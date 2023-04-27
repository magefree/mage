
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public final class VeilingOddity extends CardImpl {

    public VeilingOddity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Suspend 4-{1}{U}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{1}{U}"), this));

        // When the last time counter is removed from Veiling Oddity while it's exiled, creatures are unblockable this turn.
        this.addAbility(new VeilingOddityTriggeredAbility());
    }

    private VeilingOddity(final VeilingOddity card) {
        super(card);
    }

    @Override
    public VeilingOddity copy() {
        return new VeilingOddity(this);
    }
}

class VeilingOddityTriggeredAbility extends TriggeredAbilityImpl {

    public VeilingOddityTriggeredAbility() {
        super(Zone.EXILED, new CantBeBlockedAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn), false);
        setTriggerPhrase("When the last time counter is removed from {this} while it's exiled, ");
    }

    public VeilingOddityTriggeredAbility(final VeilingOddityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return (event.getTargetId().equals(this.getSourceId()) && game.getCard(event.getTargetId()).getCounters(game).getCount(CounterType.TIME) == 0);
    }

    @Override
    public VeilingOddityTriggeredAbility copy() {
        return new VeilingOddityTriggeredAbility(this);
    }
}
