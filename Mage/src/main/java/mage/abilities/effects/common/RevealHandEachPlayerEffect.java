package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class RevealHandEachPlayerEffect extends OneShotEffect {
    private final TargetController targetController;

    public RevealHandEachPlayerEffect() {
        this(TargetController.ANY);
    }

    public RevealHandEachPlayerEffect(TargetController targetController) {
        super(Outcome.Benefit);
        this.targetController = targetController;
    }

    public RevealHandEachPlayerEffect(final RevealHandEachPlayerEffect effect) {
        super(effect);
        this.targetController = effect.targetController;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null) {
            return true;
        }
        for (UUID playerId : game.getPlayers().keySet()) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            switch (targetController) {
                case NOT_YOU:
                    if (playerId.equals(source.getControllerId())) {
                        continue;
                    }
                    break;
                case OPPONENT:
                    if (!game.getOpponents(source.getControllerId()).contains(playerId)) {
                        continue;
                    }
                    break;
            }
            player.revealCards(sourceObject.getIdName() + player.getName(), player.getHand(), game);
        }
        return true;
    }

    @Override
    public RevealHandEachPlayerEffect copy() {
        return new RevealHandEachPlayerEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("each ");
        switch (targetController) {
            case NOT_YOU:
                sb.append("other player");
                break;
            case OPPONENT:
                sb.append("opponent");
                break;
            case ANY:
                sb.append("player");
                break;
        }
        sb.append(" reveals their hand");
        return sb.toString();
    }
}
