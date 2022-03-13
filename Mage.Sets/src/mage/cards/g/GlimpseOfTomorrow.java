package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlimpseOfTomorrow extends CardImpl {

    public GlimpseOfTomorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setRed(true);

        // Suspend 3—{R}{R}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{R}{R}"), this));

        // Shuffle all permanents you own into your library, then reveal that many cards from the top of your library. Put all non-Aura permanent cards revealed this way onto the battlefield, then do the same for Aura cards, then put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new GlimpseOfTomorrowEffect());
    }

    private GlimpseOfTomorrow(final GlimpseOfTomorrow card) {
        super(card);
    }

    @Override
    public GlimpseOfTomorrow copy() {
        return new GlimpseOfTomorrow(this);
    }
}

class GlimpseOfTomorrowEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    GlimpseOfTomorrowEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle all permanents you own into your library, then reveal " +
                "that many cards from the top of your library. Put all non-Aura permanent cards " +
                "revealed this way onto the battlefield, then do the same for Aura cards, " +
                "then put the rest on the bottom of your library in a random order";
    }

    private GlimpseOfTomorrowEffect(final GlimpseOfTomorrowEffect effect) {
        super(effect);
    }

    @Override
    public GlimpseOfTomorrowEffect copy() {
        return new GlimpseOfTomorrowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        );
        int count = permanents.size();
        player.shuffleCardsToLibrary(new CardsImpl(permanents), game, source);

        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, count));
        player.revealCards(source, cards, game);

        Cards toBattlefield = new CardsImpl(cards.getCards(StaticFilters.FILTER_CARD_PERMANENT, game));
        toBattlefield.removeIf(uuid -> game.getCard(uuid).hasSubtype(SubType.AURA, game));
        player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
        toBattlefield.clear();
        cards.retainZone(Zone.LIBRARY, game);

        toBattlefield.addAll(cards.getCards(StaticFilters.FILTER_CARD_PERMANENT, game));
        toBattlefield.removeIf(uuid -> !game.getCard(uuid).hasSubtype(SubType.AURA, game));
        player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
