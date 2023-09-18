package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class WickedPact extends CardImpl {

    public WickedPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Destroy two target nonblack creatures. You lose 5 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, 2, StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK, false));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(5));
    }

    private WickedPact(final WickedPact card) {
        super(card);
    }

    @Override
    public WickedPact copy() {
        return new WickedPact(this);
    }
}
