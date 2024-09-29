
package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DrawCardAllEffect extends OneShotEffect {

    private TargetController targetController;
    protected DynamicValue amount;

    public DrawCardAllEffect(int amount) {
        this(amount, TargetController.ANY);
    }

    public DrawCardAllEffect(DynamicValue amount) {
        this(amount, TargetController.ANY);
    }

    public DrawCardAllEffect(int amount, TargetController targetController) {
        this(StaticValue.get(amount), targetController);
    }

    public DrawCardAllEffect(DynamicValue amount, TargetController targetController) {
        super(Outcome.DrawCard);
        this.amount = amount;
        this.targetController = targetController;
        staticText = setText();
    }

    protected DrawCardAllEffect(final DrawCardAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.targetController = effect.targetController;
    }

    @Override
    public DrawCardAllEffect copy() {
        return new DrawCardAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        switch (targetController) {
            case ANY:
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(amount.calculate(game, source, this), source, game);
                    }
                }
                break;
            case OPPONENT:
                for (UUID playerId : game.getOpponents(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(amount.calculate(game, source, this), source, game);
                    }
                }
                break;
        }
        return true;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("each ");
        switch (targetController) {
            case ANY:
                sb.append("player");
                break;
            case OPPONENT:
                sb.append("opponent");
                break;
            default:
                throw new UnsupportedOperationException("Not supported value for targetController");
        }
        sb.append(" draws ");
        sb.append(CardUtil.numberToText(amount.toString(), "a"));
        sb.append(" card");
        sb.append(amount.toString().equals("1") ? "" : "s");
        return sb.toString();
    }
}
