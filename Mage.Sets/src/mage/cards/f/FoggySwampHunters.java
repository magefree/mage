package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoggySwampHunters extends CardImpl {

    public FoggySwampHunters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // As long as you've drawn two or more cards this turn, this creature has lifelink and menace.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()), DrewTwoOrMoreCardsCondition.instance,
                "as long as you've drawn two or more cards this turn, {this} has lifelink"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MenaceAbility(false)),
                DrewTwoOrMoreCardsCondition.instance, "and menace"
        ));
        this.addAbility(ability.addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private FoggySwampHunters(final FoggySwampHunters card) {
        super(card);
    }

    @Override
    public FoggySwampHunters copy() {
        return new FoggySwampHunters(this);
    }
}
