package mage.cards.d;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.DemonToken;

import java.util.UUID;

/**
 * @author noxx
 */
public final class DemonicRising extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent("you control exactly one creature"),
            ComparisonType.EQUAL_TO, 1
    );

    public DemonicRising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // At the beginning of your end step, if you control exactly one creature, create a 5/5 black Demon creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new DemonToken())).withInterveningIf(condition));
    }

    private DemonicRising(final DemonicRising card) {
        super(card);
    }

    @Override
    public DemonicRising copy() {
        return new DemonicRising(this);
    }
}
