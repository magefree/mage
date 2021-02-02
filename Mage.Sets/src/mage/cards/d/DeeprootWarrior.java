
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DeeprootWarrior extends CardImpl {

    public DeeprootWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Deeproot Warrior becomes blocked, it gets +1/+1 until end of turn.
        Effect effect = new BoostSourceEffect(1, 1, Duration.EndOfTurn);
        effect.setText("it gets +1/+1 until end of turn");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private DeeprootWarrior(final DeeprootWarrior card) {
        super(card);
    }

    @Override
    public DeeprootWarrior copy() {
        return new DeeprootWarrior(this);
    }
}
