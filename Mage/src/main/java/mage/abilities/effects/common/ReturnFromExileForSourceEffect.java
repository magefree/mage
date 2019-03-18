
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromExileForSourceEffect extends OneShotEffect {

    private Zone returnToZone;
    private boolean tapped;
    private boolean previousZone;

    /**
     *
     * @param zone Zone the card should return to
     */
    public ReturnFromExileForSourceEffect(Zone zone) {
        this(zone, false);
    }

    public ReturnFromExileForSourceEffect(Zone zone, boolean tapped) {
        this(zone, tapped, true);
    }

    /**
     *
     * @param zone
     * @param tapped
     * @param previousZone if this is used from a dies leave battlefield or
     * destroyed trigger, the exile zone is based on previous zone of the object
     */
    public ReturnFromExileForSourceEffect(Zone zone, boolean tapped, boolean previousZone) {
        super(Outcome.PutCardInPlay);
        this.returnToZone = zone;
        this.tapped = tapped;
        this.previousZone = previousZone;
        setText();
    }

    public ReturnFromExileForSourceEffect(final ReturnFromExileForSourceEffect effect) {
        super(effect);
        this.returnToZone = effect.returnToZone;
        this.tapped = effect.tapped;
        this.previousZone = effect.previousZone;
    }

    @Override
    public ReturnFromExileForSourceEffect copy() {
        return new ReturnFromExileForSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            Permanent permanentLeftBattlefield = (Permanent) getValue("permanentLeftBattlefield");
            if (permanentLeftBattlefield == null) {
                Logger.getLogger(ReturnFromExileForSourceEffect.class).error("Permanent not found: " + sourceObject.getName());
                return false;
            }
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), permanentLeftBattlefield.getZoneChangeCounter(game)));
            if (exile != null) { // null is valid if source left battlefield before enters the battlefield effect resolved
                if (returnToZone == Zone.BATTLEFIELD) {
                    controller.moveCards(exile.getCards(game), returnToZone, source, game, false, false, true, null);
                } else {
                    controller.moveCards(exile, returnToZone, source, game);
                }
            }
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("return the exiled cards ");
        switch (returnToZone) {
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
