
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class VampiricFeast extends CardImpl {

    public VampiricFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}{B}");

        // Vampiric Feast deals 4 damage to any target and you gain 4 life.
        Effect effect = new DamageTargetEffect(4);
        effect.setText("{this} deals 4 damage to any target");
        this.getSpellAbility().addEffect(effect);
        // and you gain 4 life.
        effect = new GainLifeEffect(4);
        effect.setText("and you gain 4 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private VampiricFeast(final VampiricFeast card) {
        super(card);
    }

    @Override
    public VampiricFeast copy() {
        return new VampiricFeast(this);
    }
}
