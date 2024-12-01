package mage.cards.t;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.*;

/**
 * @author frafen, xenohedron
 */
public final class TheTaleOfTamiyo extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant, sorcery, and/or Tamiyo planeswalker cards from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SubType.TAMIYO.getPredicate()
        ));
    }

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

        // IV -- Exile any number of target instant, sorcery, and/or Tamiyo planeswalker cards from your graveyard. Copy them. You may cast any number of the copies.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new TheTaleOfTamiyoEffect2(),
                new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, filter)
        );

        this.addAbility(sagaAbility);
    }

    private TheTaleOfTamiyo(final TheTaleOfTamiyo card) {
        super(card);
    }

    @Override
    public TheTaleOfTamiyo copy() {
        return new TheTaleOfTamiyo(this);
    }
}

// Based on Grindstone
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
        if (controller == null) {
            return false;
        }

        // In case of a replacement effect that would cause an infinite loop on repeating the process
        int possibleIterations = controller.getLibrary().size() / 2;
        int iteration = 0;

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

        } while (controller.canRespond());

        return true;
    }


}

class TheTaleOfTamiyoEffect2 extends OneShotEffect {

    TheTaleOfTamiyoEffect2() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile any number of target instant, sorcery, and/or Tamiyo planeswalker cards " +
                "from your graveyard. Copy them. You may cast any number of the copies.";
    }

    private TheTaleOfTamiyoEffect2(final TheTaleOfTamiyoEffect2 effect) {
        super(effect);
    }

    @Override
    public TheTaleOfTamiyoEffect2 copy() {
        return new TheTaleOfTamiyoEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Cards toExile = new CardsImpl();
        getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .forEach(toExile::add);
        if (controller == null || toExile.isEmpty()) {
            return false;
        }
        controller.moveCards(toExile, Zone.EXILED, source, game);
        toExile.retainZone(Zone.EXILED, game);
        for (Card card : toExile.getCards(game)) {
            if (controller.chooseUse(outcome, "Cast copy of " + card.getName() + "?", source, game)) {
                Card cardCopy = game.copyCard(card, source, source.getControllerId());
                game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), Boolean.TRUE);
                controller.cast(
                        controller.chooseAbilityForCast(cardCopy, game, false),
                        game, false, new ApprovingObject(source, game)
                );
                game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), null);
            }
        }
        return true;
    }
}
