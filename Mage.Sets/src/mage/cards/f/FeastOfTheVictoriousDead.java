package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.common.TargetPermanentAmount;

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
        ability.addEffect(new FeastOfTheVictoriousDeadEffect());
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

class FeastOfTheVictoriousDeadEffect extends OneShotEffect {

    FeastOfTheVictoriousDeadEffect() {
        super(Outcome.BoostCreature);
        staticText = "and distribute that many +1/+1 counters among creatures you control";
    }

    private FeastOfTheVictoriousDeadEffect(final FeastOfTheVictoriousDeadEffect effect) {
        super(effect);
    }

    @Override
    public FeastOfTheVictoriousDeadEffect copy() {
        return new FeastOfTheVictoriousDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = CreaturesDiedThisTurnCount.instance.calculate(game, source, this);
        if (amount <= 0) {
            return false;
        }

        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game) < 1) {
            return false;
        }
        TargetPermanentAmount target = new TargetCreaturePermanentAmount(amount, StaticFilters.FILTER_CONTROLLED_CREATURE);
        target.setMinNumberOfTargets(1);
        target.withNotTarget(true);
        target.withChooseHint("to distribute " + amount + " counters");
        target.chooseTarget(outcome, player.getId(), source, game);
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(target.getTargetAmount(targetId)), source, game);
            }
        }
        return true;
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
