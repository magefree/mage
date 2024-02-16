
package mage.cards.a;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class AshnodsAltar extends CardImpl {

    public AshnodsAltar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Sacrifice a creature: Add {C}{C}.
        SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT);
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, 
                new BasicManaEffect(Mana.ColorlessMana(2), CreaturesYouControlCount.instance), 
                cost));
    }

    private AshnodsAltar(final AshnodsAltar card) {
        super(card);
    }

    @Override
    public AshnodsAltar copy() {
        return new AshnodsAltar(this);
    }
}
