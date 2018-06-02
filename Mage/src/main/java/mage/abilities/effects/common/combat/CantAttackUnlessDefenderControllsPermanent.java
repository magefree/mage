

package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class CantAttackUnlessDefenderControllsPermanent extends RestrictionEffect {

    private final FilterPermanent filter;

    public CantAttackUnlessDefenderControllsPermanent(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        staticText = new StringBuilder("{this} can't attack unless defending player controls ").append(filter.getMessage()).toString();
    }

    public CantAttackUnlessDefenderControllsPermanent(final CantAttackUnlessDefenderControllsPermanent effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        UUID defendingPlayerId;
        Player player = game.getPlayer(defenderId);
        if (player == null) {
            Permanent permanent = game.getPermanent(defenderId);
            if (permanent != null) {
                defendingPlayerId = permanent.getControllerId();
            } else {
                return false;
            }
        } else {
            defendingPlayerId = defenderId;
        }
        if (defendingPlayerId != null && game.getBattlefield().countAll(filter, defendingPlayerId, game) == 0) {
            return false;
        }
        return true;
    }

    @Override
    public CantAttackUnlessDefenderControllsPermanent copy() {
        return new CantAttackUnlessDefenderControllsPermanent(this);
    }

}