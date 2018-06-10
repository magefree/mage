
package mage.cards.e;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jonubuu
 */
public final class Extirpate extends CardImpl {

    private static final FilterCard filter = new FilterCard("card in a graveyard other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(new CardTypePredicate(CardType.LAND), new SupertypePredicate(SuperType.BASIC))));
    }

    public Extirpate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Split second
        this.addAbility(new SplitSecondAbility());
        // Choose target card in a graveyard other than a basic land card. Search its owner's graveyard, hand, and library for all cards with the same name as that card and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new ExtirpateEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    public Extirpate(final Extirpate card) {
        super(card);
    }

    @Override
    public Extirpate copy() {
        return new Extirpate(this);
    }
}

class ExtirpateEffect extends OneShotEffect {

    public ExtirpateEffect() {
        super(Outcome.Exile);
        this.staticText = "Choose target card in a graveyard other than a basic land card. Search its owner's graveyard, hand, and library for any number of cards with the same name as that card and exile them. Then that player shuffles their library";
    }

    public ExtirpateEffect(final ExtirpateEffect effect) {
        super(effect);
    }

    @Override
    public ExtirpateEffect copy() {
        return new ExtirpateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        Card chosenCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (chosenCard != null && sourceObject != null && controller != null) {
            Player owner = game.getPlayer(chosenCard.getOwnerId());
            if (owner == null) {
                return false;
            }

            // Exile all cards with the same name
            // Building a card filter with the name
            FilterCard filterNamedCard = new FilterCard();
            String nameToSearch = chosenCard.isSplitCard() ? ((SplitCard) chosenCard).getLeftHalfCard().getName() : chosenCard.getName();
            filterNamedCard.add(new NamePredicate(nameToSearch));

            // The cards you're searching for must be found and exiled if they're in the graveyard because it's a public zone.
            // Finding those cards in the hand and library is optional, because those zones are hidden (even if the hand is temporarily revealed).
            // search cards in graveyard
            for (Card checkCard : owner.getGraveyard().getCards(game)) {
                if (checkCard.getName().equals(chosenCard.getName())) {
                    controller.moveCardToExileWithInfo(checkCard, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
            }

            // search cards in hand
            filterNamedCard.setMessage("card named " + chosenCard.getLogName() + " in the hand of " + owner.getLogName());
            TargetCardInHand targetCardInHand = new TargetCardInHand(0, Integer.MAX_VALUE, filterNamedCard);
            if (controller.choose(Outcome.Exile, owner.getHand(), targetCardInHand, game)) {
                List<UUID> targets = targetCardInHand.getTargets();
                for (UUID targetId : targets) {
                    Card targetCard = owner.getHand().get(targetId, game);
                    if (targetCard != null) {
                        controller.moveCardToExileWithInfo(targetCard, null, "", source.getSourceId(), game, Zone.HAND, true);
                    }
                }
            }

            // search cards in Library
            filterNamedCard.setMessage("card named " + chosenCard.getName() + " in the library of " + owner.getName());
            TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCard);
            if (controller.searchLibrary(targetCardInLibrary, game, owner.getId())) {
                List<UUID> targets = targetCardInLibrary.getTargets();
                for (UUID targetId : targets) {
                    Card targetCard = owner.getLibrary().getCard(targetId, game);
                    if (targetCard != null) {
                        controller.moveCardToExileWithInfo(targetCard, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                    }
                }
            }
            owner.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

}
