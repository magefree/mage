package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class HideousEnd extends CardImpl {

    public HideousEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{B}");

        // Destroy target nonblack creature. Its controller loses 2 life.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(2));
    }

    private HideousEnd(final HideousEnd card) {
        super(card);
    }

    @Override
    public HideousEnd copy() {
        return new HideousEnd(this);
    }
}
