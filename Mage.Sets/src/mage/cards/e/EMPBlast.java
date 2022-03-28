
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Styxo
 */
public final class EMPBlast extends CardImpl {

    public EMPBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Exile target artifact.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // Tap all other artifacts.
        this.getSpellAbility().addEffect(new EMPBlastEffect());
    }

    private EMPBlast(final EMPBlast card) {
        super(card);
    }

    @Override
    public EMPBlast copy() {
        return new EMPBlast(this);
    }
}

class EMPBlastEffect extends OneShotEffect {

    public EMPBlastEffect() {
        super(Outcome.Tap);
        this.staticText = "Tap all other artifacts";
    }

    public EMPBlastEffect(final EMPBlastEffect effect) {
        super(effect);
    }

    @Override
    public EMPBlastEffect copy() {
        return new EMPBlastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent artifact : game.getBattlefield().getActivePermanents(new FilterArtifactPermanent(), source.getControllerId(), source, game)) {
            artifact.tap(source, game);
        }
        return true;
    }
}
