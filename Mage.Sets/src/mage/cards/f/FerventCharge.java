
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class FerventCharge extends CardImpl {

    public FerventCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{W}{B}");

        // Whenever a creature you control attacks, it gets +2/+2 until end of turn.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("it gets +2/+2 until end of turn");
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(effect, false, true));
    }

    private FerventCharge(final FerventCharge card) {
        super(card);
    }

    @Override
    public FerventCharge copy() {
        return new FerventCharge(this);
    }
}
