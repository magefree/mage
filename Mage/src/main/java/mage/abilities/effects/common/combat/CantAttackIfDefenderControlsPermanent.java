package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author BursegSardaukar
 */

public class CantAttackIfDefenderControlsPermanent extends RestrictionEffect {

    private final FilterPermanent filter;

    public CantAttackIfDefenderControlsPermanent(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        staticText = new StringBuilder("{this} can't attack if defending player controls ").append(filter.getMessage()).toString();
    }

    protected CantAttackIfDefenderControlsPermanent(final CantAttackIfDefenderControlsPermanent effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }

        UUID defendingPlayerId;
        Player player = game.getPlayer(defenderId);
        if (player == null) {
            Permanent permanent = game.getPermanent(defenderId);
            if (permanent != null) {
                defendingPlayerId = permanent.getControllerId();
            } else {
                return true;
            }
        } else {
            defendingPlayerId = defenderId;
        }
        return !(defendingPlayerId != null && game.getBattlefield().countAll(filter, defendingPlayerId, game) > 0);
    }

    @Override
    public CantAttackIfDefenderControlsPermanent copy() {
        return new CantAttackIfDefenderControlsPermanent(this);
    }

}