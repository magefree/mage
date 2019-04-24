
package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;

/**
 *
 * @author North
 */
public final class KillingWave extends CardImpl {

    public KillingWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // For each creature, its controller sacrifices it unless he or she pays X life.
        this.getSpellAbility().addEffect(new KillingWaveEffect());
    }

    public KillingWave(final KillingWave card) {
        super(card);
    }

    @Override
    public KillingWave copy() {
        return new KillingWave(this);
    }
}

class KillingWaveEffect extends OneShotEffect {

    public KillingWaveEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "For each creature, its controller sacrifices it unless he or she pays X life";
    }

    public KillingWaveEffect(final KillingWaveEffect effect) {
        super(effect);
    }

    @Override
    public KillingWaveEffect copy() {
        return new KillingWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int amount = (new ManacostVariableValue()).calculate(game, source, this);
        if (amount > 0) {
            List<Permanent> sacrifices = new LinkedList<>();
            Map<UUID, Integer> lifePaidAmounts = new HashMap<>();

            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(filter, playerId, game);

                    int lifePaid = 0;
                    int playerLife = player.getLife();
                    for (Permanent creature : creatures) {
                        String message = "Pay " + amount + " life? If you don't, " + creature.getName() + " will be sacrificed.";
                        if (playerLife - amount - lifePaid >= 0 && player.chooseUse(Outcome.Neutral, message, source, game)) {
                            game.informPlayers(player.getLogName() + " pays " + amount + " life. He will not sacrifice " + creature.getName());
                            lifePaid += amount;
                        } else {
                            game.informPlayers(player.getLogName() + " will sacrifice " + creature.getName());
                            sacrifices.add(creature);
                        }
                    }
                    lifePaidAmounts.put(playerId, lifePaid);
                }
            }

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                int lifePaid = lifePaidAmounts.get(playerId);
                if (lifePaid > 0) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.loseLife(lifePaid, game, false);
                    }
                }
            }

            for (Permanent creature : sacrifices) {
                creature.sacrifice(source.getSourceId(), game);
            }
        }
        return true;
    }
}
