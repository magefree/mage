package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TempleOfAclazotz extends CardImpl {

    public TempleOfAclazotz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.nightCard = true;

        // {T}: Add {B}
        this.addAbility(new BlackManaAbility());

        // {T}, Sacrifice a creature: You gain life equal to the sacrificed creature's toughness.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(SacrificeCostCreaturesToughness.instance)
                .setText("you gain life equal to the sacrificed creature's toughness"), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private TempleOfAclazotz(final TempleOfAclazotz card) {
        super(card);
    }

    @Override
    public TempleOfAclazotz copy() {
        return new TempleOfAclazotz(this);
    }
}
