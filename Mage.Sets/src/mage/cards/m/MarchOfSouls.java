
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author LoneFox

 */
public final class MarchOfSouls extends CardImpl {

    public MarchOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}");

        // Destroy all creatures. They can't be regenerated. For each creature destroyed this way, its controller creates a 1/1 white Spirit creature token with flying.
        this.getSpellAbility().addEffect(new MarchOfSoulsEffect());
    }

    public MarchOfSouls(final MarchOfSouls card) {
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
        List<Permanent> creatures = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES,
            source.getControllerId(), source.getSourceId(), game);
        Map<UUID, Integer> playersWithCreatures = new HashMap<>();
        for(Permanent p : creatures) {
            UUID controllerId = p.getControllerId();
            if(p.destroy(source.getSourceId(), game, true)) {
                if(playersWithCreatures.containsKey(controllerId)) {
                    playersWithCreatures.put(controllerId, playersWithCreatures.get(controllerId) + 1);
                }
                else {
                    playersWithCreatures.put(controllerId, 1);
                }
            }
        }
        SpiritWhiteToken token = new SpiritWhiteToken();
        for(UUID playerId : playersWithCreatures.keySet()) {
            token.putOntoBattlefield(playersWithCreatures.get(playerId), game, source.getSourceId(), playerId);
        }
        return true;
    }
}
