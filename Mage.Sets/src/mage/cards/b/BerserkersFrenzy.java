package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.ChooseBlockersEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.common.ControlCombatRedundancyWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BerserkersFrenzy extends CardImpl {

    private static final Hint hint = new ConditionHint(BerserkersFrenzyCondition.instance, "Can be cast");

    public BerserkersFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Cast this spell only before combat or during combat before blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(
                null, null, BerserkersFrenzyCondition.instance,
                "Cast this spell only before combat or during combat before blockers are declared"
        ).addHint(hint));

        // Roll two d20 and ignore the lower roll.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "roll two d20 and ignore the lower roll", StaticValue.get(0), 1
        );

        // 1-14 | Choose any number of creatures. They block this turn if able.
        effect.addTableEntry(1, 14, new BerserkersFrenzyEffect());

        // 15-20 | You choose which creatures block this turn and how those creatures block.
        effect.addTableEntry(15, 20, new ChooseBlockersEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addWatcher(new ControlCombatRedundancyWatcher());
    }

    private BerserkersFrenzy(final BerserkersFrenzy card) {
        super(card);
    }

    @Override
    public BerserkersFrenzy copy() {
        return new BerserkersFrenzy(this);
    }
}

enum BerserkersFrenzyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPhase().getType() == TurnPhase.COMBAT) {
            return game.getStep().getType().isBefore(PhaseStep.DECLARE_BLOCKERS);
        }
        return !game.getTurn().isDeclareAttackersStepStarted();
    }
}

class BerserkersFrenzyEffect extends OneShotEffect {

    BerserkersFrenzyEffect() {
        super(Outcome.Benefit);
        staticText = "choose any number of creatures. They block this turn if able";
    }

    private BerserkersFrenzyEffect(final BerserkersFrenzyEffect effect) {
        super(effect);
    }

    @Override
    public BerserkersFrenzyEffect copy() {
        return new BerserkersFrenzyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetCreaturePermanent(0, Integer.MAX_VALUE);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        game.addEffect(new BlocksIfAbleTargetEffect(Duration.EndOfTurn)
                .setTargetPointer(new FixedTargets(new CardsImpl(target.getTargets()), game)), source);
        return true;
    }
}
