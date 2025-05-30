package mage.abilities.effects.common;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class HideawayPlayEffect extends OneShotEffect {

    private final boolean playOne;

    public HideawayPlayEffect() {
        this(false);
    }

    public HideawayPlayEffect(boolean playOne) {
        super(Outcome.Benefit);
        this.playOne = playOne;
        if (playOne) {
            staticText = "if there are cards exiled with it, you may play one of them without paying its mana cost";
        } else {
            staticText = "you may play the exiled card without paying its mana cost";
        }
    }

    protected HideawayPlayEffect(final HideawayPlayEffect effect) {
        super(effect);
        this.playOne = effect.playOne;
    }

    @Override
    public HideawayPlayEffect copy() {
        return new HideawayPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone zone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (controller == null || zone == null || zone.isEmpty()) {
            return true;
        }
        CardsImpl cards = new CardsImpl(zone.getCards(game));

        boolean cardPlayed = false;
        int maxChoices = (this.playOne) ? 1 : cards.size();
        TargetCard target = new TargetCard(0, maxChoices, Zone.EXILED, new FilterCard("cards to play (in order they're chosen)"));
        controller.choose(Outcome.PlayForFree, cards, target, source, game);
        List<UUID> targets = target.getTargets();

        for (UUID targetId : targets) {
            Card card = game.getCard(targetId);
            /* 702.74. Hideaway, rulings:
             * If the removed card is a land, you may play it as a result of the last ability only if it's your turn
             * and you haven't already played a land that turn. This counts as your land play for the turn.
             * TODO: this doesn't work correctly with the back half of MDFCs
             */
            if (card.isLand(game)) {
                UUID playerId = controller.getId();
                if (!game.isActivePlayer(playerId) || !game.getPlayer(playerId).canPlayLand()) {
                    continue;
                }
            }

            card.setFaceDown(false, game);
            int zcc = card.getZoneChangeCounter(game);

            if (controller.playCard(card, game, true, new ApprovingObject(source, game))) {
                cardPlayed = true;
            } else {
                if (card.getZoneChangeCounter(game) == zcc) {
                    card.setFaceDown(true, game);
                }
            }
        }
        return cardPlayed;
    }
}
