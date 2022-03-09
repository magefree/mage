package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MuseVortex extends CardImpl {

    public MuseVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Exile the top X cards of your library. You may cast an instant or sorcery spell with mana value X or less from among them without paying its mana cost. Then put the exiled instant and sorcery cards that weren't cast this way into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new MuseVortexEffect());
    }

    private MuseVortex(final MuseVortex card) {
        super(card);
    }

    @Override
    public MuseVortex copy() {
        return new MuseVortex(this);
    }
}

class MuseVortexEffect extends OneShotEffect {

    MuseVortexEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top X cards of your library. You may cast an instant or sorcery spell "
                + "with mana value X or less from among them without paying its mana cost. "
                + "Then put the exiled instant and sorcery cards that weren't cast this way into your hand "
                + "and the rest on the bottom of your library in a random order";
    }

    private MuseVortexEffect(final MuseVortexEffect effect) {
        super(effect);
    }

    @Override
    public MuseVortexEffect copy() {
        return new MuseVortexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = source.getManaCostsToPay().getX();
        if (player == null || xValue < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, xValue));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        FilterCard filter = new FilterInstantOrSorceryCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter);
        cards.retainZone(Zone.EXILED, game);
        player.moveCards(cards.getCards(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game), Zone.HAND, source, game);
        cards.retainZone(Zone.EXILED, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
