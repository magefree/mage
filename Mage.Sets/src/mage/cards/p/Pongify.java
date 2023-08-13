
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ApeToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author dustinconrad
 */
public final class Pongify extends CardImpl {

    public Pongify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Destroy target creature. It can't be regenerated. That creature's controller creates a 3/3 green Ape creature token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addEffect(new PongifyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Pongify(final Pongify card) {
        super(card);
    }

    @Override
    public Pongify copy() {
        return new Pongify(this);
    }
}

class PongifyEffect extends OneShotEffect {

    public PongifyEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Its controller creates a 3/3 green Ape creature token";
    }

    public PongifyEffect(final PongifyEffect effect) {
        super(effect);
    }

    @Override
    public PongifyEffect copy() {
        return new PongifyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId != null) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
            if (permanent != null) {
                UUID controllerId = permanent.getControllerId();
                if (controllerId != null) {
                    new ApeToken().putOntoBattlefield(1, game, source, controllerId);
                    return true;
                }
            }
        }
        return false;
    }
}
