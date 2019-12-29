package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FarmsteadGleaner extends CardImpl {

    public FarmsteadGleaner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Farmstead Gleaner doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));

        // {2}, {Q}: Put a +1/+1 counter on Farmstead Gleaner.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2)
        );
        ability.addCost(new UntapSourceCost());
        this.addAbility(ability);
    }

    private FarmsteadGleaner(final FarmsteadGleaner card) {
        super(card);
    }

    @Override
    public FarmsteadGleaner copy() {
        return new FarmsteadGleaner(this);
    }
}
