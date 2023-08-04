package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SacrificeSourceEffect extends OneShotEffect {

    public SacrificeSourceEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice {this}";
    }

    protected SacrificeSourceEffect(final SacrificeSourceEffect effect) {
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
            if (game.getState().getZone(source.getSourceId()).equals(Zone.BATTLEFIELD)
                    && source.getSourceObjectZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(source.getSourceId())) {
                sourceObject = game.getPermanent(source.getSourceId());
            }
        }
        if (sourceObject instanceof Permanent) {
            Permanent permanent = (Permanent) sourceObject;
            // you can only sacrifice a permanent you control
            if (source.isControlledBy(permanent.getControllerId())) {
                return permanent.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }

}
