package mage.cards.f;

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
 * @author Jgod
 */
public final class Fissure extends CardImpl {
    
    public Fissure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}{R}");

        // Destroy target creature or land. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        Target target = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND);
        this.getSpellAbility().addTarget(target);
    }

    private Fissure(final Fissure card) {
        super(card);
    }

    @Override
    public Fissure copy() {
        return new Fissure(this);
    }
}
