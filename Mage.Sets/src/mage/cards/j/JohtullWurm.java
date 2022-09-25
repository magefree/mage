package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class JohtullWurm extends CardImpl {

    private static final DynamicValue xValue1 = new MultipliedValue(BlockingCreatureCount.BEYOND_FIRST, -1);
    private static final DynamicValue xValue2 = new MultipliedValue(BlockingCreatureCount.BEYOND_FIRST, -2);

    public JohtullWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Johtull Wurm becomes blocked, it gets -2/-1 until end of turn for each creature blocking it beyond the first.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(xValue2, xValue1, Duration.EndOfTurn, true, "it"), false));
    }

    private JohtullWurm(final JohtullWurm card) {
        super(card);
    }

    @Override
    public JohtullWurm copy() {
        return new JohtullWurm(this);
    }
}
