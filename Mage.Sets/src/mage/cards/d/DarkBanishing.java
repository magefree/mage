package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK;

/**
 *
 * @author Loki
 */
public final class DarkBanishing extends CardImpl {

    public DarkBanishing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private DarkBanishing(final DarkBanishing card) {
        super(card);
    }

    @Override
    public DarkBanishing copy() {
        return new DarkBanishing(this);
    }
}
