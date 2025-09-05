package mage.cards.e;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExplosiveShot extends CardImpl {

    public ExplosiveShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Explosive Shot deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ExplosiveShot(final ExplosiveShot card) {
        super(card);
    }

    @Override
    public ExplosiveShot copy() {
        return new ExplosiveShot(this);
    }
}
