package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ShimianSpecter extends CardImpl {

    public ShimianSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.SPECTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Shimian Specter deals combat damage to a player, that player 
        // reveals their hand. You choose a nonland card from it. Search that player's 
        // graveyard, hand, and library for all cards with the same name as that card 
        // and exile them. Then that player shuffles their library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ShimianSpecterEffect(), false, true));
    }

    private ShimianSpecter(final ShimianSpecter card) {
        super(card);
    }

    @Override
    public ShimianSpecter copy() {
        return new ShimianSpecter(this);
    }
}

class ShimianSpecterEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public ShimianSpecterEffect() {
        super(Outcome.Benefit);
        staticText = "that player reveals their hand. You choose a nonland card from it. "
                + "Search that player's graveyard, hand, and library for all cards "
                + "with the same name as that card and exile them. Then that "
                + "player shuffles";
    }

    public ShimianSpecterEffect(final ShimianSpecterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (targetPlayer != null
                && sourceObject != null
                && controller != null) {

            // reveal hand of target player
            targetPlayer.revealCards(sourceObject.getName(), targetPlayer.getHand(), game);

            // You choose a nonland card from it
            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard());
            target.setNotTarget(true);
            Card chosenCard = null;
            if (target.canChoose(controller.getId(), source, game)
                    && controller.chooseTarget(Outcome.Benefit, targetPlayer.getHand(), target, source, game)) {
                chosenCard = game.getCard(target.getFirstTarget());
            }

            // Exile all cards with the same name
            // Building a card filter with the name
            FilterCard filterNamedCards = new FilterCard();
            String nameToSearch = "---";// so no card matches
            if (chosenCard != null) {
                nameToSearch = CardUtil.getCardNameForSameNameSearch(chosenCard);
            }
            filterNamedCards.add(new NamePredicate(nameToSearch));

            // The cards you're searching for must be found and exiled if they're 
            // in the graveyard because it's a public zone.
            // Finding those cards in the hand and library is optional, because 
            // those zones are hidden (even if the hand is temporarily revealed).
            // search cards in graveyard
            if (chosenCard != null) {
                for (Card checkCard : targetPlayer.getGraveyard().getCards(game)) {
                    if (checkCard.getName().equals(chosenCard.getName())) {
                        controller.moveCardToExileWithInfo(checkCard, null, "",
                                source, game, Zone.GRAVEYARD, true);
                    }
                }

                // search cards in hand
                TargetCard targetHandCards = new TargetCard(0, Integer.MAX_VALUE, Zone.HAND, filterNamedCards);
                controller.chooseTarget(Outcome.Benefit, targetPlayer.getHand(), targetHandCards, source, game);
                for (UUID cardId : targetHandCards.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "",
                                source, game, Zone.HAND, true);
                    }
                }
            }

            // search cards in Library
            // If the player has no nonland cards in their hand, you can still search 
            // that player's library and have that player shuffle it.
            if (chosenCard != null
                    || controller.chooseUse(outcome, "Search library anyway?", source, game)) {
                TargetCardInLibrary targetCardsLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCards);
                controller.searchLibrary(targetCardsLibrary, source, game, targetPlayer.getId());
                for (UUID cardId : targetCardsLibrary.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.LIBRARY, true);
                    }
                }
                targetPlayer.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public ShimianSpecterEffect copy() {
        return new ShimianSpecterEffect(this);
    }

}
