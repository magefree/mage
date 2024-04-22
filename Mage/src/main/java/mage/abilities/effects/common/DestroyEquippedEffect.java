package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class DestroyEquippedEffect extends OneShotEffect {

    public DestroyEquippedEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy that permanent";
    }

    protected DestroyEquippedEffect(final DestroyEquippedEffect effect) {
        super(effect);
    }

    @Override
    public DestroyEquippedEffect copy() {
        return new DestroyEquippedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            UUID uuid = getTargetPointer().getFirst(game, source);
            Permanent permanent = game.getPermanent(uuid);
            if (permanent == null) {
                permanent = game.getPermanent(equipment.getAttachedTo());
            }
            if (permanent != null) {
                return permanent.destroy(source, game, false);
            }
        }
        return false;
    }

}
