package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author arcox
 */
public final class TomeAnima extends CardImpl {

    public TomeAnima(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tome Anima can’t be blocked as long as you’ve drawn two or more cards this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        new CantBeBlockedSourceAbility(), Duration.WhileOnBattlefield
                ), DrewTwoOrMoreCardsCondition.instance, "{this} can't be blocked " +
                "as long as you've drawn two or more cards this turn"
        )).addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private TomeAnima(final TomeAnima card) {
        super(card);
    }

    @Override
    public TomeAnima copy() {
        return new TomeAnima(this);
    }
}
