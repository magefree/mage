

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
 * @author BetaSteward_at_googlemail.com
 */
public class GainLifeEffect extends OneShotEffect {

    private DynamicValue life;

    public GainLifeEffect(int life) {
        this(StaticValue.get(life));
    }

    public GainLifeEffect(DynamicValue life) {
        super(Outcome.GainLife);
        this.life = life;
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

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        String message = life.getMessage();
        sb.append("you gain ");
        if (message.isEmpty() || !life.toString().equals("1")) {
            sb.append(life).append(' ');
        }
        sb.append("life");
        if (!message.isEmpty()) {
            sb.append(life.toString().equals("1") ? " equal to the number of " : " for each ");
            sb.append(message);
        }
        return sb.toString();
    }
}
