
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SacrificeSourceEffect extends OneShotEffect {

    public SacrificeSourceEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice {this}";
    }

    public SacrificeSourceEffect(final SacrificeSourceEffect effect) {
        super(effect);
    }

    @Override
    public SacrificeSourceEffect copy() {
        return new SacrificeSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (sourceObject == null) {
            // Check if the effect was installed by the spell the source was cast by (e.g. Necromancy), if not don't sacrifice the permanent
            if (source.getSourceObject(game) instanceof Spell) {
                sourceObject = game.getPermanent(source.getSourceId());
                if (sourceObject != null && sourceObject.getZoneChangeCounter(game) > source.getSourceObjectZoneChangeCounter() + 1) {
                    return false;
                }
            }
        }
        if (sourceObject instanceof Permanent) {
            Permanent permanent = (Permanent) sourceObject;
            // you can only sacrifice a permanent you control
            if (source.isControlledBy(permanent.getControllerId())) {
                return permanent.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

}
