
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.GreatestPowerControlledPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CracklingDoom extends CardImpl {

    public CracklingDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}{B}");

        // Crackling Doom deals 2 damage to each opponent. Each opponent sacrifices a creature with the greatest power among creatures they control.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(2, TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new CracklingDoomEffect());

    }

    private CracklingDoom(final CracklingDoom card) {
        super(card);
    }

    @Override
    public CracklingDoom copy() {
        return new CracklingDoom(this);
    }
}

class CracklingDoomEffect extends OneShotEffect {

    static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature with the greatest power among creatures you control");
    static {
        filter.add(GreatestPowerControlledPredicate.instance);
    }
    public CracklingDoomEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each opponent sacrifices a creature with the greatest power among creatures that player controls";
    }

    private CracklingDoomEffect(final CracklingDoomEffect effect) {
        super(effect);
    }

    @Override
    public CracklingDoomEffect copy() {
        return new CracklingDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> toSacrifice = new ArrayList<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        Target target = new TargetControlledCreaturePermanent(filter);
                        target.withNotTarget(true);
                        if (opponent.choose(outcome, target, source, game)) {
                            Permanent permanent = game.getPermanent(target.getFirstTarget());
                            if (permanent != null) {
                                toSacrifice.add(permanent);
                            }
                        }
                    }
                }
            }
            for (Permanent permanent : toSacrifice) {
                permanent.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }
}
