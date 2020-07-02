package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author L_J
 */
public final class FogPatch extends CardImpl {

    public FogPatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Cast Fog Patch only during the declare blockers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(
                null, PhaseStep.DECLARE_BLOCKERS, null,
                "Cast this spell only during the declare blockers step"
        ));

        // Attacking creatures become blocked.
        this.getSpellAbility().addEffect(new FogPatchEffect());
    }

    private FogPatch(final FogPatch card) {
        super(card);
    }

    @Override
    public FogPatch copy() {
        return new FogPatch(this);
    }
}

class FogPatchEffect extends OneShotEffect {

    FogPatchEffect() {
        super(Outcome.Benefit);
        this.staticText = "Attacking creatures become blocked";
    }

    private FogPatchEffect(final FogPatchEffect effect) {
        super(effect);
    }

    @Override
    public FogPatchEffect copy() {
        return new FogPatchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Effect effect = new BecomeBlockedTargetEffect();
        effect.setTargetPointer(new FixedTargets(game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_ATTACKING_CREATURES, source.getSourceId(), game
        ), game));
        return effect.apply(game, source);
    }
}
