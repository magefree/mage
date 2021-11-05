package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DamagePlayersEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final TargetController controller;
    private final String sourceName;

    public DamagePlayersEffect(int amount) {
        this(Outcome.Damage, StaticValue.get(amount));
    }

    public DamagePlayersEffect(int amount, TargetController controller) {
        this(Outcome.Damage, StaticValue.get(amount), controller);
    }

    public DamagePlayersEffect(int amount, TargetController controller, String whoDealDamageName) {
        this(Outcome.Damage, StaticValue.get(amount), controller, whoDealDamageName);
    }

    public DamagePlayersEffect(Outcome outcome, DynamicValue amount) {
        this(outcome, amount, TargetController.ANY);
    }

    public DamagePlayersEffect(Outcome outcome, DynamicValue amount, TargetController controller) {
        this(outcome, amount, controller, "{this}");
    }

    public DamagePlayersEffect(Outcome outcome, DynamicValue amount, TargetController controller, String whoDealDamageName) {
        super(outcome);
        this.amount = amount;
        this.controller = controller;
        this.sourceName = whoDealDamageName;
    }


    public DamagePlayersEffect(final DamagePlayersEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.controller = effect.controller;
        this.sourceName = effect.sourceName;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Collection<UUID> players;
        switch (controller) {
            case ANY:
            case EACH_PLAYER:
                players = game.getState().getPlayersInRange(source.getControllerId(), game);
                break;
            case OPPONENT:
                players = game.getOpponents(source.getControllerId());
                break;
            default:
                throw new UnsupportedOperationException("TargetController type not supported.");
        }
        int damage = amount.calculate(game, source, this);
        for (UUID playerId : players) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.damage(damage, source.getSourceId(), source, game);
            }
        }
        return true;
    }

    @Override
    public DamagePlayersEffect copy() {
        return new DamagePlayersEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String message = sourceName + " deals " + amount.toString() + " damage to each ";
        switch (controller) {
            case ANY:
            case EACH_PLAYER:
                return message + "player";
            case OPPONENT:
                return message + "opponent";
            default:
                throw new UnsupportedOperationException("TargetController type not supported.");
        }
    }
}
