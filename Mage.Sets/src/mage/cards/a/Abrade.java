
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Abrade extends CardImpl {

    public Abrade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one &mdash; Abrade deals 3 damage to target creature.;
        getSpellAbility().addEffect(new DamageTargetEffect(3));
        getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Destroy target artifact.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private Abrade(final Abrade card) {
        super(card);
    }

    @Override
    public Abrade copy() {
        return new Abrade(this);
    }
}
