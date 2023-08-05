
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DragonToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DescentOfTheDragons extends CardImpl {

    public DescentOfTheDragons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Destroy any number of target creatures.  For each creature destroyed this way, its controller creates a 4/4 red Dragon creature token with flying.
        this.getSpellAbility().addEffect(new DescentOfTheDragonsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));

    }

    private DescentOfTheDragons(final DescentOfTheDragons card) {
        super(card);
    }

    @Override
    public DescentOfTheDragons copy() {
        return new DescentOfTheDragons(this);
    }
}

class DescentOfTheDragonsEffect extends OneShotEffect {

    public DescentOfTheDragonsEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy any number of target creatures. For each creature destroyed this way, its controller creates a 4/4 red Dragon creature token with flying";
    }

    public DescentOfTheDragonsEffect(final DescentOfTheDragonsEffect effect) {
        super(effect);
    }

    @Override
    public DescentOfTheDragonsEffect copy() {
        return new DescentOfTheDragonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> playersWithTargets = new HashMap<>();
            for (Target target : source.getTargets()) {
                for (UUID permanentId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(permanentId);
                    if (permanent != null) {
                        UUID controllerOfTargetId = permanent.getControllerId();
                        if (permanent.destroy(source, game, false)) {
                            int count = playersWithTargets.getOrDefault(controllerOfTargetId, 0);
                            playersWithTargets.put(controllerOfTargetId, count + 1);
                        }
                    }
                }
            }
            game.getState().processAction(game);
            DragonToken dragonToken = new DragonToken();
            for (Map.Entry<UUID, Integer> amountTokensPerPlayer : playersWithTargets.entrySet()) {
                dragonToken.putOntoBattlefield(amountTokensPerPlayer.getValue(), game, source, amountTokensPerPlayer.getKey());
            }
            return true;
        }
        return false;
    }
}
