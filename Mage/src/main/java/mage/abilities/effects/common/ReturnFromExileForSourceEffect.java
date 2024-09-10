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
    private boolean pluralCards;
    private boolean pluralOwners;
    private boolean putPhrasing;

    /**
     * @param zone Zone the card should return to
     */
    public ReturnFromExileForSourceEffect(Zone zone) {
        super(Outcome.PutCardInPlay);
        this.returnToZone = zone;
        this.pluralCards = false;
        this.pluralOwners = false;
        this.putPhrasing = false;
        updateText();
    }

    protected ReturnFromExileForSourceEffect(final ReturnFromExileForSourceEffect effect) {
        super(effect);
        this.returnToZone = effect.returnToZone;
        this.pluralCards = effect.pluralCards;
        this.pluralOwners = effect.pluralOwners;
        this.putPhrasing = effect.putPhrasing;
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

    public ReturnFromExileForSourceEffect withText(boolean pluralCards, boolean pluralOwners, boolean putPhrasing) {
        this.pluralCards = pluralCards;
        this.pluralOwners = pluralOwners;
        this.putPhrasing = putPhrasing;
        updateText();
        return this;
    }

    private void updateText() {
        StringBuilder sb = new StringBuilder();
        if (putPhrasing) {
            sb.append("put ").append(pluralCards ? "all cards " : "the card ").append("exiled with {this} ");
            sb.append(returnToZone == Zone.BATTLEFIELD ? "onto " : "into ");
        } else {
            sb.append("return the exiled ").append(pluralCards ? "cards" : "card").append(" to ");
        }
        if (returnToZone == Zone.BATTLEFIELD) {
            sb.append("the battlefield under ");
        }
        sb.append(pluralCards ? "their " : "its ").append(pluralOwners ? "owners' " : "owner's ");
        switch (returnToZone) {
            case BATTLEFIELD:
                sb.append("control");
                break;
            case HAND:
                sb.append(pluralOwners ? "hands" : "hand");
                break;
            case GRAVEYARD:
                sb.append(pluralOwners ? "graveyards" : "graveyard");
                break;
        }
        staticText = sb.toString();
    }

}
