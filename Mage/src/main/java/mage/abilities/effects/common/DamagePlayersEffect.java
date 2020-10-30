package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DamagePlayersEffect extends OneShotEffect {
    private DynamicValue amount;
    private TargetController controller;
    private String sourceName = "{this}";

    public DamagePlayersEffect(int amount) {
        this(Outcome.Damage, StaticValue.get(amount));
    }

    public DamagePlayersEffect(int amount, TargetController controller) {
        this(Outcome.Damage, StaticValue.get(amount), controller);
    }

    public DamagePlayersEffect(int amount, TargetController controller, String whoDealDamageName) {
        this(Outcome.Damage, StaticValue.get(amount), controller);

        this.sourceName = whoDealDamageName;
        setText(); // TODO: replace to @Override public String getText()
    }

    public DamagePlayersEffect(Outcome outcome, DynamicValue amount) {
        this(outcome, amount, TargetController.ANY);
    }

    public DamagePlayersEffect(Outcome outcome, DynamicValue amount, TargetController controller) {
        super(outcome);
        this.amount = amount;
        this.controller = controller;
        setText();
    }


    public DamagePlayersEffect(final DamagePlayersEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.controller = effect.controller;
        this.sourceName = effect.sourceName;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        switch (controller) {
            case ANY:
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.damage(amount.calculate(game, source, this), source.getSourceId(), game);
                    }
                }
                break;
            case OPPONENT:
                for (UUID playerId : game.getOpponents(source.getControllerId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.damage(amount.calculate(game, source, this), source.getSourceId(), game);
                    }
                }
                break;
            default:
                throw new UnsupportedOperationException("TargetController type not supported.");
        }
        return true;
    }

    @Override
    public DamagePlayersEffect copy() {
        return new DamagePlayersEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder().append(this.sourceName).append(" deals ").append(amount.toString());
        switch (controller) {
            case ANY:
                sb.append(" damage to each player");
                break;
            case OPPONENT:
                sb.append(" damage to each opponent");
                break;
            default:
                throw new UnsupportedOperationException("TargetController type not supported.");
        }
        staticText = sb.toString();
    }

}
