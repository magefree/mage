package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IcebindPillar extends CardImpl {

    public IcebindPillar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.supertype.add(SuperType.SNOW);

        // {S}, {T}: Tap target artifact or creature.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{S}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private IcebindPillar(final IcebindPillar card) {
        super(card);
    }

    @Override
    public IcebindPillar copy() {
        return new IcebindPillar(this);
    }
}
