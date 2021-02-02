
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class UnstableObelisk extends CardImpl {

    public UnstableObelisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {7}, {tap}, Sacrifice Unstable Obelisk: Destroy target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    private UnstableObelisk(final UnstableObelisk card) {
        super(card);
    }

    @Override
    public UnstableObelisk copy() {
        return new UnstableObelisk(this);
    }
}
