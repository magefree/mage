
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;

import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class MalevolentAwakening extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature card from your graveyard");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public MalevolentAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // {1}{B}{B}, Sacrifice a creature: Return target creature card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{1}{B}{B}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private MalevolentAwakening(final MalevolentAwakening card) {
        super(card);
    }

    @Override
    public MalevolentAwakening copy() {
        return new MalevolentAwakening(this);
    }
}
