package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class PoisonArrow extends CardImpl {

    public PoisonArrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");

        // Destroy target nonblack creature. You gain 3 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private PoisonArrow(final PoisonArrow card) {
        super(card);
    }

    @Override
    public PoisonArrow copy() {
        return new PoisonArrow(this);
    }
}
