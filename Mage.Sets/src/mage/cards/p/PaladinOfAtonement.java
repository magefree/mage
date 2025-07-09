package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.LiveLostLastTurnCondition;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
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
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ).withInterveningIf(LiveLostLastTurnCondition.instance));

        // When Paladin of Atonement dies, you gain life equal to it's toughness.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(
                SourcePermanentToughnessValue.instance, "you gain life equal to its toughness"
        )));
    }

    private PaladinOfAtonement(final PaladinOfAtonement card) {
        super(card);
    }

    @Override
    public PaladinOfAtonement copy() {
        return new PaladinOfAtonement(this);
    }
}
