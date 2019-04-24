
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
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

        // Whenever Shimian Specter deals combat damage to a player, that player reveals their hand. You choose a nonland card from it. Search that player's graveyard, hand, and library for all cards with the same name as that card and exile them. Then that player shuffles their library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ShimianSpecterEffect(), false, true));
    }

    public ShimianSpecter(final ShimianSpecter card) {
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
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public ShimianSpecterEffect() {
        super(Outcome.Benefit);
        staticText = "that player reveals their hand. You choose a nonland card from it. Search that player's graveyard, hand, and library for all cards with the same name as that card and exile them. Then that player shuffles their library";
    }

    public ShimianSpecterEffect(final ShimianSpecterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (targetPlayer != null && sourceObject != null && controller != null) {

            // reveal hand of target player
            targetPlayer.revealCards(sourceObject.getName(), targetPlayer.getHand(), game);

            // You choose a nonland card from it
            TargetCardInHand target = new TargetCardInHand(new FilterNonlandCard());
            target.setNotTarget(true);
            Card chosenCard = null;
            if (controller.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                chosenCard = game.getCard(target.getFirstTarget());
            }

            // Exile all cards with the same name
            // Building a card filter with the name
            FilterCard filterNamedCards = new FilterCard();
            String nameToSearch = "---";// so no card matches
            if (chosenCard != null) {
                nameToSearch = chosenCard.isSplitCard() ? ((SplitCard) chosenCard).getLeftHalfCard().getName() : chosenCard.getName();
            }
            filterNamedCards.add(new NamePredicate(nameToSearch));

            // The cards you're searching for must be found and exiled if they're in the graveyard because it's a public zone.
            // Finding those cards in the hand and library is optional, because those zones are hidden (even if the hand is temporarily revealed).
            // search cards in graveyard
            if (chosenCard != null) {
                for (Card checkCard : targetPlayer.getGraveyard().getCards(game)) {
                    if (checkCard.getName().equals(chosenCard.getName())) {
                        controller.moveCardToExileWithInfo(checkCard, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                    }
                }

                // search cards in hand
                TargetCardInHand targetHandCards = new TargetCardInHand(0, Integer.MAX_VALUE, filterNamedCards);
                controller.chooseTarget(outcome, targetPlayer.getHand(), targetHandCards, source, game);
                for (UUID cardId : targetHandCards.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.HAND, true);
                    }
                }
            }

            // search cards in Library
            // If the player has no nonland cards in their hand, you can still search that player's library and have him or her shuffle it.
            if (chosenCard != null || controller.chooseUse(outcome, "Search library anyway?", source, game)) {
                TargetCardInLibrary targetCardsLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCards);
                controller.searchLibrary(targetCardsLibrary, game, targetPlayer.getId());
                for (UUID cardId : targetCardsLibrary.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
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
