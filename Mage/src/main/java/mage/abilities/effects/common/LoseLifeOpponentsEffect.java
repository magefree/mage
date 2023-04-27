
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class LoseLifeOpponentsEffect extends OneShotEffect {

    private final DynamicValue amount;

    public LoseLifeOpponentsEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public LoseLifeOpponentsEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = generateStaticText();
    }

    public LoseLifeOpponentsEffect(final LoseLifeOpponentsEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null) {
                player.loseLife(amount.calculate(game, source, this), game, source, false);
            }
        }
        return true;
    }

    @Override
    public LoseLifeOpponentsEffect copy() {
        return new LoseLifeOpponentsEffect(this);
    }

    private String generateStaticText() {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        String message = amount.getMessage();

        sb.append("each opponent loses ");
        if (!message.equals("1")) {
            sb.append(amount).append(' ');
        }
        sb.append("life");
        if (!message.isEmpty()) {
            sb.append(message.equals("1") || message.startsWith("the ") ? " equal to the number of " : " for each ");
            sb.append(message);
        }
        return sb.toString();
    }
}
