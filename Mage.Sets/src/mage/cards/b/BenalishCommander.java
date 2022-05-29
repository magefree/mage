
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author LevelX2
 */
public final class BenalishCommander extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Soldiers you control");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public BenalishCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Benalish Commander's power and toughness are each equal to the number of Soldiers you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));

        // Suspend X-{X}{W}{W}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl<>("{W}{W}"), this, true));

        // Whenever a time counter is removed from Benalish Commander while it's exiled, create a 1/1 white Soldier creature token.
        this.addAbility(new BenalishCommanderTriggeredAbility());
    }

    private BenalishCommander(final BenalishCommander card) {
        super(card);
    }

    @Override
    public BenalishCommander copy() {
        return new BenalishCommander(this);
    }
}

class BenalishCommanderTriggeredAbility extends TriggeredAbilityImpl {

    public BenalishCommanderTriggeredAbility() {
        super(Zone.EXILED, new CreateTokenEffect(new SoldierToken()), false);
    }

    public BenalishCommanderTriggeredAbility(final BenalishCommanderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BenalishCommanderTriggeredAbility copy() {
        return new BenalishCommanderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.TIME.getName()) && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a time counter is removed from {this} while it's exiled, " ;
    }

}
