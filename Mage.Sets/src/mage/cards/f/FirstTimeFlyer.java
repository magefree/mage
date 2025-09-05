package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirstTimeFlyer extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(1, new FilterCard(SubType.LESSON));
    private static final Hint hint = new ConditionHint(condition, "There's a Lesson card in your graveyard");

    public FirstTimeFlyer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature gets +1/+1 as long as there's a Lesson card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                condition, "{this} gets +1/+1 as long as there's a Lesson card in your graveyard"
        )).addHint(hint));
    }

    private FirstTimeFlyer(final FirstTimeFlyer card) {
        super(card);
    }

    @Override
    public FirstTimeFlyer copy() {
        return new FirstTimeFlyer(this);
    }
}
