

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GainLifeEffect extends OneShotEffect {

    private DynamicValue life;

    public GainLifeEffect(int life) {
        this(new StaticValue(life));
    }

    public GainLifeEffect(DynamicValue life) {
        super(Outcome.GainLife);
        this.life = life;
        setText();
    }
    
    public GainLifeEffect(DynamicValue life, String rule) {
        super(Outcome.GainLife);
        this.life = life;
        staticText = rule;
    }

    public GainLifeEffect(final GainLifeEffect effect) {
        super(effect);
        this.life = effect.life.copy();
    }

    @Override
    public GainLifeEffect copy() {
        return new GainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(life.calculate(game, source, this), game, source);
        }
        return true;
    }

    private void setText() {
        if (!staticText.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        String message = life.getMessage();
        sb.append("you gain ");
        if (message.startsWith("that")) {
            sb.append(message).append(' ');
        } else if (message.isEmpty() || !message.equals("1")) {
            sb.append(life).append(' ');
        }
        sb.append("life");
        if (!message.isEmpty() && !message.startsWith("that")) {
            sb.append(message.equals("1") ? " equal to the number of " : " for each ");
            sb.append(message);
        }
        staticText = sb.toString();
    }

}
