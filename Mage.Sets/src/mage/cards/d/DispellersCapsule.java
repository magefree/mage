
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;
import mage.abilities.costs.common.TapSourceCost;

/**
 *
 * @author Loki
 */
public final class DispellersCapsule extends CardImpl {
    
    public DispellersCapsule (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{W}");

        // {2}{W}, {T}, Sacrifice Dispeller's Capsule: Destroy target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    public DispellersCapsule (final DispellersCapsule card) {
        super(card);
    }

    @Override
    public DispellersCapsule copy() {
        return new DispellersCapsule(this);
    }
}
