package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class DiscoverEffect extends OneShotEffect {

    private final int amount;

    public DiscoverEffect(int amount) {
        this(amount, true);
    }

    public DiscoverEffect(int amount, boolean withReminderText) {
        super(Outcome.Benefit);
        this.amount = amount;
        staticText = "discover " + amount
                + (withReminderText ? ". <i>(Exile cards from the top of your library " +
                "until you exile a nonland card with mana value " + amount + " or less. " +
                "Cast it without paying its mana cost or put it into your hand. " +
                "Put the rest on the bottom in a random order.)</i>" : "");
    }

    private DiscoverEffect(final DiscoverEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DiscoverEffect copy() {
        return new DiscoverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        doDiscover(player, amount, game, source);
        return true;
    }

    private static FilterCard makeDiscoverFilter(int amount) {
        FilterCard filter = new FilterNonlandCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, amount + 1));
        return filter;
    }

    private static Card getCard(Player player, FilterCard filter, Cards cards, Game game, Ability source) {
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            player.moveCards(card, Zone.EXILED, source, game);
            game.getState().processAction(game);
            if (filter.match(card, game)) {
                return card;
            }
        }
        return null;
    }

    public static Card doDiscover(Player player, int amount, Game game, Ability source) {
        Cards cards = new CardsImpl();
        FilterCard filter = makeDiscoverFilter(amount);
        Card card = getCard(player, filter, cards, game, source);
        if (card != null) {
            CardUtil.castSpellWithAttributesForFree(player, source, game, card, filter);
            if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                player.moveCards(card, Zone.HAND, source, game);
            }
        }
        cards.retainZone(Zone.EXILED, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DISCOVERED, null, source, player.getId(), amount));
        return card;
    }
}
