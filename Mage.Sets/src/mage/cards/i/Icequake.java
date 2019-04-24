
package mage.cards.i;

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
import mage.target.common.TargetLandPermanent;


/**
 *
 * @author fireshoes
 */
public final class Icequake extends CardImpl {

    public Icequake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Destroy target land.
        // If that land was a snow land, Icequake deals 1 damage to that land's controller.
        this.getSpellAbility().addEffect(new IcequakeEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        
        
    }

    public Icequake(final Icequake card) {
        super(card);
    }

    @Override
    public Icequake copy() {
        return new Icequake(this);
    }
}

class IcequakeEffect extends OneShotEffect {

    public IcequakeEffect() {
        super(Outcome.Damage);
        this.staticText = "Destroy target land.<br>If that land was a snow land, {this} deals 1 damage to that land's controller.";
    }

    public IcequakeEffect(final IcequakeEffect effect) {
        super(effect);
    }

    @Override
    public IcequakeEffect copy() {
        return new IcequakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && controller != null) {
            permanent.destroy(source.getSourceId(), game, false);
            if (permanent.isSnow()) {
                controller.damage(1, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
