package mage.cards.o;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
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
public final class OverwriteTheMultiverse extends CardImpl {

    public OverwriteTheMultiverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Exile all creatures. Empower Jace X, where X is the number of creatures exiled this way.
        this.getSpellAbility().addEffect(new OverwriteTheMultiverseEffect());
    }

    private OverwriteTheMultiverse(final OverwriteTheMultiverse card) {
        super(card);
    }

    @Override
    public OverwriteTheMultiverse copy() {
        return new OverwriteTheMultiverse(this);
    }
}


class OverwriteTheMultiverseEffect extends OneShotEffect {

    OverwriteTheMultiverseEffect() {
        super(Outcome.Benefit);
        staticText = "exile all creatures. Empower Jace X, where X is the number of creatures exiled this way";
    }

    private OverwriteTheMultiverseEffect(final OverwriteTheMultiverseEffect effect) {
        super(effect);
    }

    @Override
    public OverwriteTheMultiverseEffect copy() {
        return new OverwriteTheMultiverseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards creatures = new CardsImpl(game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game
        ));
        player.moveCards(creatures, Zone.EXILED, source, game);
        new EmpowerJaceEffect(creatures.size()).apply(game, source);
        return true;
    }
}
