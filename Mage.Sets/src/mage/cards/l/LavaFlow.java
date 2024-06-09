package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author ilcartographer
 */
public final class LavaFlow extends CardImpl {
    
    public LavaFlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");

        // Destroy target creature or land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND);
        this.getSpellAbility().addTarget(target);
    }

    private LavaFlow(final LavaFlow card) {
        super(card);
    }

    @Override
    public LavaFlow copy() {
        return new LavaFlow(this);
    }
}
