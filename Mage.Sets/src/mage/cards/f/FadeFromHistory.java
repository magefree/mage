package mage.cards.f;

import java.util.HashSet;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BearToken;

/**
 *
 * @author weirddan455
 */
public final class FadeFromHistory extends CardImpl {

    public FadeFromHistory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Each player who controls an artifact or enchantment creates a 2/2 green Bear creature token. Then destroy all artifacts and enchantments.
        this.getSpellAbility().addEffect(new FadeFromHistoryTokenEffect());
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT)
                .setText("Then destroy all artifacts and enchantments."));
    }

    private FadeFromHistory(final FadeFromHistory card) {
        super(card);
    }

    @Override
    public FadeFromHistory copy() {
        return new FadeFromHistory(this);
    }
}

class FadeFromHistoryTokenEffect extends OneShotEffect {

    public FadeFromHistoryTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Each player who controls an artifact or enchantment creates a 2/2 green Bear creature token.";
    }

    private FadeFromHistoryTokenEffect(final FadeFromHistoryTokenEffect effect) {
        super(effect);
    }

    @Override
    public FadeFromHistoryTokenEffect copy() {
        return new FadeFromHistoryTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        HashSet<UUID> playerIds = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT, source.getControllerId(), source, game)) {
            playerIds.add(permanent.getControllerId());
        }
        for (UUID playerId : playerIds) {
            new BearToken().putOntoBattlefield(1, game, source, playerId);
        }
        return true;
    }
}
