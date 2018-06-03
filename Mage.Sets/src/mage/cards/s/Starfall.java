
package mage.cards.s;

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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Starfall extends CardImpl {

    public Starfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");


        // Starfall deals 3 damage to target creature. If that creature is an enchantment, Starfall deals 3 damage to that creature's controller.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new StarfallEffect());

    }

    public Starfall(final Starfall card) {
        super(card);
    }

    @Override
    public Starfall copy() {
        return new Starfall(this);
    }
}

class StarfallEffect extends OneShotEffect {

    public StarfallEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to target creature. If that creature is an enchantment, {this} deals 3 damage to that creature's controller";
    }

    public StarfallEffect(final StarfallEffect effect) {
        super(effect);
    }

    @Override
    public StarfallEffect copy() {
        return new StarfallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.damage(3, source.getSourceId(), game, false, true);
            if (permanent.isEnchantment()) {
                Player targetController = game.getPlayer(permanent.getControllerId());
                if (targetController != null) {
                    targetController.damage(3, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

}
