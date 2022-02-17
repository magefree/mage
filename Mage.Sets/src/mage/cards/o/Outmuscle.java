package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Outmuscle extends CardImpl {

    public Outmuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Put a +1/+1 counter on target creature you control, then it fights target creature you don't control.
        // Adamant — If at least three green mana was spent to cast this spell, the creature you control gains indestructible until end of turn.
        this.getSpellAbility().addEffect(new OutmuscleEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private Outmuscle(final Outmuscle card) {
        super(card);
    }

    @Override
    public Outmuscle copy() {
        return new Outmuscle(this);
    }
}

class OutmuscleEffect extends OneShotEffect {

    OutmuscleEffect() {
        super(Outcome.Benefit);
        staticText = "Put a +1/+1 counter on target creature you control, " +
                "then it fights target creature you don't control. " +
                "<i>(Each deals damage equal to its power to the other.)</i> " +
                "<br><i>Adamant</i> &mdash; If at least three green mana was spent to cast this spell, " +
                "the creature you control gains indestructible until end of turn.";
    }

    private OutmuscleEffect(final OutmuscleEffect effect) {
        super(effect);
    }

    @Override
    public OutmuscleEffect copy() {
        return new OutmuscleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (AdamantCondition.GREEN.apply(game, source)) {
            ContinuousEffect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature == null) {
            return true;
        }
        game.getState().processAction(game);
        return creature.fight(permanent, source, game);
    }
}
