
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class MeteorBlast extends CardImpl {

    public MeteorBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}{R}{R}");

        // Meteor Blast deals 4 damage to each of X target creatures and/or players.
        this.getSpellAbility().addEffect(new MeteorBlastEffect());
    }

    public MeteorBlast(final MeteorBlast card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            Target target = new TargetAnyTarget(xValue);
            ability.addTarget(target);
        }
    }

    @Override
    public MeteorBlast copy() {
        return new MeteorBlast(this);
    }
}

class MeteorBlastEffect extends OneShotEffect {

    public MeteorBlastEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 4 damage to each of X target creatures and/or players";
    }

    public MeteorBlastEffect(final MeteorBlastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (!source.getTargets().isEmpty()) {
                for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                    Permanent creature = game.getPermanent(targetId);
                    if (creature != null) {
                        creature.damage(4, source.getSourceId(), game, false, true);
                    } else {
                        Player player = game.getPlayer(targetId);
                        if (player != null) {
                            player.damage(4, source.getSourceId(), game, false, true);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MeteorBlastEffect copy() {
        return new MeteorBlastEffect(this);
    }
}
