
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.LiveLostLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class PaladinOfAtonement extends CardImpl {

    public PaladinOfAtonement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of each upkeep, if you lost life last turn, put a +1/+1 counter on Paladin of Atonement.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.ANY, false),
                LiveLostLastTurnCondition.instance,
                "At the beginning of each upkeep, if you lost life last turn, put a +1/+1 counter on {this}"));

        // When Paladin of Atonement dies, you gain life equal to it's toughness.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(SourcePermanentToughnessValue.getInstance(),
                "you gain life equal to its toughness")));
    }

    private PaladinOfAtonement(final PaladinOfAtonement card) {
        super(card);
    }

    @Override
    public PaladinOfAtonement copy() {
        return new PaladinOfAtonement(this);
    }
}
