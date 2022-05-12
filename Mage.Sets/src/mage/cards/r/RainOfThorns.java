package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class RainOfThorns extends CardImpl {

    public RainOfThorns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");


        // Choose one or more - Destroy target artifact; destroy target enchantment; and/or destroy target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent().withChooseHint("destroy"));
        this.getSpellAbility().getModes().setMaxModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        Mode mode1 = new Mode(new DestroyTargetEffect());
        mode1.addTarget(new TargetEnchantmentPermanent().withChooseHint("destroy"));
        this.getSpellAbility().addMode(mode1);

        Mode mode2 = new Mode(new DestroyTargetEffect());
        mode2.addTarget(new TargetLandPermanent().withChooseHint("destroy"));
        this.getSpellAbility().addMode(mode2);
    }

    private RainOfThorns(final RainOfThorns card) {
        super(card);
    }

    @Override
    public RainOfThorns copy() {
        return new RainOfThorns(this);
    }
}
