package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author North
 */
public final class FactOrFiction extends CardImpl {

    public FactOrFiction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Reveal the top five cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new FactOrFictionEffect());
    }

    private FactOrFiction(final FactOrFiction card) {
        super(card);
    }

    @Override
    public FactOrFiction copy() {
        return new FactOrFiction(this);
    }
}

class FactOrFictionEffect extends OneShotEffect {

    FactOrFictionEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard";
    }

    private FactOrFictionEffect(final FactOrFictionEffect effect) {
        super(effect);
    }

    @Override
    public FactOrFictionEffect copy() {
        return new FactOrFictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        controller.revealCards(sourceObject.getName(), cards, game);

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.isEmpty()) {
            Player opponent = game.getPlayer(opponents.iterator().next());
            if (opponents.size() > 1) {
                Target targetOpponent = new TargetOpponent(true);
                if (controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game)) {
                    opponent = game.getPlayer(targetOpponent.getFirstTarget());
                    game.informPlayers(controller.getLogName() + " chose " + opponent.getLogName() + " to separate the revealed cards");
                }
            }
            TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, new FilterCard("cards to put in the first pile"));
            List<Card> pile1 = new ArrayList<>();
            if (opponent.choose(Outcome.Neutral, cards, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = cards.get(targetId, game);
                    if (card != null) {
                        pile1.add(card);
                        cards.remove(card);
                    }
                }
            }
            List<Card> pile2 = new ArrayList<>();
            pile2.addAll(cards.getCards(game));

            boolean choice = controller.choosePile(outcome, "Choose a pile to put into your hand.", pile1, pile2, game);

            Zone pile1Zone = Zone.GRAVEYARD;
            Zone pile2Zone = Zone.HAND;
            if (choice) {
                pile1Zone = Zone.HAND;
                pile2Zone = Zone.GRAVEYARD;
            }

            StringBuilder sb = new StringBuilder("Pile 1, going to ").append(pile1Zone == Zone.HAND ? "Hand" : "Graveyard").append(": ");
            int i = 0;
            for (Card card : pile1) {
                i++;
                sb.append(card.getName());
                if (i < pile1.size()) {
                    sb.append(", ");
                }
            }
            cards.clear();
            cards.addAll(pile1);
            controller.moveCards(cards, pile1Zone, source, game);
            game.informPlayers(sb.toString());

            sb = new StringBuilder("Pile 2, going to ").append(pile2Zone == Zone.HAND ? "Hand" : "Graveyard").append(':');
            i = 0;
            for (Card card : pile2) {
                i++;
                sb.append(' ').append(card.getName());
                if (i < pile2.size()) {
                    sb.append(", ");
                }
            }
            cards.clear();
            cards.addAll(pile2);
            controller.moveCards(cards, pile2Zone, source, game);
            game.informPlayers(sb.toString());
        }

        return true;
    }
}
