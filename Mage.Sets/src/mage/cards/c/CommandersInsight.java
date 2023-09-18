package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommandersInsight extends CardImpl {

    public CommandersInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}{U}");

        // Target player draws X cards plus an additional card for each time they've cast a commander from the command zone this game.
        this.getSpellAbility().addEffect(new CommandersInsightEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private CommandersInsight(final CommandersInsight card) {
        super(card);
    }

    @Override
    public CommandersInsight copy() {
        return new CommandersInsight(this);
    }
}

enum CommandersInsightHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        if (watcher == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("Commander cast counts &mdash; ");
        boolean flag = false;
        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (flag) {
                sb.append(',').append(' ');
            }
            flag = true;
            sb.append(player.getName());
            sb.append(": ");
            sb.append(watcher.getPlayerCount(playerId));
        }
        return sb.toString();
    }

    @Override
    public CommandersInsightHint copy() {
        return instance;
    }
}

class CommandersInsightEffect extends OneShotEffect {

    CommandersInsightEffect() {
        super(Outcome.Benefit);
        staticText = "target player draws X cards plus an additional card " +
                "for each time they've cast a commander from the command zone this game";
    }

    private CommandersInsightEffect(final CommandersInsightEffect effect) {
        super(effect);
    }

    @Override
    public CommandersInsightEffect copy() {
        return new CommandersInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        if (player == null || watcher == null) {
            return false;
        }
        int toDraw = watcher.getPlayerCount(player.getId()) + source.getManaCostsToPay().getX();
        return player.drawCards(toDraw, source, game) > 0;
    }
}
