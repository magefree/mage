package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.DoubleCounterOnEachPermanentEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SheHulkAttorneyAtLaw extends CardImpl {

    public SheHulkAttorneyAtLaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.ADVISOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Power-up -- {6}{G/W}: Put a +1/+1 counter on She-Hulk. Then double the number of +1/+1 counters on each creature you control.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new ManaCostsImpl<>("{6}{G/W}")
        );
        ability.addEffect(
            new DoubleCounterOnEachPermanentEffect(
                CounterType.P1P1,
                StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED
            ).concatBy("Then"));
        this.addAbility(ability);
    }

    private SheHulkAttorneyAtLaw(final SheHulkAttorneyAtLaw card) {
        super(card);
    }

    @Override
    public SheHulkAttorneyAtLaw copy() {
        return new SheHulkAttorneyAtLaw(this);
    }
}
