package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;


/**
 * This effect applies to the permanent's controller which originated the ability, but not to the controller of that
 * source ability.
 *
 * @author jesusjbr
 */
public class LoseLifePermanentControllerEffect extends OneShotEffect {

    protected DynamicValue amount;

    public LoseLifePermanentControllerEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public LoseLifePermanentControllerEffect(DynamicValue amount) {
        super(Outcome.LoseLife);
        this.amount = amount;
        setText();
    }

    protected LoseLifePermanentControllerEffect(final LoseLifePermanentControllerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public LoseLifePermanentControllerEffect copy() {
        return new LoseLifePermanentControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Player player = null;
        if (permanent != null) {
            player = game.getPlayer(permanent.getControllerId());
        }
        if (player != null) {
            player.loseLife(amount.calculate(game, source, this), game, source, false);
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("controller ").append("loses").append(amount.toString()).append("life");
        staticText += sb.toString();
    }

}