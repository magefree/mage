
package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrushingCanopy extends CardImpl {

    public CrushingCanopy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose one --
        // * Destroy target creature with flying.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());

        // * Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private CrushingCanopy(final CrushingCanopy card) {
        super(card);
    }

    @Override
    public CrushingCanopy copy() {
        return new CrushingCanopy(this);
    }
}
