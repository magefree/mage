package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Lobotomy extends CardImpl {

    public Lobotomy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Target player reveals their hand, then you choose a card other than a basic land card from it. Search that player's graveyard, hand, and library for all cards with the same name as the chosen card and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new LobotomyEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Lobotomy(final Lobotomy card) {
        super(card);
    }

    @Override
    public Lobotomy copy() {
        return new Lobotomy(this);
    }
}

class LobotomyEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(CardType.LAND.getPredicate(), SuperType.BASIC.getPredicate())));
    }

    public LobotomyEffect() {
        super(Outcome.Benefit);
        staticText = "Target player reveals their hand, then you choose a card other than a basic land card from it. Search that player's graveyard, hand, and library for all cards with the same name as the chosen card and exile them. Then that player shuffles";
    }

    public LobotomyEffect(final LobotomyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (targetPlayer != null && sourceObject != null && controller != null) {

            // reveal hand of target player
            targetPlayer.revealCards(sourceObject.getIdName(), targetPlayer.getHand(), game);

            // You choose card other than a basic land card
            TargetCard target = new TargetCard(Zone.HAND, filter);
            target.setNotTarget(true);
            Card chosenCard = null;
            if (controller.chooseTarget(Outcome.Benefit, targetPlayer.getHand(), target, source, game)) {
                chosenCard = game.getCard(target.getFirstTarget());
            }

            // Exile all cards with the same name
            // Building a card filter with the name
            FilterCard filterNamedCards = new FilterCard();
            String nameToSearch = "---";// so no card matches
            if (chosenCard != null) {
                nameToSearch = CardUtil.getCardNameForSameNameSearch(chosenCard);
                filterNamedCards.setMessage("cards named " + nameToSearch);
            }
            filterNamedCards.add(new NamePredicate(nameToSearch));
            Cards cardsToExile = new CardsImpl();
            // The cards you're searching for must be found and exiled if they're in the graveyard because it's a public zone.
            // Finding those cards in the hand and library is optional, because those zones are hidden (even if the hand is temporarily revealed).
            // search cards in graveyard
            if (chosenCard != null) {
                for (Card checkCard : targetPlayer.getGraveyard().getCards(game)) {
                    if (checkCard.getName().equals(chosenCard.getName())) {
                        cardsToExile.add(checkCard);
                    }
                }
                // search cards in hand
                TargetCard targetCardsHand = new TargetCard(0, Integer.MAX_VALUE, Zone.HAND, filterNamedCards);
                controller.chooseTarget(outcome, targetPlayer.getHand(), targetCardsHand, source, game);
                for (UUID cardId : targetCardsHand.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        cardsToExile.add(card);
                    }
                }
            }

            // search cards in Library
            // If the player has no nonland cards in their hand, you can still search that player's library and have that player shuffle it.
            if (chosenCard != null || controller.chooseUse(outcome, "Search library anyway?", source, game)) {
                TargetCardInLibrary targetCardsLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCards);
                controller.searchLibrary(targetCardsLibrary, source, game, targetPlayer.getId());
                for (UUID cardId : targetCardsLibrary.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        cardsToExile.add(card);
                    }
                }

            }
            if (!cardsToExile.isEmpty()) {
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);
            }
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public LobotomyEffect copy() {
        return new LobotomyEffect(this);
    }
}
