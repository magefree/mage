package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class ManifestDreadThenAttachEffect extends OneShotEffect {

    public ManifestDreadThenAttachEffect() {
        super(Outcome.Benefit);
        staticText = "manifest dread, then attach {this} to that creature";
    }

    private ManifestDreadThenAttachEffect(final ManifestDreadThenAttachEffect effect) {
        super(effect);
    }

    @Override
    public ManifestDreadThenAttachEffect copy() {
        return new ManifestDreadThenAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent creature = ManifestDreadEffect.doManifestDread(player, source, game);
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        return creature != null
                && equipment != null
                && creature.addAttachment(equipment.getId(), source, game);
    }
}
