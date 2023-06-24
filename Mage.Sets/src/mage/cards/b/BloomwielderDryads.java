package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloomwielderDryads extends CardImpl {

    public BloomwielderDryads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // At the beginning of your end step, put a +1/+1 counter on target creature you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private BloomwielderDryads(final BloomwielderDryads card) {
        super(card);
    }

    @Override
    public BloomwielderDryads copy() {
        return new BloomwielderDryads(this);
    }
}
