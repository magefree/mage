package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Cards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdventureAwaits extends CardImpl {

    public AdventureAwaits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order. If you don't put a card into your hand this way, draw a card.
        this.getSpellAbility().addEffect(new AdventureAwaitsEffect());
    }

    private AdventureAwaits(final AdventureAwaits card) {
        super(card);
    }

    @Override
    public AdventureAwaits copy() {
        return new AdventureAwaits(this);
    }
}

class AdventureAwaitsEffect extends LookLibraryAndPickControllerEffect {

    AdventureAwaitsEffect() {
        super(5, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM);
    }

    private AdventureAwaitsEffect(final AdventureAwaitsEffect effect) {
        super(effect);
    }

    @Override
    public AdventureAwaitsEffect copy() {
        return new AdventureAwaitsEffect(this);
    }

    @Override
    public boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        super.actionWithPickedCards(game, source, player, pickedCards, otherCards);
        if (pickedCards.isEmpty()) {
            player.drawCards(1, source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode).concat(". If you didn't put a card into your hand this way, draw a card");
    }
}
