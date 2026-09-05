package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class PartInFriendship extends CardImpl {

    public PartInFriendship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // Whenever a nontoken creature you control dies, reveal cards from the top of your library until you reveal a creature card.
        // If its mana value is less than or equal to the number of lands you control, put it onto the battlefield.
        // Otherwise, put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        // This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new PartInFriendshipEffect(), false,
            StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, true
        ).setTriggersLimitEachTurn(1));
    }

    private PartInFriendship(final PartInFriendship card) {
        super(card);
    }

    @Override
    public PartInFriendship copy() {
        return new PartInFriendship(this);
    }
}

class PartInFriendshipEffect extends OneShotEffect {

    public PartInFriendshipEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "reveal cards from the top of your library until you reveal a creature card. "
            + "If its mana value is less than or equal to the number of lands you control, put it onto the battlefield. "
            + "Otherwise, put it into your hand. "
            + "Put the rest on the bottom of your library in a random order.";
    }

    private PartInFriendshipEffect(final PartInFriendshipEffect effect) {
        super(effect);
    }

    @Override
    public PartInFriendshipEffect copy() {
        return new PartInFriendshipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        Card creatureCard = null;

        while (true) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                break;
            }
            cards.add(card);
            if (card.isCreature(game)) {
                creatureCard = card;
                break;
            }
        }
        controller.revealCards(source, cards, game);

        if (creatureCard != null) {
            int landCount = game.getBattlefield().countAll(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source.getControllerId(), game);
            if (creatureCard.getManaValue() <= landCount) {
                controller.moveCards(creatureCard, Zone.BATTLEFIELD, source, game);
            } else {
                controller.moveCards(creatureCard, Zone.HAND, source, game);
            }
            cards.remove(creatureCard);
        }

        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
