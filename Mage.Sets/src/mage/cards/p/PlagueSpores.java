package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author LoneFox
 *
 */
public final class PlagueSpores extends CardImpl {

    public PlagueSpores(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{R}");

        // Destroy target nonblack creature and target land. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true).setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private PlagueSpores(final PlagueSpores card) {
        super(card);
    }

    @Override
    public PlagueSpores copy() {
        return new PlagueSpores(this);
    }
}
