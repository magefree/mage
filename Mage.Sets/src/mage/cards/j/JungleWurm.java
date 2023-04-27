package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class JungleWurm extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(BlockingCreatureCount.BEYOND_FIRST);

    public JungleWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Jungle Wurm becomes blocked, it gets -1/-1 until end of turn for each creature blocking it beyond the first.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true, "it"), false));
    }

    private JungleWurm(final JungleWurm card) {
        super(card);
    }

    @Override
    public JungleWurm copy() {
        return new JungleWurm(this);
    }
}
