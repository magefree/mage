

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GainLifeTargetEffect extends OneShotEffect {

    private DynamicValue life;

    public GainLifeTargetEffect(int life) {
        this(new StaticValue(life));
    }

    public GainLifeTargetEffect(DynamicValue life) {
        super(Outcome.GainLife);
        this.life = life;
    }

    public GainLifeTargetEffect(final GainLifeTargetEffect effect) {
        super(effect);
        this.life = effect.life;
    }

    @Override
    public GainLifeTargetEffect copy() {
        return new GainLifeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId: targetPointer.getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.gainLife(life.calculate(game, source, this), game, source);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        String message = life.getMessage();

        if (!mode.getTargets().isEmpty()) {
            sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        } else {
            sb.append("that player");
        }
        sb.append(" gains ");
        if (message.isEmpty() || !message.equals("1")) {
            sb.append(life.toString()).append(' ');
        }
        sb.append("life");
        if (!message.isEmpty()) {
            sb.append(message.equals("1") ? " equal to the number of " : " for each ");
            sb.append(message);
        }
        return sb.toString();
    }

}
