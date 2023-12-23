package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.IxalanVampireToken;
import mage.game.permanent.token.VampireDemonToken;

/**
 * @author arcox
 */
public final class MarchOfTheCanonized extends CardImpl {

    public MarchOfTheCanonized(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{W}{W}");

        // When March of the Canonized enters the battlefield, create X 1/1 white Vampire creature tokens with lifelink.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new IxalanVampireToken(), ManacostVariableValue.ETB)));

        // At the beginning of your upkeep, if your devotion to white and black is seven or greater, create a 4/3 white and black Vampire Demon creature token with flying.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new VampireDemonToken()), TargetController.YOU, false),
                new MarchOfTheCanonizedCondition(),
                "At the beginning of your upkeep, "
                        + "if your devotion to white and black is seven or greater, "
                        + "create a 4/3 white and black Vampire Demon creature token with flying."
        ).addHint(DevotionCount.WB.getHint()));
    }

    private MarchOfTheCanonized(final MarchOfTheCanonized card) {
        super(card);
    }

    @Override
    public MarchOfTheCanonized copy() {
        return new MarchOfTheCanonized(this);
    }
}

class MarchOfTheCanonizedCondition extends IntCompareCondition {

    MarchOfTheCanonizedCondition() {
        super(ComparisonType.OR_GREATER, 7);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        return DevotionCount.WB.calculate(game, source, null);
    }
}
