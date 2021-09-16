package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.CovenHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class DuelForDominance extends CardImpl {

    public DuelForDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Coven — Choose target creature you control and target creature you don't control.
        // If you control three or more creatures with different powers, put a +1/+1 counter on the chosen creature you control.
        // Then the chosen creatures fight each other.
        this.getSpellAbility().addEffect(new DuelForDominanceEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().setAbilityWord(AbilityWord.COVEN);
        this.getSpellAbility().addHint(CovenHint.instance);
    }

    private DuelForDominance(final DuelForDominance card) {
        super(card);
    }

    @Override
    public DuelForDominance copy() {
        return new DuelForDominance(this);
    }
}

class DuelForDominanceEffect extends OneShotEffect {

    public DuelForDominanceEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target creature you control and target creature you don't control. " +
                "If you control three or more creatures with different powers, put a +1/+1 counter on the chosen creature you control. " +
                "Then the chosen creatures fight each other";
    }

    private DuelForDominanceEffect(final DuelForDominanceEffect effect) {
        super(effect);
    }

    @Override
    public DuelForDominanceEffect copy() {
        return new DuelForDominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent controlledCreature = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (controlledCreature == null) {
            return false;
        }
        if (CovenCondition.instance.apply(game, source)) {
            controlledCreature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        Permanent enemyCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (enemyCreature != null) {
            controlledCreature.fight(enemyCreature, source, game);
        }
        return true;
    }
}
