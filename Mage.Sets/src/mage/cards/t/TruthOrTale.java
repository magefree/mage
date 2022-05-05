package mage.cards.t;

import java.util.*;

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

/**
 *
 * @author noahg
 */
public final class TruthOrTale extends CardImpl {

    public TruthOrTale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        

        // Reveal the top five cards of your library and separate them into two piles. An opponent chooses one of those piles. Put a card from the chosen pile into your hand, then put all other cards revealed this way on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new TruthOrTaleEffect());
    }

    private TruthOrTale(final TruthOrTale card) {
        super(card);
    }

    @Override
    public TruthOrTale copy() {
        return new TruthOrTale(this);
    }
}

class TruthOrTaleEffect extends OneShotEffect {

    public TruthOrTaleEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library and separate them into two piles. An opponent chooses one of those piles. Put a card from the chosen pile into your hand, then put all other cards revealed this way on the bottom of your library in any order";
    }

    public TruthOrTaleEffect(final TruthOrTaleEffect effect) {
        super(effect);
    }

    @Override
    public TruthOrTaleEffect copy() {
        return new TruthOrTaleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
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
            target.setRequired(false);
            if (controller.choose(Outcome.Neutral, cards, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        pile1.add(card);
                    }
                }
            }
            List<Card> pile2 = new ArrayList<>();
            for (UUID cardId : cards) {
                Card card = game.getCard(cardId);
                if (card != null && !pile1.contains(card)) {
                    pile2.add(card);
                }
            }
            boolean choice = opponent.choosePile(Outcome.Detriment, "Choose a pile for " + controller.getName() + " to choose a card from.", pile1, pile2, game);

            List<Card> chosen = choice ? pile1 : pile2;
            if (!chosen.isEmpty()) {
                Cards chosenCards = new CardsImpl(new HashSet<>(chosen));
                TargetCard finalChosenCardTarget = new TargetCard(Zone.LIBRARY,new FilterCard("card to put into your hand"));
                if (controller.choose(Outcome.DrawCard, chosenCards, finalChosenCardTarget, game)){
                    Card finalChosenCard = game.getCard(finalChosenCardTarget.getFirstTarget());
                    if (finalChosenCard != null) {
                        if (!game.isSimulation()) {
                            game.informPlayers(controller.getLogName() + " chose to put " + finalChosenCard.getIdName() + " into their hand.");
                        }
                        cards.remove(finalChosenCard);
                        controller.moveCards(finalChosenCard, Zone.HAND, source, game);
                    }
                }
            }

            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        }

        return true;
    }
}
