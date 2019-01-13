
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author Ryan-Saklad
 */

public final class CrushContraband extends CardImpl {

    public CrushContraband(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // Choose one or both - Destroy target artifact; or Destroy target land.
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addEffect(new ExileTargetEffect());

        Mode mode1 = new Mode();
        mode1.addTarget(new TargetEnchantmentPermanent());
        mode1.addEffect(new ExileTargetEffect());
        this.getSpellAbility().addMode(mode1);

    }

    public CrushContraband(final CrushContraband card) {
        super(card);
    }

    @Override
    public CrushContraband copy() {
        return new CrushContraband(this);
    }
}