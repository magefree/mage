package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class WorfHonorBoundWarrior extends CardImpl {

    public WorfHonorBoundWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R/W}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you attack while creatures you control have total power 8 or greater, double Worf's power until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new BoostSourceEffect(
            SourcePermanentPowerValue.ALLOW_NEGATIVE, StaticValue.get(0), Duration.EndOfTurn
        ).setText("double {this}'s power until end of turn"), 1).withTriggerCondition(FormidableCondition.instance));

        // Tap two untapped artifacts and/or creatures you control: Put a +1/+1 counter on Worf. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new TapTargetCost(2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_ARTIFACT_OR_CREATURE)
        ));
    }

    private WorfHonorBoundWarrior(final WorfHonorBoundWarrior card) {
        super(card);
    }

    @Override
    public WorfHonorBoundWarrior copy() {
        return new WorfHonorBoundWarrior(this);
    }
}
