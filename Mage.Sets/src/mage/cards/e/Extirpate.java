package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author jonubuu
 */
public final class Extirpate extends CardImpl {

    private static final FilterCard filter = new FilterCard("card in a graveyard other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(CardType.LAND.getPredicate(), SuperType.BASIC.getPredicate())));
    }

    public Extirpate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Split second
        this.addAbility(new SplitSecondAbility());
        // Choose target card in a graveyard other than a basic land card. 
        // Search its owner's graveyard, hand, and library for all cards with 
        // the same name as that card and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new ExtirpateEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    private Extirpate(final Extirpate card) {
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
        this.staticText = "Choose target card in a graveyard other than "
                + "a basic land card. Search its owner's graveyard, hand, "
                + "and library for any number of cards with the same name "
                + "as that card and exile them. Then that player shuffles";
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
        MageObject sourceObject = game.getObject(source);
        Card chosenCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (chosenCard != null && sourceObject != null && controller != null) {
            Player owner = game.getPlayer(chosenCard.getOwnerId());
            if (owner == null) {
                return false;
            }

            // Exile all cards with the same name
            // Building a card filter with the name
            FilterCard filterNamedCard = new FilterCard();
            String nameToSearch = CardUtil.getCardNameForSameNameSearch(chosenCard);
            filterNamedCard.add(new NamePredicate(nameToSearch));

            // The cards you're searching for must be found and exiled if they're in the graveyard because it's a public zone.
            // Finding those cards in the hand and library is optional, because those zones are hidden (even if the hand is temporarily revealed).
            // search cards in graveyard
            for (Card checkCard : owner.getGraveyard().getCards(game)) {
                if (checkCard.getName().equals(chosenCard.getName())) {
                    controller.moveCardToExileWithInfo(checkCard, null, "", source, game, Zone.GRAVEYARD, true);
                }
            }

            // search cards in hand
            filterNamedCard.setMessage("card named " + chosenCard.getLogName() + " in the hand of " + owner.getLogName());
            TargetCard targetCardInHand = new TargetCard(0, Integer.MAX_VALUE, Zone.HAND, filterNamedCard);
            targetCardInHand.setNotTarget(true);
            if (controller.chooseTarget(Outcome.Exile, owner.getHand(), targetCardInHand, source, game)) {
                List<UUID> targets = targetCardInHand.getTargets();
                for (UUID targetId : targets) {
                    Card targetCard = owner.getHand().get(targetId, game);
                    if (targetCard != null) {
                        controller.moveCardToExileWithInfo(targetCard, null, "", source, game, Zone.HAND, true);
                    }
                }
            }

            // search cards in Library
            filterNamedCard.setMessage("card named " + chosenCard.getName() + " in the library of " + owner.getName());
            TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(0, Integer.MAX_VALUE, filterNamedCard);
            if (controller.searchLibrary(targetCardInLibrary, source, game, owner.getId())) {
                List<UUID> targets = targetCardInLibrary.getTargets();
                for (UUID targetId : targets) {
                    Card targetCard = owner.getLibrary().getCard(targetId, game);
                    if (targetCard != null) {
                        controller.moveCardToExileWithInfo(targetCard, null, "", source, game, Zone.LIBRARY, true);
                    }
                }
            }
            owner.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

}
