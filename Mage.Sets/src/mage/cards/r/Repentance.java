
package mage.cards.r;

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
 * @author fireshoes
 */
public final class Repentance extends CardImpl {

    public Repentance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Target creature deals damage to itself equal to its power.
        this.getSpellAbility().addEffect(new RepentanceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Repentance(final Repentance card) {
        super(card);
    }

    @Override
    public Repentance copy() {
        return new Repentance(this);
    }
}

class RepentanceEffect extends OneShotEffect {

    public RepentanceEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature deals damage to itself equal to its power";
    }

    private RepentanceEffect(final RepentanceEffect effect) {
        super(effect);
    }

    @Override
    public RepentanceEffect copy() {
        return new RepentanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                targetCreature.damage(targetCreature.getPower().getValue(), source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}