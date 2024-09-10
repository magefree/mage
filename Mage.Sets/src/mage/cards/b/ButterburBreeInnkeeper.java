package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ButterburBreeInnkeeper extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(
                    SubType.FOOD, "you don't control a Food"
            ), ComparisonType.EQUAL_TO, 0
    );

    public ButterburBreeInnkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if you don't control a Food, create a Food token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new FoodToken()), TargetController.YOU, condition, false
        ));
    }

    private ButterburBreeInnkeeper(final ButterburBreeInnkeeper card) {
        super(card);
    }

    @Override
    public ButterburBreeInnkeeper copy() {
        return new ButterburBreeInnkeeper(this);
    }
}
