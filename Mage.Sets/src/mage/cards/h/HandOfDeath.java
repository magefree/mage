package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class HandOfDeath extends CardImpl {

    public HandOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Destroy target nonblack creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private HandOfDeath(final HandOfDeath card) {
        super(card);
    }

    @Override
    public HandOfDeath copy() {
        return new HandOfDeath(this);
    }
}
