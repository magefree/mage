package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public class SacrificeEquippedEffect extends OneShotEffect {

    public SacrificeEquippedEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice equipped permanent";
    }

    protected SacrificeEquippedEffect(final SacrificeEquippedEffect effect) {
        super(effect);
    }

    @Override
    public SacrificeEquippedEffect copy() {
        return new SacrificeEquippedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            UUID uuid = getTargetPointer().getFirst(game, source);
            Permanent permanent = game.getPermanent(uuid);
            if (permanent == null) {
                permanent = game.getPermanent(equipment.getAttachedTo());
            }
            if (permanent != null) {
                return permanent.sacrifice(source, game);
            }
        }
        return false;
    }

}
