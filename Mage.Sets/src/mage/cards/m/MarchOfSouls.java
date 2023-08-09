
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MarchOfSouls extends CardImpl {

    public MarchOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Destroy all creatures. They can't be regenerated. For each creature destroyed this way, its controller creates a 1/1 white Spirit creature token with flying.
        this.getSpellAbility().addEffect(new MarchOfSoulsEffect());
    }

    private MarchOfSouls(final MarchOfSouls card) {
        super(card);
    }

    @Override
    public MarchOfSouls copy() {
        return new MarchOfSouls(this);
    }
}

class MarchOfSoulsEffect extends OneShotEffect {

    public MarchOfSoulsEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy all creatures. They can't be regenerated. For each creature destroyed this way, its controller creates a 1/1 white Spirit creature token with flying.";
    }

    public MarchOfSoulsEffect(final MarchOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfSoulsEffect copy() {
        return new MarchOfSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> playersWithCreatures = new HashMap<>();
        for (Permanent p : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURES,
                source.getControllerId(), source, game
        )) {
            UUID controllerId = p.getControllerId();
            if (p.destroy(source, game, true)) {
                playersWithCreatures.put(controllerId, playersWithCreatures.getOrDefault(controllerId, 0) + 1);
            }
        }
        game.getState().processAction(game);
        SpiritWhiteToken token = new SpiritWhiteToken();
        for (Map.Entry<UUID, Integer> destroyedCreaturePerPlayer : playersWithCreatures.entrySet()) {
            token.putOntoBattlefield(destroyedCreaturePerPlayer.getValue(), game, source, destroyedCreaturePerPlayer.getKey());
        }
        return true;
    }
}
