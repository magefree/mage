
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class SuffocatingBlast extends CardImpl {

    public SuffocatingBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}{R}");

        // Counter target spell and Suffocating Blast deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        Effect effect = new DamageTargetEffect(3);
        effect.setText("and {this} deals 3 damage to target creature");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SuffocatingBlast(final SuffocatingBlast card) {
        super(card);
    }

    @Override
    public SuffocatingBlast copy() {
        return new SuffocatingBlast(this);
    }
}
