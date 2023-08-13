package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author JotaPeRL

 */
public final class WreckingBall extends CardImpl {

    public WreckingBall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{R}");

        // Destroy target creature or land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND));
    }

    private WreckingBall(final WreckingBall card) {
        super(card);
    }

    @Override
    public WreckingBall copy() {
        return new WreckingBall(this);
    }
}
