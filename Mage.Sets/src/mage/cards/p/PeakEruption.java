
package mage.cards.p;

import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class PeakEruption extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain");

    static{
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public PeakEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Destroy target Mountain. Peak Eruption deals 3 damage to that land's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DamageTargetControllerEffect(3, "land"));
        this.getSpellAbility().addTarget(new TargetLandPermanent(filter));
    }

    private PeakEruption(final PeakEruption card) {
        super(card);
    }

    @Override
    public PeakEruption copy() {
        return new PeakEruption(this);
    }
}
