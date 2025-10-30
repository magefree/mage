package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class DamageEachOtherOpponentThatMuchEffect extends OneShotEffect {

    public DamageEachOtherOpponentThatMuchEffect() {
        super(Outcome.Benefit);
        staticText = "it deals that much damage to each other opponent";
    }

    private DamageEachOtherOpponentThatMuchEffect(final DamageEachOtherOpponentThatMuchEffect effect) {
        super(effect);
    }

    @Override
    public DamageEachOtherOpponentThatMuchEffect copy() {
        return new DamageEachOtherOpponentThatMuchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = (Integer) getValue("damage");
        if (damage < 1) {
            return false;
        }
        UUID playerId = getTargetPointer().getFirst(game, source);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (opponentId.equals(playerId)) {
                continue;
            }
            Optional.ofNullable(opponentId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.damage(damage, source, game));
        }
        return true;
    }
}
