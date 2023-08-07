
package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author maxlebedev
 */
public final class TouchOfDarkness extends CardImpl {

    public TouchOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // One or more target creatures become black until end of turn.
        Effect effect = new BecomesColorTargetEffect(ObjectColor.BLACK, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, Integer.MAX_VALUE));

    }

    private TouchOfDarkness(final TouchOfDarkness card) {
        super(card);
    }

    @Override
    public TouchOfDarkness copy() {
        return new TouchOfDarkness(this);
    }

}
