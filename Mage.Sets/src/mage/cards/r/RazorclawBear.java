
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class RazorclawBear extends CardImpl {

    public RazorclawBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Razorclaw Bear becomes blocked, it gets +2/+2 until end of turn.
        Effect effect = new BoostSourceEffect(2, 2, Duration.EndOfTurn);
        effect.setText("it gets +2/+2 until end of turn");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private RazorclawBear(final RazorclawBear card) {
        super(card);
    }

    @Override
    public RazorclawBear copy() {
        return new RazorclawBear(this);
    }
}
