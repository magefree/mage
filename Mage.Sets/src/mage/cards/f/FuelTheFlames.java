package mage.cards.f;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FuelTheFlames extends CardImpl {

    public FuelTheFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Fuel the Flames deals 2 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private FuelTheFlames(final FuelTheFlames card) {
        super(card);
    }

    @Override
    public FuelTheFlames copy() {
        return new FuelTheFlames(this);
    }
}
