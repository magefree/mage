
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class FieryJustice extends CardImpl {

    public FieryJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}{G}{W}");

        // Fiery Justice deals 5 damage divided as you choose among any number of targets. Target opponent gains 5 life.
        this.getSpellAbility().addEffect(new DamageMultiEffect(5));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(5));
        Effect effect = new GainLifeTargetEffect(5);
        effect.setTargetPointer(new SecondTargetPointer());
        effect.setText("Target opponent gains 5 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private FieryJustice(final FieryJustice card) {
        super(card);
    }

    @Override
    public FieryJustice copy() {
        return new FieryJustice(this);
    }
}
