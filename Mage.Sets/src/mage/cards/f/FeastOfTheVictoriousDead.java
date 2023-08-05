package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeastOfTheVictoriousDead extends CardImpl {

    public FeastOfTheVictoriousDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}");

        // At the beginning of your end step, if one or more creatures died this turn, you gain that much life and distribute that many +1/+1 counters among creatures you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new GainLifeEffect(CreaturesDiedThisTurnCount.instance)
                        .setText("you gain that much life"),
                TargetController.YOU, FeastOfTheVictoriousDeadCondition.instance, false
        );
        ability.addEffect(new DistributeCountersEffect(CounterType.P1P1, 1, "")
                .setText("and distribute that many +1/+1 counters among creatures you control"));
        Target target = new TargetCreaturePermanentAmount(CreaturesDiedThisTurnCount.instance, StaticFilters.FILTER_CONTROLLED_CREATURES);
        target.setNotTarget(true);
        ability.addTarget(target);
        this.addAbility(ability.addHint(CreaturesDiedThisTurnHint.instance));
    }

    private FeastOfTheVictoriousDead(final FeastOfTheVictoriousDead card) {
        super(card);
    }

    @Override
    public FeastOfTheVictoriousDead copy() {
        return new FeastOfTheVictoriousDead(this);
    }
}

enum FeastOfTheVictoriousDeadCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CreaturesDiedThisTurnCount.instance.calculate(game, source, null) > 0;
    }

    @Override
    public String toString() {
        return "one or more creatures died this turn";
    }
}
