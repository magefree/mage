package mage.cards.t;

import java.util.*;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author frafen and chat gpt
 */
public final class TheTaleOfTamiyo extends CardImpl {


    public TheTaleOfTamiyo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);
        // I, II, III -- Mill two cards. If two cards that share a card type were milled this way, draw a card and repeat this process.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new TheTaleOfTamiyoEffect1()
        );
        // Saga effect for Chapter IV -- "Exile any number of target instant, sorcery, and/or Tamiyo planeswalker cards from your graveyard. Copy them. You may cast any number of the copies."
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new TheTaleOfTamiyoEffect2()
        );

        this.addAbility(sagaAbility);
    }

    private TheTaleOfTamiyo(final TheTaleOfTamiyo card) {
        super(card);
    }

    @Override
    public TheTaleOfTamiyo copy() {return new TheTaleOfTamiyo(this); }
}
class TheTaleOfTamiyoEffect1 extends OneShotEffect {

    TheTaleOfTamiyoEffect1() {
        super(Outcome.DrawCard);
        this.staticText = "Mill two cards. If two cards that share a card type were milled this way, draw a card and repeat this process.";
    }
    private TheTaleOfTamiyoEffect1(final TheTaleOfTamiyoEffect1 effect) {
        super(effect);
    }

    @Override
    public TheTaleOfTamiyoEffect1 copy() {
        return new TheTaleOfTamiyoEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            int possibleIterations = controller.getLibrary().size() / 2;
            int iteration = 0;

            // Stop if there are not enough cards
            if (controller.getLibrary().size() < 2) {
                return true;
            }

            do {
                iteration++;
                if (iteration > possibleIterations + 20) {
                    // Protection against infinity loops
                    game.setDraw(source.getControllerId());
                    return true;
                }

                // Mill 2 cards
                List<Card> cards = new ArrayList<>(controller
                        .millCards(2, source, game)
                        .getCards(game));

                // Stop if less than 2 cards were milled
                if (cards.size() < 2) {
                    break;
                }

                // Set to hold types of the first card
                Set<CardType> firstCardTypes = new HashSet<>(cards.get(0).getCardType());

                // Check if there's at least one type in common
                boolean typesMatch = false;

                // Iterate over the second card's types to see if there is any overlap with the first card's types
                for (CardType type : cards.get(1).getCardType()) {
                    if (firstCardTypes.contains(type)) {
                        typesMatch = true;
                        break;
                    }
                }

                // If there is a match, draw a card and continue; otherwise, break
                if (typesMatch) {
                    controller.drawCards(1, source, game);
                } else {
                    break;
                }

            } while (controller.getLibrary().size() >= 2); // Continue if there are at least 2 cards left in the library

            return true;
        }
        return false;
    }



}
class TheTaleOfTamiyoEffect2 extends OneShotEffect {

    private final FilterCard filter;

    TheTaleOfTamiyoEffect2() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile any number of target instant, sorcery, and/or Tamiyo planeswalker cards " +
                "from your graveyard. Copy them. You may cast any number of the copies.";

        // Initialize filter
        this.filter = new FilterCard("instant, sorcery, or Tamiyo planeswalker card");
        this.filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SubType.TAMIYO.getPredicate()
        ));
    }

    private TheTaleOfTamiyoEffect2(final TheTaleOfTamiyoEffect2 effect) {
        super(effect);
        this.filter = effect.filter;  // Copy filter
    }

    @Override
    public TheTaleOfTamiyoEffect2 copy() {
        return new TheTaleOfTamiyoEffect2(this);
    }
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Use filter to choose the cards to exile
            Set<Card> cardsToExile = controller.getGraveyard().getCards(
                    filter, source.getControllerId(), source, game);

            if (!cardsToExile.isEmpty()) {
                // Move cards from graveyard to exile
                if (controller.moveCards(cardsToExile, Zone.EXILED, source, game)) {
                    Cards copiedCards = new CardsImpl();
                    // Copy cards
                    for (Card card : cardsToExile) {
                        copiedCards.add(game.copyCard(card, source, source.getControllerId()));
                    }

                    // Cast copies
                    boolean continueCasting = true;
                    while (controller.canRespond() && continueCasting && !copiedCards.isEmpty()) {
                        // Ask player what to cast
                        TargetCard targetCard = new TargetCard(0, 1, Zone.EXILED,
                                new FilterCard("copied card to cast paying its mana cost?"));
                        targetCard.withNotTarget(true); // To not select one card multiple times

                        if (controller.chooseTarget(Outcome.PlayForFree, copiedCards, targetCard, source, game)) {
                            Card selectedCard = game.getCard(targetCard.getFirstTarget());
                            if (selectedCard != null
                                    && selectedCard.getSpellAbility().canChooseTarget(game, controller.getId())) {
                                game.getState().setValue("PlayFromNotOwnHandZone" + selectedCard.getId(), Boolean.TRUE);
                                controller.cast(controller.chooseAbilityForCast(selectedCard, game, false),
                                        game, false, new ApprovingObject(source, game));
                                game.getState().setValue("PlayFromNotOwnHandZone" + selectedCard.getId(), null);
                            }
                            copiedCards.remove(selectedCard);
                        }

                        // Verify if player wants to cast more cards
                        continueCasting = !copiedCards.isEmpty()
                                && controller.chooseUse(Outcome.Benefit, "Continue to choose copies and cast?", source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
