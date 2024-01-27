package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class BubblingCauldron extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature named Festering Newt");

    static {
        filter.add(new NamePredicate("Festering Newt"));
    }

    public BubblingCauldron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {T}, Sacrifice a creature: You gain 4 life.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(4), new ManaCostsImpl<>("{1}"));
        ability1.addCost(new TapSourceCost());
        ability1.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability1);
        // {1}, {T}, Sacrifice a creature named Festering Newt: Each opponent loses 4 life. You gain life equal to the life lost this way.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeOpponentsYouGainLifeLostEffect(4), new ManaCostsImpl<>("{1}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability2);
    }

    private BubblingCauldron(final BubblingCauldron card) {
        super(card);
    }

    @Override
    public BubblingCauldron copy() {
        return new BubblingCauldron(this);
    }
}
