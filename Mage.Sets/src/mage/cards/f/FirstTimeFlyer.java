package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.LessonsInGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirstTimeFlyer extends CardImpl {

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
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), LessonsInGraveCondition.ONE,
                "{this} gets +1/+1 as long as there's a Lesson card in your graveyard"
        )).addHint(LessonsInGraveCondition.getHint()));
    }

    private FirstTimeFlyer(final FirstTimeFlyer card) {
        super(card);
    }

    @Override
    public FirstTimeFlyer copy() {
        return new FirstTimeFlyer(this);
    }
}
