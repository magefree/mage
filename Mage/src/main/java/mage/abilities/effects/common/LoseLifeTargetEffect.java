package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LoseLifeTargetEffect extends OneShotEffect {

    protected DynamicValue amount;

    public LoseLifeTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public LoseLifeTargetEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    protected LoseLifeTargetEffect(final LoseLifeTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public LoseLifeTargetEffect copy() {
        return new LoseLifeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        for (UUID playerId : targetPointer.getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player != null
                    && player.loseLife(amount.calculate(game, source, this), game, source, false) > 0) {
                applied = true;
            }
        }
        return applied;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "that player"));
        sb.append(" loses ");
        String message = amount.getMessage();
        if (!message.equals("1")) {
            sb.append(amount).append(' ');
        }
        sb.append("life");
        if (!message.isEmpty()) {
            if (amount.toString().equals("X")) {
                sb.append(", where X is ");
            } else {
                sb.append(message.equals("1") ? " equal to the number of " : " for each ");
            }
            sb.append(message);
        }
        return sb.toString();
    }

}
