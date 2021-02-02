
package mage.cards.n;

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
public final class NorwoodWarrior extends CardImpl {

    public NorwoodWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Norwood Warrior becomes blocked, it gets +1/+1 until end of turn.
        Effect effect = new BoostSourceEffect(1, 1, Duration.EndOfTurn);
        effect.setText("it gets +1/+1 until end of turn");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private NorwoodWarrior(final NorwoodWarrior card) {
        super(card);
    }

    @Override
    public NorwoodWarrior copy() {
        return new NorwoodWarrior(this);
    }
}
