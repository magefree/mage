package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 * Case of the Gateway Express {1}{W}
 * Enchantment - Case
 * When this case enters the battlefield, choose target creature you don't control. Each creature you control deals 1 damage to that creature.
 * To solve -- Three or more creatures attacked this turn.
 * Solved -- Creatures you control get +1/+0.
 *
 * @author DominionSpy
 */
public final class CaseOfTheGatewayExpress extends CardImpl {

    public CaseOfTheGatewayExpress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.CASE);

        // When this case enters the battlefield, choose target creature you don't control. Each creature you control deals 1 damage to that creature.
        Ability initialAbility = new EntersBattlefieldTriggeredAbility(
                new CaseOfTheGatewayExpressEffect());
        initialAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        // To solve -- Three or more creatures attacked this turn.
        Condition toSolveCondition = new CaseOfTheGatewayExpressCondition();
        // Solved -- Creatures you control get +1/+0.
        Ability solvedAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield),
                SolvedSourceCondition.SOLVED, ""));

        this.addAbility(new CaseAbility(initialAbility, toSolveCondition, solvedAbility)
                .addHint(new CaseOfTheGatewayExpressHint(toSolveCondition)));
    }

    private CaseOfTheGatewayExpress(final CaseOfTheGatewayExpress card) {
        super(card);
    }

    @Override
    public CaseOfTheGatewayExpress copy() {
        return new CaseOfTheGatewayExpress(this);
    }
}

class CaseOfTheGatewayExpressEffect extends OneShotEffect {

    CaseOfTheGatewayExpressEffect() {
        super(Outcome.Damage);
        staticText = "choose target creature you don't control. " +
                "Each creature you control deals 1 damage to that creature.";
    }

    private CaseOfTheGatewayExpressEffect(final CaseOfTheGatewayExpressEffect effect) {
        super(effect);
    }

    @Override
    public CaseOfTheGatewayExpressEffect copy() {
        return new CaseOfTheGatewayExpressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game)) {
            if (creature == null) {
                continue;
            }
            permanent.damage(1, creature.getId(), source, game);
        }
        return true;
    }
}

class CaseOfTheGatewayExpressCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability ability) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return watcher != null && watcher.getAttackedThisTurnCreatures().size() >= 3;
    }

    @Override
    public String toString() {
        return "Three or more creatures attacked this turn";
    }
}

class CaseOfTheGatewayExpressHint extends CaseSolvedHint {

    CaseOfTheGatewayExpressHint(Condition condition) {
        super(condition);
    }

    private CaseOfTheGatewayExpressHint(final CaseOfTheGatewayExpressHint hint) {
        super(hint);
    }

    @Override
    public CaseOfTheGatewayExpressHint copy() {
        return new CaseOfTheGatewayExpressHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability ability) {
        int attacked = game.getState()
                .getWatcher(AttackedThisTurnWatcher.class)
                .getAttackedThisTurnCreatures().size();
        return "Creatures that attacked: " + attacked + " (need 3).";
    }
}
