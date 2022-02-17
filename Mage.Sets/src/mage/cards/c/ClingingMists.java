package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class ClingingMists extends CardImpl {

    public ClingingMists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Prevent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(null, Duration.EndOfTurn, true));

        // Fateful hour - If you have 5 or less life, tap all attacking creatures. Those creatures don't untap during their controller's next untap step.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ClingingMistsEffect(),
                FatefulHourCondition.instance, "<br><i>Fateful hour</i> &mdash; If you have 5 or less life, " +
                "tap all attacking creatures. Those creatures don't untap during their controller's next untap step."));

    }

    private ClingingMists(final ClingingMists card) {
        super(card);
    }

    @Override
    public ClingingMists copy() {
        return new ClingingMists(this);
    }
}

class ClingingMistsEffect extends OneShotEffect {

    public ClingingMistsEffect() {
        super(Outcome.Tap);
        staticText = "tap all attacking creatures. Those creatures don't untap during their controller's next untap step";
    }

    public ClingingMistsEffect(final ClingingMistsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
        for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_ATTACKING_CREATURES, source.getControllerId(), source.getSourceId(), game)) {
            creature.tap(source, game);
            doNotUntapNextUntapStep.add(creature);
        }
        if (!doNotUntapNextUntapStep.isEmpty()) {
            ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("This creature");
            effect.setTargetPointer(new FixedTargets(doNotUntapNextUntapStep, game));
            game.addEffect(effect, source);
        }
        return true;
    }

    @Override
    public ClingingMistsEffect copy() {
        return new ClingingMistsEffect(this);
    }
}
