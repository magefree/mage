
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class TributeAbility extends EntersBattlefieldAbility {

    private int tributeValue;

    public TributeAbility(int tributeValue) {
        super(new TributeEffect(tributeValue));
        this.tributeValue = tributeValue;
    }

    public TributeAbility(final TributeAbility ability) {
        super(ability);
        this.tributeValue = ability.tributeValue;
    }

    @Override
    public EntersBattlefieldAbility copy() {
        return new TributeAbility(this);
    }

    @Override
    public String getRule() {
        return "Tribute " + tributeValue + " <i>(As this creature enters the battlefield, an opponent of your choice may put "
                + tributeValue + " +1/+1 counter on it.)</i>";
    }
}

class TributeEffect extends OneShotEffect {

    private final int tributeValue;

    public TributeEffect(int tributeValue) {
        super(Outcome.Detriment);
        this.tributeValue = tributeValue;
    }

    public TributeEffect(final TributeEffect effect) {
        super(effect);
        this.tributeValue = effect.tributeValue;
    }

    @Override
    public TributeEffect copy() {
        return new TributeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            UUID opponentId;
            if (game.getOpponents(controller.getId()).size() == 1) {
                opponentId = game.getOpponents(controller.getId()).iterator().next();
            } else {
                Target target = new TargetOpponent();
                controller.choose(outcome, target, source, game);
                opponentId = target.getFirstTarget();
            }
            if (opponentId != null) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    StringBuilder sb = new StringBuilder("Pay tribute to ");
                    sb.append(sourcePermanent.getName());
                    sb.append(" (add ").append(CardUtil.numberToText(tributeValue)).append(" +1/+1 counter");
                    sb.append(tributeValue > 1 ? "s" : "").append(" to it)?");
                    if (opponent.chooseUse(outcome, sb.toString(), source, game)) {
                        if (!game.isSimulation()) {
                            game.informPlayers(opponent.getLogName() + " pays tribute to " + sourcePermanent.getLogName());
                        }
                        game.getState().setValue("tributeValue" + source.getSourceId(), "yes");
                        return sourcePermanent.addCounters(CounterType.P1P1.createInstance(tributeValue),opponent.getId(),source,game);
                    } else {
                        if (!game.isSimulation()) {
                            game.informPlayers(opponent.getLogName() + " does not pay tribute to " + sourcePermanent.getLogName());
                        }
                        game.getState().setValue("tributeValue" + source.getSourceId(), "no");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
