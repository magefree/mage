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
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.ManaSpentToCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Outmuscle extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public Outmuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Put a +1/+1 counter on target creature you control, then it fights target creature you don't control.
        // Adamant — If at least three green mana was spent to cast this spell, the creature you control gains indestructible until end of turn.
        this.getSpellAbility().addEffect(new OutmuscleEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
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
                "then it fights target creature you don't control." +
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
        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature == null) {
            return true;
        }
        game.applyEffects();
        return creature.fight(permanent, source, game);
    }
}