package mage.cards.t;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMeathookMassacre extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ManacostVariableValue.ETB, -1);

    public TheMeathookMassacre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);

        // When The Meathook Massacre enters the battlefield, each creature gets -X/-X until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setText("each creature gets -X/-X until end of turn")));

        // Whenever a creature you control dies, each opponent loses 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));

        // Whenever a creature an opponent controls dies, you gain 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new GainLifeEffect(1), false,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));
    }

    private TheMeathookMassacre(final TheMeathookMassacre card) {
        super(card);
    }

    @Override
    public TheMeathookMassacre copy() {
        return new TheMeathookMassacre(this);
    }
}
