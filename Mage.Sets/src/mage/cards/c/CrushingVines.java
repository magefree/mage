
package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class CrushingVines extends CardImpl {

    public CrushingVines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose one - Destroy target creature with flying; or destroy target artifact.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private CrushingVines(final CrushingVines card) {
        super(card);
    }

    @Override
    public CrushingVines copy() {
        return new CrushingVines(this);
    }
}
