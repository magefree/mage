
package mage.abilities.effects.common;

import java.util.List;
import java.util.UUID;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ExileAllEffect extends OneShotEffect {

    private FilterPermanent filter;
    private String exileZone = null;
    private UUID exileId = null;

    public ExileAllEffect(FilterPermanent filter) {
        this(filter, null, null);
    }

    public ExileAllEffect(FilterPermanent filter, UUID exileId, String exileZone) {
        super(Outcome.Exile);
        this.filter = filter;
        this.exileZone = exileZone;
        this.exileId = exileId;
        setText();
    }

    public ExileAllEffect(final ExileAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.exileZone = effect.exileZone;
        this.exileId = effect.exileId;
    }

    @Override
    public ExileAllEffect copy() {
        return new ExileAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
            for (Permanent permanent : permanents) {
                controller.moveCardToExileWithInfo(permanent, exileId, exileZone, source.getSourceId(), game, Zone.BATTLEFIELD, true);
            }
            return true;
        }
        return false;

    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("exile all ").append(filter.getMessage());
        staticText = sb.toString();
    }
}
