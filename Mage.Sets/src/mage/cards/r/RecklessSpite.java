package mage.cards.r;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class RecklessSpite extends CardImpl {

    public RecklessSpite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{B}");

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, 2, StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK, false));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(5));
    }

    private RecklessSpite(final RecklessSpite card) {
        super(card);
    }

    @Override
    public RecklessSpite copy() {
        return new RecklessSpite(this);
    }
}
