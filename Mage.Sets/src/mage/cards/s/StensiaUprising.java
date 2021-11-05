package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RedHumanToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StensiaUprising extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_PERMANENT, ComparisonType.EQUAL_TO, 13, true
    );
    private static final Hint hint = new ValueHint(
            "Permanents you control",
            new PermanentsOnBattlefieldCount(
                    StaticFilters.FILTER_CONTROLLED_PERMANENT
            )
    );

    public StensiaUprising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // At the beginning of your end step, create a 1/1 red Human creature token. Then if you control exactly thirteen permanents, you may sacrifice Stensia Uprising. When you do, it deals 7 damage to any target.
        ReflexiveTriggeredAbility reflexiveTrigger = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(7), false, "{this} deals 7 damage to any target"
        );
        reflexiveTrigger.addTarget(new TargetAnyTarget());
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new RedHumanToken()), TargetController.YOU, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DoWhenCostPaid(reflexiveTrigger, new SacrificeSourceCost(), "Sacrifice {this}?"),
                condition, "Then if you control exactly thirteen permanents, " +
                "you may sacrifice {this}. When you do, it deals 7 damage to any target."
        ));
        this.addAbility(ability.addHint(hint));
    }

    private StensiaUprising(final StensiaUprising card) {
        super(card);
    }

    @Override
    public StensiaUprising copy() {
        return new StensiaUprising(this);
    }
}
