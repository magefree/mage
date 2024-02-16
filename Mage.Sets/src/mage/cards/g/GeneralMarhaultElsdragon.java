package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class GeneralMarhaultElsdragon extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(BlockingCreatureCount.TARGET, 3);

    public GeneralMarhaultElsdragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF, SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a creature you control becomes blocked, it gets +3/+3 until end of turn for each creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE, true
        ));
    }

    private GeneralMarhaultElsdragon(final GeneralMarhaultElsdragon card) {
        super(card);
    }

    @Override
    public GeneralMarhaultElsdragon copy() {
        return new GeneralMarhaultElsdragon(this);
    }
}
