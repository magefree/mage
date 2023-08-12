
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class SacrificeSourceUnlessConditionEffect extends OneShotEffect {

    protected Condition condition;

    public SacrificeSourceUnlessConditionEffect(Condition condition) {
        super(Outcome.Sacrifice);
        this.condition = condition;
    }

    protected SacrificeSourceUnlessConditionEffect(final SacrificeSourceUnlessConditionEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            if (condition.apply(game, source)) {
                return true;
            }
            permanent.sacrifice(source, game);
            return true;
        }
        return false;
    }

    @Override
    public SacrificeSourceUnlessConditionEffect copy() {
        return new SacrificeSourceUnlessConditionEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("sacrifice {this} unless ");
        sb.append(condition.toString());
        return sb.toString();
    }
}
