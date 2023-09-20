
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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class InnerStruggle extends CardImpl {

    public InnerStruggle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");

        // Target creature deals damage to itself equal to its power.
        this.getSpellAbility().addEffect(new InnerStruggleEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private InnerStruggle(final InnerStruggle card) {
        super(card);
    }

    @Override
    public InnerStruggle copy() {
        return new InnerStruggle(this);
    }
}

class InnerStruggleEffect extends OneShotEffect {

    public InnerStruggleEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature deals damage to itself equal to its power";
    }

    private InnerStruggleEffect(final InnerStruggleEffect effect) {
        super(effect);
    }

    @Override
    public InnerStruggleEffect copy() {
        return new InnerStruggleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                targetCreature.damage(targetCreature.getPower().getValue(), targetCreature.getId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}
