package mage.cards.s;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.GameLog;

/**
 *
 * @author LevelX2
 */
public final class SteamAugury extends CardImpl {

    public SteamAugury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{R}");

        // Reveal the top five cards of your library and separate them into two piles. An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new SteamAuguryEffect());
    }

    private SteamAugury(final SteamAugury card) {
        super(card);
    }

    @Override
    public SteamAugury copy() {
        return new SteamAugury(this);
    }
}

class SteamAuguryEffect extends OneShotEffect {

    public SteamAuguryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library and separate them into two piles. An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard";
    }

    public SteamAuguryEffect(final SteamAuguryEffect effect) {
        super(effect);
    }

    @Override
    public SteamAuguryEffect copy() {
        return new SteamAuguryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        Set<Card> cardsToGraveyard = new LinkedHashSet<>();
        Set<Card> cardsToHand = new LinkedHashSet<>();
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        controller.revealCards(sourceObject.getIdName(), cards, game);

        Player opponent;
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            Target target = new TargetOpponent(true);
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            opponent = game.getPlayer(target.getFirstTarget());
        }

        if (opponent != null) {
            TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, new FilterCard("cards to put in the first pile"));
            List<Card> pile1 = new ArrayList<>();
            Cards pile1CardsIds = new CardsImpl();
            target.setRequired(false);
            if (controller.choose(Outcome.Neutral, cards, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        pile1.add(card);
                        pile1CardsIds.add(card.getId());
                    }
                }
            }
            List<Card> pile2 = new ArrayList<>();
            Cards pile2CardsIds = new CardsImpl();
            for (UUID cardId : cards) {
                Card card = game.getCard(cardId);
                if (card != null && !pile1.contains(card)) {
                    pile2.add(card);
                    pile2CardsIds.add(card.getId());
                }
            }
            boolean choice = opponent.choosePile(Outcome.Detriment, "Choose a pile to put into " + controller.getName() + "'s hand.", pile1, pile2, game);

            Zone pile1Zone = Zone.GRAVEYARD;
            Zone pile2Zone = Zone.HAND;
            if (choice) {
                pile1Zone = Zone.HAND;
                pile2Zone = Zone.GRAVEYARD;
            }

            StringBuilder sb = new StringBuilder(sourceObject.getLogName() + ": Pile 1, going to ").append(pile1Zone == Zone.HAND ? "Hand" : "Graveyard").append(": ");
            int i = 0;
            for (UUID cardUuid : pile1CardsIds) {
                i++;
                Card card = game.getCard(cardUuid);
                if (card != null) {
                    sb.append(GameLog.getColoredObjectName(card));
                    if (i < pile1CardsIds.size()) {
                        sb.append(", ");
                    }
                    cardsToGraveyard.add(card);
                }
            }
            controller.moveCards(cardsToGraveyard, pile1Zone, source, game);
            game.informPlayers(sb.toString());

            sb = new StringBuilder(sourceObject.getLogName() + ": Pile 2, going to ").append(pile2Zone == Zone.HAND ? "Hand" : "Graveyard").append(':');
            i = 0;
            for (UUID cardUuid : pile2CardsIds) {
                Card card = game.getCard(cardUuid);
                if (card != null) {
                    i++;
                    sb.append(' ').append(GameLog.getColoredObjectName(card));
                    if (i < pile2CardsIds.size()) {
                        sb.append(", ");
                    }
                    cardsToHand.add(card);
                }
            }
            controller.moveCards(cardsToHand, pile2Zone, source, game);
            game.informPlayers(sb.toString());
        }

        return true;
    }
}
