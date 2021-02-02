
package mage.cards.d;

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
public final class DeepwoodWolverine extends CardImpl {

    public DeepwoodWolverine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.WOLVERINE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Deepwood Wolverine becomes blocked, it gets +2/+0 until end of turn.
        Effect effect = new BoostSourceEffect(2, 0, Duration.EndOfTurn);
        effect.setText("it gets +2/+0 until end of turn");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private DeepwoodWolverine(final DeepwoodWolverine card) {
        super(card);
    }

    @Override
    public DeepwoodWolverine copy() {
        return new DeepwoodWolverine(this);
    }
}
