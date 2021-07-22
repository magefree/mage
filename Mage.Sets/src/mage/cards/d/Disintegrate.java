
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.ruleModifying.CantRegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author dustinconrad
 */
public final class Disintegrate extends CardImpl {

    public Disintegrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Disintegrate deals X damage to any target. That creature can't be regenerated this turn. If the creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new CantRegenerateTargetEffect(Duration.EndOfTurn, "That creature"));
        Effect effect = new ExileTargetIfDiesEffect();
        effect.setText("If the creature would die this turn, exile it instead");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private Disintegrate(final Disintegrate card) {
        super(card);
    }

    @Override
    public Disintegrate copy() {
        return new Disintegrate(this);
    }
}
