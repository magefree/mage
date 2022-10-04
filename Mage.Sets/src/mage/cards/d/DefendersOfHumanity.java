package mage.cards.d;

import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AstartesWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefendersOfHumanity extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            "you control no creatures and only during your turn", MyTurnCondition.instance,
            new PermanentsOnTheBattlefieldCondition(
                    StaticFilters.FILTER_PERMANENT_CREATURE,
                    ComparisonType.EQUAL_TO, 0, true
            )
    );

    public DefendersOfHumanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{2}{W}");

        // When Defenders of Humanity enters the battlefield, create X 2/2 white Astartes Warrior creature tokens with vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new AstartesWarriorToken(), ManacostVariableValue.ETB)
        ));

        // {X}{2}{W}, Exile Defenders of Humanity: Create X 2/2 white Astartes Warrior creature tokens with vigilance. Activate only if you control no creatures and only during your turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(
                        new AstartesWarriorToken(), GetXValue.instance
                ), new ManaCostsImpl<>("{X}{2}{W}"), condition
        ).addHint(CreaturesYouControlHint.instance).addHint(MyTurnHint.instance));
    }

    private DefendersOfHumanity(final DefendersOfHumanity card) {
        super(card);
    }

    @Override
    public DefendersOfHumanity copy() {
        return new DefendersOfHumanity(this);
    }
}
