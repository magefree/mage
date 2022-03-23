
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DeadlyTempest extends CardImpl {

    public DeadlyTempest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Destroy all creatures. Each player loses life equal to the number of creatures they controlled that were destroyed this way.
        getSpellAbility().addEffect(new DeadlyTempestEffect());
    }

    private DeadlyTempest(final DeadlyTempest card) {
        super(card);
    }

    @Override
    public DeadlyTempest copy() {
        return new DeadlyTempest(this);
    }
}

class DeadlyTempestEffect extends OneShotEffect {

    public DeadlyTempestEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures. Each player loses life equal to the number of creatures they controlled that were destroyed this way";
    }

    public DeadlyTempestEffect(final DeadlyTempestEffect effect) {
        super(effect);
    }

    @Override
    public DeadlyTempestEffect copy() {
        return new DeadlyTempestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> destroyedCreatures = new HashMap<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                if (permanent.destroy(source, game, false)) {
                    int count = destroyedCreatures.getOrDefault(permanent.getControllerId(), 0);
                    destroyedCreatures.put(permanent.getControllerId(), count + 1);
                }
            }
            for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                int count = destroyedCreatures.getOrDefault(playerId, 0);
                if (count > 0) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.loseLife(count, game, source, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
