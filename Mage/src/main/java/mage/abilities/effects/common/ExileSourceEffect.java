package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileSourceEffect extends OneShotEffect {

    private final boolean toUniqueExileZone;

    public ExileSourceEffect() {
        this(false);
    }

    /**
     * @param toUniqueExileZone moves the card to a source object dependant
     *                          unique exile zone, so another effect of the same source object (e.g.
     *                          Deadeye Navigator) can identify the card
     */
    public ExileSourceEffect(boolean toUniqueExileZone) {
        super(Outcome.Exile);
        staticText = "exile {this}";
        this.toUniqueExileZone = toUniqueExileZone;
    }

    protected ExileSourceEffect(final ExileSourceEffect effect) {
        super(effect);
        this.toUniqueExileZone = effect.toUniqueExileZone;
    }

    @Override
    public ExileSourceEffect copy() {
        return new ExileSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (controller == null || card == null) {
            return false;
        }
        if (card instanceof Permanent) {
            if (!((Permanent) card).isPhasedIn()) {
                return false;
            }
        }
        UUID exileZoneId = null;
        String exileZoneName = "";
        if (toUniqueExileZone) {
            exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            exileZoneName = card.getName();
        }
        return controller.moveCardsToExile(card, source, game, true, exileZoneId, exileZoneName);
    }
}
