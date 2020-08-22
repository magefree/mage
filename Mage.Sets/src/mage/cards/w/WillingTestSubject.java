
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RollDiceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author spjspj
 */
public final class WillingTestSubject extends CardImpl {

    public WillingTestSubject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.MONKEY);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you roll a 4 or higher on a die, put a +1/+1 counter on Willing Test Subject.
        this.addAbility(new WillingTestSubjectTriggeredAbility());

        // 6: Roll a six-sided die.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RollDiceEffect(null, 6), new GenericManaCost(6));
        this.addAbility(ability);
    }

    public WillingTestSubject(final WillingTestSubject card) {
        super(card);
    }

    @Override
    public WillingTestSubject copy() {
        return new WillingTestSubject(this);
    }
}

class WillingTestSubjectTriggeredAbility extends TriggeredAbilityImpl {

    public WillingTestSubjectTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));

    }

    public WillingTestSubjectTriggeredAbility(final WillingTestSubjectTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WillingTestSubjectTriggeredAbility copy() {
        return new WillingTestSubjectTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DICE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.isControlledBy(event.getPlayerId()) && event.getFlag()) {
            if (event.getAmount() >= 4) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you roll a 4 or higher on a die, put a +1/+1 counter on {this}";
    }
}
