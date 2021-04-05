package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.TurnPhase;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class ClosingStatement extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public ClosingStatement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{B}");

        // This spell costs {2} less to cast during your end step.
        IsPhaseCondition condition = new IsPhaseCondition(TurnPhase.END, true);
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{1}{W}{B}"), condition, "this spell costs {2} less to cast during your end step.")
            .addHint(new ConditionHint(condition, "On your end step")));

        // Destroy target creature or planeswalker you don't control. Put a +1/+1 counter on up to one target creature you control.
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker(1, 1, filter, false));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ClosingStatementEffect());
    }

    private ClosingStatement(final ClosingStatement card) {
        super(card);
    }

    @Override
    public ClosingStatement copy() {
        return new ClosingStatement(this);
    }
}

class ClosingStatementEffect extends OneShotEffect {

    ClosingStatementEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on up to one target creature you control";
    }

    private ClosingStatementEffect(final ClosingStatementEffect effect) {
        super(effect);
    }

    @Override
    public ClosingStatementEffect copy() {
        return new ClosingStatementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent(0, 1);
        player.choose(outcome, target, source.getSourceId(), game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            return permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }
}