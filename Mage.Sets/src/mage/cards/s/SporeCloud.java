package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author L_J
 */
public final class SporeCloud extends CardImpl {

    public SporeCloud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{G}");

        // Tap all blocking creatures.
        this.getSpellAbility().addEffect(new TapAllEffect(StaticFilters.FILTER_BLOCKING_CREATURES));
        // Prevent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        // Each attacking creature and each blocking creature doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new SporeCloudEffect());
    }

    private SporeCloud(final SporeCloud card) {
        super(card);
    }

    @Override
    public SporeCloud copy() {
        return new SporeCloud(this);
    }
}

class SporeCloudEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each attacking creature and each blocking creature");
    static {
        filter.add(Predicates.or(AttackingPredicate.instance, BlockingPredicate.instance));
    }

    public SporeCloudEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each attacking creature and each blocking creature doesn't untap during its controller's next untap step";
    }

    public SporeCloudEffect(final SporeCloudEffect effect) {
        super(effect);
    }

    @Override
    public SporeCloudEffect copy() {
        return new SporeCloudEffect(this);
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
