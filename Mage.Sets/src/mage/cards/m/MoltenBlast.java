package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenBlast extends CardImpl {

    public MoltenBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Choose one —
        // • Molten Blast deals 2 damage to target creature or planeswalker.
        getSpellAbility().addEffect(new DamageTargetEffect(2));
        getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // • Destroy target artifact.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private MoltenBlast(final MoltenBlast card) {
        super(card);
    }

    @Override
    public MoltenBlast copy() {
        return new MoltenBlast(this);
    }
}
