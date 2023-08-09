package mage.cards.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.ApprovingObject;
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
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class BrilliantUltimatum extends CardImpl {

    public BrilliantUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}{U}{U}{U}{B}{B}");

        // Exile the top five cards of your library. An opponent separates those cards into two piles. You may play any number of cards from one of those piles without paying their mana costs.
        this.getSpellAbility().addEffect(new BrilliantUltimatumEffect());
    }

    private BrilliantUltimatum(final BrilliantUltimatum card) {
        super(card);
    }

    @Override
    public BrilliantUltimatum copy() {
        return new BrilliantUltimatum(this);
    }
}

class BrilliantUltimatumEffect extends OneShotEffect {

    public BrilliantUltimatumEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top five cards of your library. An opponent separates those cards into two piles. You may play any number of cards from one of those piles without paying their mana costs";
    }

    public BrilliantUltimatumEffect(final BrilliantUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public BrilliantUltimatumEffect copy() {
        return new BrilliantUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Cards pile2 = new CardsImpl();
        pile2.addAllCards(controller.getLibrary().getTopCards(game, 5));
        controller.moveCardsToExile(pile2.getCards(game), source, game, true, source.getSourceId(), sourceObject.getIdName());

        TargetOpponent targetOpponent = new TargetOpponent(true);
        targetOpponent.choose(outcome, source.getControllerId(), source.getSourceId(), source, game);
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent != null) {
            TargetCard target = new TargetCard(0, pile2.size(), Zone.EXILED, new FilterCard("cards to put in the first pile"));
            target.setRequired(false);
            Cards pile1 = new CardsImpl();
            List<Card> pileOne = new ArrayList<>();
            List<Card> pileTwo = new ArrayList<>();
            if (opponent.choose(Outcome.Neutral, pile2, target, source, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = pile2.get(targetId, game);
                    if (card != null) {
                        pile1.add(card);
                        pile2.remove(card);
                    }
                }
            }
            pileOne.addAll(pile1.getCards(game));
            pileTwo.addAll(pile2.getCards(game));
            controller.revealCards("Pile 1 - " + sourceObject.getIdName(), pile1, game);
            controller.revealCards("Pile 2 - " + sourceObject.getIdName(), pile2, game);

            boolean choice = controller.choosePile(Outcome.PlayForFree, "Which pile (play for free)?", pileOne, pileTwo, game);
            String selectedPileName;
            List<Card> selectedPileCards;
            Cards selectedPile;
            if (choice) {
                selectedPileName = "pile 1";
                selectedPileCards = pileOne;
                selectedPile = pile1;
            } else {
                selectedPileName = "pile 2";
                selectedPileCards = pileTwo;
                selectedPile = pile2;
            }
            game.informPlayers(controller.getLogName() + " chose " + selectedPileName + '.');
            while (!selectedPileCards.isEmpty() && controller.chooseUse(Outcome.PlayForFree, "Play a card for free from " + selectedPileName + '?', source, game)) {
                TargetCard targetExiledCard = new TargetCard(Zone.EXILED, new FilterCard());
                if (controller.chooseTarget(Outcome.PlayForFree, selectedPile, targetExiledCard, source, game)) {
                    Card card = selectedPile.get(targetExiledCard.getFirstTarget(), game);
                    if (controller.playCard(card, game, true, new ApprovingObject(source, game))) {
                        selectedPileCards.remove(card);
                        selectedPile.remove(card);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
