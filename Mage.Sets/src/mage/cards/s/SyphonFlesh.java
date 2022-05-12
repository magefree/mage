
package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class SyphonFlesh extends CardImpl {

    public SyphonFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Each other player sacrifices a creature. You create a 2/2 black Zombie creature token for each creature sacrificed this way.
        this.getSpellAbility().addEffect(new SyphonFleshEffect());
    }

    private SyphonFlesh(final SyphonFlesh card) {
        super(card);
    }

    @Override
    public SyphonFlesh copy() {
        return new SyphonFlesh(this);
    }
}

class SyphonFleshEffect extends OneShotEffect {

    public SyphonFleshEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each other player sacrifices a creature. You create a 2/2 black Zombie creature token for each creature sacrificed this way.";
    }

    public SyphonFleshEffect(final SyphonFleshEffect effect) {
        super(effect);
    }

    @Override
    public SyphonFleshEffect copy() {
        return new SyphonFleshEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {            
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && !playerId.equals(source.getControllerId())) {
                    TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                    target.setNotTarget(true);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
            }
            for (UUID permID : perms) {
                Permanent permanent = game.getPermanent(permID);
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                }
            }
            
            int sacrificedAmount = perms.isEmpty() ? 0 : perms.size();
            if (sacrificedAmount > 0) {
                ZombieToken token = new ZombieToken();
                token.putOntoBattlefield(sacrificedAmount, game, source, source.getControllerId());
            }
            return true;
        }
        return false;        
    }
}