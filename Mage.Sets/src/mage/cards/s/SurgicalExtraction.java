package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
 * @author North
 */
public final class SurgicalExtraction extends CardImpl {

    private static final FilterCard filter = new FilterCard("card in a graveyard other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(CardType.LAND.getPredicate(), SuperType.BASIC.getPredicate())));
    }

    public SurgicalExtraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B/P}");

        // Choose target card in a graveyard other than a basic land card. Search its owner's graveyard,
        // hand, and library for any number of cards with the same name as that card and exile them.
        // Then that player shuffles their library.
        this.getSpellAbility().addEffect(new SurgicalExtractionEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    private SurgicalExtraction(final SurgicalExtraction card) {
        super(card);
    }

    @Override
    public SurgicalExtraction copy() {
        return new SurgicalExtraction(this);
    }
}

class SurgicalExtractionEffect extends OneShotEffect {

    public SurgicalExtractionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose target card in a graveyard other than a basic land card. "
                + "Search its owner's graveyard, hand, and library for any number of cards "
                + "with the same name as that card and exile them. Then that player shuffles";
    }

    private SurgicalExtractionEffect(final SurgicalExtractionEffect effect) {
        super(effect);
    }

    @Override
    public SurgicalExtractionEffect copy() {
        return new SurgicalExtractionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // 6/1/2011 	"Any number of cards" means just that. If you wish, you can choose to
        //              leave some or all of the cards with the same name as the targeted card,
        //              including that card, in the zone they're in.

        Card chosenCard = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (chosenCard != null && controller != null) {
            Player owner = game.getPlayer(chosenCard.getOwnerId());
            if (owner != null) {
                String nameToSearch = CardUtil.getCardNameForSameNameSearch(chosenCard);
                FilterCard filterNamedCard = new FilterCard("card named " + nameToSearch);
                filterNamedCard.add(new NamePredicate(nameToSearch));

                // cards in Graveyard
                int cardsCount = owner.getGraveyard().count(filterNamedCard, game);
                if (cardsCount > 0) {
                    filterNamedCard.setMessage("card named " + nameToSearch
                            + " in the graveyard of " + owner.getName());
                    TargetCardInGraveyard target = new TargetCardInGraveyard(0, cardsCount, filterNamedCard);
                    if (controller.chooseTarget(Outcome.Exile, owner.getGraveyard(), target, source, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card targetCard = owner.getGraveyard().get(targetId, game);
                            if (targetCard != null) {
                                controller.moveCardToExileWithInfo(targetCard, null,
                                        "", source, game, Zone.GRAVEYARD, true);
                            }
                        }
                    }
                }

                // cards in Hand
                filterNamedCard.setMessage("card named " + nameToSearch + " in the hand of " + owner.getName());
                TargetCard targetCardInHand = new TargetCard(0, Integer.MAX_VALUE, Zone.HAND, filterNamedCard);
                targetCardInHand.withNotTarget(true);
                if (controller.chooseTarget(Outcome.Exile, owner.getHand(), targetCardInHand, source, game)) {
                    List<UUID> targets = targetCardInHand.getTargets();
                    for (UUID targetId : targets) {
                        Card targetCard = owner.getHand().get(targetId, game);
                        if (targetCard != null) {
                            controller.moveCardToExileWithInfo(targetCard, null, "", source, game, Zone.HAND, true);
                        }
                    }
                }

                // cards in Library
                filterNamedCard.setMessage("card named " + nameToSearch + " in the library of " + owner.getName());
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
        }

        return false;
    }
}
