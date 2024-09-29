
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromExileEffect extends OneShotEffect {

    private final Zone zone;
    private final boolean tapped;

    public ReturnFromExileEffect(Zone zone) {
        this(zone, false);
    }

    public ReturnFromExileEffect(Zone zone, String text) {
        this(zone, false);
        staticText = text;
    }

    public ReturnFromExileEffect(Zone zone, boolean tapped) {
        super(Outcome.PutCardInPlay);
        this.zone = zone;
        this.tapped = tapped;
        setText();
    }

    protected ReturnFromExileEffect(final ReturnFromExileEffect effect) {
        super(effect);
        this.zone = effect.zone;
        this.tapped = effect.tapped;
    }

    @Override
    public ReturnFromExileEffect copy() {
        return new ReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || exile == null) {
            return false;
        }
        if (zone == Zone.LIBRARY) {
            return controller.putCardsOnTopOfLibrary(exile, game, source, false);
        }
        return controller.moveCards(exile.getCards(game), zone, source, game, tapped, false, true, null);
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
