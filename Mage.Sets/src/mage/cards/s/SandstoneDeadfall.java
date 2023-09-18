
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author fireshoes
 */
public final class SandstoneDeadfall extends CardImpl {

    public SandstoneDeadfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}, Sacrifice two lands and Sandstone Deadfall: Destroy target attacking creature.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("lands"), true)));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private SandstoneDeadfall(final SandstoneDeadfall card) {
        super(card);
    }

    @Override
    public SandstoneDeadfall copy() {
        return new SandstoneDeadfall(this);
    }
}
