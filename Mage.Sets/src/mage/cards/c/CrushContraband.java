package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author Ryan-Saklad
 */

public final class CrushContraband extends CardImpl {

    public CrushContraband(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Choose one or both - Destroy target artifact; or Destroy target land.
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent().withChooseHint("destroy"));

        Mode mode1 = new Mode(new ExileTargetEffect());
        mode1.addTarget(new TargetEnchantmentPermanent().withChooseHint("destroy"));
        this.getSpellAbility().addMode(mode1);
    }

    private CrushContraband(final CrushContraband card) {
        super(card);
    }

    @Override
    public CrushContraband copy() {
        return new CrushContraband(this);
    }
}