package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CasualtiesOfWar extends CardImpl {

    public CasualtiesOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}{G}{G}");

        // Choose one or more —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(5);

        // • Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // • Destroy target creature.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // • Destroy target enchantment.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);

        // • Destroy target land.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetLandPermanent());
        this.getSpellAbility().addMode(mode);

        // • Destroy target planeswalker.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_PLANESWALKER));
        this.getSpellAbility().addMode(mode);
    }

    private CasualtiesOfWar(final CasualtiesOfWar card) {
        super(card);
    }

    @Override
    public CasualtiesOfWar copy() {
        return new CasualtiesOfWar(this);
    }
}
