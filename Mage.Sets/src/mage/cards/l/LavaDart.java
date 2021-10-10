
package mage.cards.l;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class LavaDart extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Mountain");
    
    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public LavaDart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Lava Dart deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        
        // Flashback-Sacrifice a Mountain.
        this.addAbility(new FlashbackAbility(this, new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    private LavaDart(final LavaDart card) {
        super(card);
    }

    @Override
    public LavaDart copy() {
        return new LavaDart(this);
    }
}
