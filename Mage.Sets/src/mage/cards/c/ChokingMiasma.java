package mage.cards.c;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class ChokingMiasma extends CardImpl {

    public ChokingMiasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Kicker {G}
        this.addAbility(new KickerAbility("{G}"));

        // If this spell was kicked, put a +1/+1 counter on a creature you control.
        this.getSpellAbility().addEffect(new ChokingMiasmaEffect());

        // All creatures get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn).concatBy("<br>"));
    }

    private ChokingMiasma(final ChokingMiasma card) {
        super(card);
    }

    @Override
    public ChokingMiasma copy() {
        return new ChokingMiasma(this);
    }
}

class ChokingMiasmaEffect extends OneShotEffect {

    public ChokingMiasmaEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If this spell was kicked, put a +1/+1 counter on a creature you control.";
    }

    private ChokingMiasmaEffect(final ChokingMiasmaEffect effect) {
        super(effect);
    }

    @Override
    public ChokingMiasmaEffect copy() {
        return new ChokingMiasmaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!KickedCondition.ONCE.apply(game, source)) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (!target.canChoose(controller.getId(), source, game)) {
            return false;
        }
        controller.chooseTarget(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        if (!game.isSimulation()) {
            StringBuilder sb = new StringBuilder();
            MageObject sourceObject = game.getObject(source);
            if (sourceObject != null) {
                sb.append(sourceObject.getLogName());
                sb.append(": ");
            }
            sb.append(controller.getLogName());
            sb.append(" put a +1/+1 counter on ");
            sb.append(permanent.getLogName());
            game.informPlayers(sb.toString());
        }
        return true;
    }
}
