package mage.cards.z;

import mage.abilities.Ability;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class ZimonesExperiment extends CardImpl {

    public ZimonesExperiment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Look at the top five cards of your library.
        //
        // You may reveal up to two creature and/or land cards from among them,
        // then put the rest on the bottom of your library in a random order.
        //
        // Put all land cards revealed this way onto the battlefield tapped and
        // put all creature cards revealed this way into your hand.
        this.getSpellAbility().addEffect(new ZimonesExperimentEffect());
    }

    private ZimonesExperiment(final ZimonesExperiment card) {
        super(card);
    }

    @Override
    public ZimonesExperiment copy() {
        return new ZimonesExperiment(this);
    }
}

class ZimonesExperimentEffect extends LookLibraryAndPickControllerEffect {

    private static final FilterCard filterCard = new FilterCard("creature and/or land cards");

    static {
        filterCard.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.LAND.getPredicate()
        ));
    }

    ZimonesExperimentEffect() {
        super(5, 2, filterCard, PutCards.HAND, PutCards.BOTTOM_RANDOM);
        this.staticText = "look at the top five cards of your library. "
            + "You may reveal up to two creature and/or land cards from among them, "
            + "then put the rest on the bottom of your library in a random order. Put "
            + "all land cards revealed this way onto the battlefield tapped and put "
            + "all creature cards revealed this way into your hand";
    }

    protected ZimonesExperimentEffect(final ZimonesExperimentEffect effect) {
        super(effect);
    }

    @Override
    public ZimonesExperimentEffect copy() {
        return new ZimonesExperimentEffect(this);
    }

    @Override
    protected boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        boolean result = putLookedCards.moveCards(player, otherCards, source, game);
        if (pickedCards.isEmpty()) {
            return result;
        }

        Cards landCards = new CardsImpl();
        Cards creatureCards = new CardsImpl();
        for (UUID cardId : pickedCards) {
            Card card = game.getCard(cardId);
            if (card == null) {
                continue;
            }

            if (card.isLand(game)) {
                landCards.add(cardId);
            } else {
                creatureCards.add(cardId);
            }
        }

        if (!landCards.isEmpty()) {
            result |= PutCards.BATTLEFIELD_TAPPED.moveCards(player, landCards, source, game);
        }

        if (!creatureCards.isEmpty()) {
            result |= PutCards.HAND.moveCards(player, creatureCards, source, game);
        }
        return result;
    }
}
