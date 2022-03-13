
package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author LevelX2
 */
public final class Tangle extends CardImpl {

    public Tangle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Prevent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        // Each attacking creature doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new TangleEffect());
    }

    private Tangle(final Tangle card) {
        super(card);
    }

    @Override
    public Tangle copy() {
        return new Tangle(this);
    }

}

class TangleEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public TangleEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each attacking creature doesn't untap during its controller's next untap step";
    }

    public TangleEffect(final TangleEffect effect) {
        super(effect);
    }

    @Override
    public TangleEffect copy() {
        return new TangleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                doNotUntapNextUntapStep.add(permanent);
            }
            if (!doNotUntapNextUntapStep.isEmpty()) {
                ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("This creature");
                effect.setTargetPointer(new FixedTargets(doNotUntapNextUntapStep, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
