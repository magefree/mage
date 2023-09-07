
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.ManifestTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class RealityShift extends CardImpl {

    public RealityShift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Exile target creature. Its controller manifests the top card of their library.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new RealityShiftEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private RealityShift(final RealityShift card) {
        super(card);
    }

    @Override
    public RealityShift copy() {
        return new RealityShift(this);
    }
}

class RealityShiftEffect extends OneShotEffect {

    public RealityShiftEffect() {
        super(Outcome.Exile);
        this.staticText = "Its controller manifests the top card of their library. <i>(That player puts the top card of their library onto the battlefield face down as a 2/2 creature. If it's a creature card, it can be turned face up any time for its mana cost.)</i>";
    }

    private RealityShiftEffect(final RealityShiftEffect effect) {
        super(effect);
    }

    @Override
    public RealityShiftEffect copy() {
        return new RealityShiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (targetCreature != null) {
            Effect effect = new ManifestTargetPlayerEffect(1, "Its controller");
            effect.setTargetPointer(new FixedTarget(targetCreature.getControllerId()));
            return effect.apply(game, source);
        }
        return false;
    }
}
