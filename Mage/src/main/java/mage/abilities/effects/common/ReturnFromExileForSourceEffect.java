package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Use it for combo with ExileTargetForSourceEffect (exile and return exiled later)
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromExileForSourceEffect extends OneShotEffect {

    private final Zone returnToZone;
    private final boolean tapped;
    private final boolean previousZone;
    private String returnName = "cards";
    private String returnControlName;

    /**
     * @param zone Zone the card should return to
     */
    public ReturnFromExileForSourceEffect(Zone zone) {
        this(zone, false);
    }

    public ReturnFromExileForSourceEffect(Zone zone, boolean tapped) {
        this(zone, tapped, true);
    }

    /**
     * @param zone
     * @param tapped
     * @param previousZone if this is used from a dies leave battlefield or
     *                     destroyed trigger, the exile zone is based on previous zone of the object
     */
    public ReturnFromExileForSourceEffect(Zone zone, boolean tapped, boolean previousZone) {
        super(Outcome.PutCardInPlay);
        this.returnToZone = zone;
        this.tapped = tapped;
        this.previousZone = previousZone;

        // different default name for zones
        switch (zone) {
            case BATTLEFIELD:
                this.returnControlName = "its owner's";
                break;
            default:
                this.returnControlName = "their owner's";
                break;
        }

        updateText();
    }

    public ReturnFromExileForSourceEffect(final ReturnFromExileForSourceEffect effect) {
        super(effect);
        this.returnToZone = effect.returnToZone;
        this.tapped = effect.tapped;
        this.previousZone = effect.previousZone;
        this.returnName = effect.returnName;
        this.returnControlName = effect.returnControlName;

        updateText();
    }

    @Override
    public ReturnFromExileForSourceEffect copy() {
        return new ReturnFromExileForSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // effect uses in two use cases:
        // * on battlefield
        // * after leaves the battlefield
        // so ZCC must be different in different use cases

        UUID exileId;
        Permanent permanentLeftBattlefield = (Permanent) getValue("permanentLeftBattlefield");
        if (permanentLeftBattlefield != null) {
            exileId = CardUtil.getExileZoneId(game, source.getSourceId(), permanentLeftBattlefield.getZoneChangeCounter(game));
        } else {
            exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        }

        ExileZone exile = game.getExile().getExileZone(exileId);
        if (exile != null) { // null is valid if source left battlefield before enters the battlefield effect resolved
            if (Zone.BATTLEFIELD.equals(returnToZone)) {
                controller.moveCards(exile.getCards(game), returnToZone, source, game, false, false, true, null);
            } else {
                controller.moveCards(exile, returnToZone, source, game);
            }
        }
        return true;
    }

    private void updateText() {
        StringBuilder sb = new StringBuilder();
        sb.append("return the exiled ").append(this.returnName).append(" ");
        switch (returnToZone) {
            case BATTLEFIELD:
                sb.append("to the battlefield under ").append(this.returnControlName).append(" control");
                if (tapped) {
                    sb.append(" tapped");
                }
                break;
            case HAND:
                sb.append("to ").append(this.returnControlName).append(" hand");
                break;
            case GRAVEYARD:
                sb.append("to ").append(this.returnControlName).append(" graveyard");
                break;
        }
        staticText = sb.toString();
    }

    public ReturnFromExileForSourceEffect withReturnName(String returnName, String returnControlName) {
        this.returnName = returnName;
        this.returnControlName = returnControlName;
        updateText();
        return this;
    }
}
