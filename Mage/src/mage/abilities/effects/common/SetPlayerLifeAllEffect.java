/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import static mage.constants.TargetController.ANY;
import static mage.constants.TargetController.OPPONENT;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SetPlayerLifeAllEffect extends OneShotEffect {

    private TargetController targetController;
    protected DynamicValue amount;

    public SetPlayerLifeAllEffect(int amount) {
        this(amount, TargetController.ANY);
    }

    public SetPlayerLifeAllEffect(DynamicValue amount) {
        this(amount, TargetController.ANY);
    }

    public SetPlayerLifeAllEffect(int amount, TargetController targetController) {
        this(new StaticValue(amount), targetController);
    }

    public SetPlayerLifeAllEffect(DynamicValue amount, TargetController targetController) {
        super(Outcome.DrawCard);
        this.amount = amount;
        this.targetController = targetController;
        staticText = setText();
    }

    public SetPlayerLifeAllEffect(final SetPlayerLifeAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.targetController = effect.targetController;
    }

    @Override
    public SetPlayerLifeAllEffect copy() {
        return new SetPlayerLifeAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        switch (targetController) {
            case ANY:
                for (UUID playerId : sourcePlayer.getInRange()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.setLife(amount.calculate(game, source, this), game);
                    }
                }
                break;
            case OPPONENT:
                for (UUID playerId : game.getOpponents(sourcePlayer.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.setLife(amount.calculate(game, source, this), game);
                    }
                }
                break;
        }
        return true;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Each ");
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
        sb.append(" 's life total becomes ");
        sb.append(amount.toString());
        return sb.toString();
    }
}
