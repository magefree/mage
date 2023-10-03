package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class PhyrexianAltar extends CardImpl {

    public PhyrexianAltar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Sacrifice a creature: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)),
                CreaturesYouControlCount.instance,
                false
        ));
    }

    private PhyrexianAltar(final PhyrexianAltar card) {
        super(card);
    }

    @Override
    public PhyrexianAltar copy() {
        return new PhyrexianAltar(this);
    }
}
