package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class SealOfDoom extends CardImpl {

    public SealOfDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(true), new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.addAbility(ability);
    }

    private SealOfDoom(final SealOfDoom card) {
        super(card);
    }

    @Override
    public SealOfDoom copy() {
        return new SealOfDoom(this);
    }
}
