
package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamageAllControlledTargetEffect extends OneShotEffect {

    private FilterPermanent filter;
    private int amount;

    public DamageAllControlledTargetEffect(int amount, FilterPermanent filter) {
        super(Outcome.Damage);
        this.amount = amount;
        this.filter = filter;
        getText();
    }

    public DamageAllControlledTargetEffect(final DamageAllControlledTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter.copy();
    }

    @Override
    public DamageAllControlledTargetEffect copy() {
        return new DamageAllControlledTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
            permanent.damage(amount, source.getSourceId(), source, game, false, true);
        }
        return true;
    }

    private void getText() {
        StringBuilder sb = new StringBuilder("{this} deals ");
        sb.append(amount).append(" damage to each ").append(filter.getMessage()).append(" controlled by target player");
        staticText = sb.toString();
    }
}
