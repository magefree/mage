
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class ChandrasRevolution extends CardImpl {

    public ChandrasRevolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Chandra's Revolution deals 4 damage to target creature. Tap target land. That land doesn't untap during its controller's next untap step.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new ChandrasRevolutionEffect());
    }

    private ChandrasRevolution(final ChandrasRevolution card) {
        super(card);
    }

    @Override
    public ChandrasRevolution copy() {
        return new ChandrasRevolution(this);
    }
}

class ChandrasRevolutionEffect extends OneShotEffect {

    public ChandrasRevolutionEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 4 damage to target creature. Tap target land. That land doesn't untap during its controller's next untap step";
    }

    private ChandrasRevolutionEffect(final ChandrasRevolutionEffect effect) {
        super(effect);
    }

    @Override
    public ChandrasRevolutionEffect copy() {
        return new ChandrasRevolutionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;

        // Chandra's Revolution deals 4 damage to target creature.             
        Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (permanent != null) {
            applied |= permanent.damage(4, source.getSourceId(), source, game, false, true) > 0;
        }

        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            // Tap target land. That land doesn't untap during its controller's next untap step.
            permanent.tap(source, game);
            ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("that land");
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }

        return applied;
    }
}
