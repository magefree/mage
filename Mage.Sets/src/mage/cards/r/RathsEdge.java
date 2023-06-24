
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author anonymous
 */
public final class RathsEdge extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("a land");

    public RathsEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.LEGENDARY);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {4}, {tap}, Sacrifice a land: Rath's Edge deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private RathsEdge(final RathsEdge card) {
        super(card);
    }

    @Override
    public RathsEdge copy() {
        return new RathsEdge(this);
    }
}
