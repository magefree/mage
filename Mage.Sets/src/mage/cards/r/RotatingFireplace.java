package mage.cards.r;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.TimeTravelEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class RotatingFireplace extends CardImpl {

    public RotatingFireplace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Rotating Fireplace enters the battlefield tapped with a time counter on it.
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true), false, null,
                "{this} enters the battlefield tapped with a time counter on it.", null
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.TIME.createInstance()));
        this.addAbility(ability);

        // {T}: Add an amount of {C} equal to the number of time counters on Rotating Fireplace.
        this.addAbility(new DynamicManaAbility(Mana.ColorlessMana(1), new CountersSourceCount(CounterType.TIME),
                "Add an amount of {C} equal to the number of time counters on {this}"
        ));

        // {4}, {T}: Time travel. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new TimeTravelEffect(), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RotatingFireplace(final RotatingFireplace card) {
        super(card);
    }

    @Override
    public RotatingFireplace copy() {
        return new RotatingFireplace(this);
    }
}
