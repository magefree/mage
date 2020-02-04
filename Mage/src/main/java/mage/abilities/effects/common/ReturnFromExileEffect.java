
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromExileEffect extends OneShotEffect {

    private UUID exileId;
    private Zone zone;
    private boolean tapped;

    public ReturnFromExileEffect(UUID exileId, Zone zone) {
        this(exileId, zone, false);
    }

    public ReturnFromExileEffect(UUID exileId, Zone zone, String text) {
        this(exileId, zone, false);
        staticText = text;
    }

    public ReturnFromExileEffect(UUID exileId, Zone zone, boolean tapped) {
        super(Outcome.PutCardInPlay);
        this.exileId = exileId;
        this.zone = zone;
        this.tapped = tapped;
        setText();
    }

    public ReturnFromExileEffect(final ReturnFromExileEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
        this.zone = effect.zone;
        this.tapped = effect.tapped;
    }

    @Override
    public ReturnFromExileEffect copy() {
        return new ReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exile = game.getExile().getExileZone(exileId);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && exile != null) {
            switch (zone) {
                case LIBRARY:
                    controller.putCardsOnTopOfLibrary(exile, game, source, false);
                    break;
                default:
                    controller.moveCards(exile.getCards(game), zone, source, game, tapped, false, true, null);
            }
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("return the exiled cards ");
        switch (zone) {
            case BATTLEFIELD:
                sb.append("to the battlefield under its owner's control");
                if (tapped) {
                    sb.append(" tapped");
                }
                break;
            case HAND:
                sb.append("to their owner's hand");
                break;
            case GRAVEYARD:
                sb.append("to their owner's graveyard");
                break;
        }
        staticText = sb.toString();
    }

}
