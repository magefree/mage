
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class AlabasterPotion extends CardImpl {

    public AlabasterPotion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{W}");

        // Choose one - Target player gains X life; or prevent the next X damage that would be dealt to any target this turn.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPlayer());
        Mode mode = new Mode(new PreventDamageToTargetEffect(Duration.EndOfTurn, false, true, ManacostVariableValue.REGULAR));
        mode.addTarget(new TargetAnyTarget());
        this.getSpellAbility().addMode(mode);
    }

    private AlabasterPotion(final AlabasterPotion card) {
        super(card);
    }

    @Override
    public AlabasterPotion copy() {
        return new AlabasterPotion(this);
    }
}
