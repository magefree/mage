
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class Lunge extends CardImpl {

    public Lunge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Lunge deals 2 damage to target creature and 2 damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2).setUseOnlyTargetPointer(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        Effect effect = new DamageTargetEffect(2).setUseOnlyTargetPointer(true);
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("and 2 damage to target player or planeswalker");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    private Lunge(final Lunge card) {
        super(card);
    }

    @Override
    public Lunge copy() {
        return new Lunge(this);
    }
}
