
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LoseLifeTargetEffect extends OneShotEffect {

    protected DynamicValue amount;

    public LoseLifeTargetEffect(int amount) {
        this(new StaticValue(amount));
    }

    public LoseLifeTargetEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public LoseLifeTargetEffect(final LoseLifeTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public LoseLifeTargetEffect copy() {
        return new LoseLifeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.loseLife(amount.calculate(game, source, this), game, false);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        String message = amount.getMessage();

        if (!mode.getTargets().isEmpty()) {
            sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        } else {
            sb.append("that player");
        }
        sb.append(" loses ");
        if (message.isEmpty() || !message.equals("1")) {
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
