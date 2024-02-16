package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class Depopulate extends CardImpl {

    public Depopulate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Each player who controls a multicolored creature draws a card. Then destroy all creatures.
        this.getSpellAbility().addEffect(new DepopulateEffect());
        this.getSpellAbility().addEffect(new DestroyAllEffect(
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).concatBy("Then"));
    }

    private Depopulate(final Depopulate card) {
        super(card);
    }

    @Override
    public Depopulate copy() {
        return new Depopulate(this);
    }
}

class DepopulateEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    DepopulateEffect() {
        super(Outcome.Benefit);
        staticText = "each player who controls a multicolored creature draws a card";
    }

    private DepopulateEffect(final DepopulateEffect effect) {
        super(effect);
    }

    @Override
    public DepopulateEffect copy() {
        return new DepopulateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> players = game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), game)
                .stream()
                .map(Controllable::getControllerId)
                .collect(Collectors.toSet());
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (!players.contains(playerId)) {
                continue;
            }
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(1, source, game);
            }
        }
        return true;
    }
}
