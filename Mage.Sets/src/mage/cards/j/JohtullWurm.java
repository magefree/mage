
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.BlockedCreatureCount;
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
public final class JohtullWurm extends CardImpl {

    public JohtullWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Johtull Wurm becomes blocked, it gets -2/-1 until end of turn for each creature blocking it beyond the first.
        DynamicValue blockedCreatureCount = new BlockedCreatureCount("each creature blocking it beyond the first", true);
        Effect effect = new BoostSourceEffect(new MultipliedValue(blockedCreatureCount, -2), new MultipliedValue(blockedCreatureCount, -1), Duration.EndOfTurn, true);
        effect.setText("it gets -2/-1 until end of turn for each creature blocking it beyond the first");
        this.addAbility(new BecomesBlockedTriggeredAbility(effect, false));
    }

    public JohtullWurm(final JohtullWurm card) {
        super(card);
    }

    @Override
    public JohtullWurm copy() {
        return new JohtullWurm(this);
    }
}
