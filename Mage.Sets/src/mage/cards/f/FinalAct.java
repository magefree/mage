package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinalAct extends CardImpl {

    public FinalAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Choose one or more --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(Integer.MAX_VALUE);

        // * Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));

        // * Destroy all planeswalkers.
        this.getSpellAbility().addMode(new Mode(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_PLANESWALKERS)));

        // * Destroy all battles.
        this.getSpellAbility().addMode(new Mode(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_BATTLES)));

        // * Exile all graveyards.
        this.getSpellAbility().addMode(new Mode(new ExileGraveyardAllPlayersEffect()));

        // * Each opponent loses all counters.
        this.getSpellAbility().addMode(new Mode(new FinalActEffect()));
    }

    private FinalAct(final FinalAct card) {
        super(card);
    }

    @Override
    public FinalAct copy() {
        return new FinalAct(this);
    }
}

class FinalActEffect extends OneShotEffect {

    FinalActEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses all counters";
    }

    private FinalActEffect(final FinalActEffect effect) {
        super(effect);
    }

    @Override
    public FinalActEffect copy() {
        return new FinalActEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.loseAllCounters(source, game);
        }
        return true;
    }
}
