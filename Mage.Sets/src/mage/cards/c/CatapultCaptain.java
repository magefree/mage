package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class CatapultCaptain extends CardImpl {

    public CatapultCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.ZOMBIE);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Back half of Catapult Fodder
        this.nightCard = true;

        // {2}{B}, {T}, Sacrifice another creature: Target opponent loses life equal to the sacrificed creature's toughness.
        Ability ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(SacrificeCostCreaturesToughness.instance)
                .setText("Target opponent loses life equal to the sacrificed creature's toughness"), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private CatapultCaptain(final CatapultCaptain card) {
        super(card);
    }

    @Override
    public CatapultCaptain copy() {
        return new CatapultCaptain(this);
    }
}
