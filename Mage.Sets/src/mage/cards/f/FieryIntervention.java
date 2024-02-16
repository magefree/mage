
package mage.cards.f;

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
 * @author TheElk801
 */
public final class FieryIntervention extends CardImpl {

    public FieryIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Choose one -
        // -Fiery Intervention deals 5 damage to target creature.
        getSpellAbility().addEffect(new DamageTargetEffect(5));
        getSpellAbility().addTarget(new TargetCreaturePermanent());

        // -Destroy target artifact.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private FieryIntervention(final FieryIntervention card) {
        super(card);
    }

    @Override
    public FieryIntervention copy() {
        return new FieryIntervention(this);
    }
}
