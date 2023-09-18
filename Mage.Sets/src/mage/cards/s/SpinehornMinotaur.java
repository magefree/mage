package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpinehornMinotaur extends CardImpl {

    public SpinehornMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As long as you've drawn two or more cards this turn, Spinehorn Minotaur has double strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                DrewTwoOrMoreCardsCondition.instance, "As long as you've drawn two or more cards this turn, " +
                "{this} has double strike"
        )).addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private SpinehornMinotaur(final SpinehornMinotaur card) {
        super(card);
    }

    @Override
    public SpinehornMinotaur copy() {
        return new SpinehornMinotaur(this);
    }
}
