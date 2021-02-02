
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SparkmagesGambit extends CardImpl {

    public SparkmagesGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Sparkmage's Gambit deals 1 damage to each of up to two target creatures. Those creatures can't block this turn.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("{this} deals 1 damage to each of up to two target creatures");
        this.getSpellAbility().addEffect(effect);
        effect = new CantBlockTargetEffect(Duration.EndOfTurn);
        effect.setText("Those creatures can't block this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private SparkmagesGambit(final SparkmagesGambit card) {
        super(card);
    }

    @Override
    public SparkmagesGambit copy() {
        return new SparkmagesGambit(this);
    }
}
