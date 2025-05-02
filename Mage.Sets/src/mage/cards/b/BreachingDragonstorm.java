package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreachingDragonstorm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "a Dragon");

    public BreachingDragonstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        // When this enchantment enters, exile cards from the top of your library until you exile a nonland card. You may cast it without paying its mana cost if that spell's mana value is 8 or less. If you don't, put that card into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BreachingDragonstormEffect()));

        // When a Dragon you control enters, return this enchantment to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter));
    }

    private BreachingDragonstorm(final BreachingDragonstorm card) {
        super(card);
    }

    @Override
    public BreachingDragonstorm copy() {
        return new BreachingDragonstorm(this);
    }
}

class BreachingDragonstormEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 9));
    }

    BreachingDragonstormEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a nonland card. " +
                "You may cast it without paying its mana cost if that spell's mana value is 8 or less. " +
                "If you don't, put that card into your hand";
    }

    private BreachingDragonstormEffect(final BreachingDragonstormEffect effect) {
        super(effect);
    }

    @Override
    public BreachingDragonstormEffect copy() {
        return new BreachingDragonstormEffect(this);
    }

    private static Card getCard(Player player, Cards cards, Game game, Ability source) {
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            player.moveCards(card, Zone.EXILED, source, game);
            game.processAction();
            if (!card.isLand(game)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card card = getCard(player, cards, game, source);
        if (card != null) {
            CardUtil.castSpellWithAttributesForFree(player, source, game, card, filter);
            if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                player.moveCards(card, Zone.HAND, source, game);
            }
        }
        cards.retainZone(Zone.EXILED, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
