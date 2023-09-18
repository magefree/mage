package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LokiX
 */
public final class DoomBlade extends CardImpl {

    public DoomBlade(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private DoomBlade(final DoomBlade card) {
        super(card);
    }

    @Override
    public DoomBlade copy() {
        return new DoomBlade(this);
    }
}
