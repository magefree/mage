
package mage.cards.f;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FirstVolley extends CardImpl {

    public FirstVolley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");
        this.subtype.add(SubType.ARCANE);

        // First Volley deals 1 damage to target creature and 1 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        Effect effect = new DamageTargetControllerEffect(1);
        effect.setText("and 1 damage to that creature's controller");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private FirstVolley(final FirstVolley card) {
        super(card);
    }

    @Override
    public FirstVolley copy() {
        return new FirstVolley(this);
    }
}
