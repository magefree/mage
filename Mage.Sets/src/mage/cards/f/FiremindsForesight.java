package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FiremindsForesight extends CardImpl {

    public FiremindsForesight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}{R}");

        // Search your library for an instant card with converted mana cost 3, reveal it,
        // and put it into your hand. Then repeat this process for instant cards with
        // converted mana costs 2 and 1. Then shuffle your library.
        this.getSpellAbility().addEffect(new FiremindsForesightSearchEffect());
    }

    private FiremindsForesight(final FiremindsForesight card) {
        super(card);
    }

    @Override
    public FiremindsForesight copy() {
        return new FiremindsForesight(this);
    }
}

class FiremindsForesightSearchEffect extends OneShotEffect {

    private static final Map<Integer, FilterCard> filterMap = new HashMap<>();

    static {
        for (int cmc = 3; cmc > 0; cmc--) {
            FilterCard filter = new FilterCard("instant card with mana value " + cmc);
            filter.add(CardType.INSTANT.getPredicate());
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, cmc));
            filterMap.put(cmc, filter);
        }
    }

    FiremindsForesightSearchEffect() {
        super(Outcome.DrawCard);
        staticText = "Search your library for an instant card with mana value 3, " +
                "reveal it, and put it into your hand. Then repeat this process " +
                "for instant cards with mana values 2 and 1. Then shuffle";
    }

    private FiremindsForesightSearchEffect(final FiremindsForesightSearchEffect effect) {
        super(effect);
    }

    @Override
    public FiremindsForesightSearchEffect copy() {
        return new FiremindsForesightSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cardToReveal = new CardsImpl();
        for (int cmc = 3; cmc > 0; cmc--) {
            TargetCardInLibrary target = new TargetCardInLibrary(filterMap.get(cmc));
            player.searchLibrary(target, source, game);
            Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
            if (card == null) {
                continue;
            }
            cardToReveal.clear();
            cardToReveal.add(card);
            player.revealCards(source, cardToReveal, game);
            player.moveCards(cardToReveal, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
