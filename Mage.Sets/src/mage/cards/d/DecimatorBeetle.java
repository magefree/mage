package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class DecimatorBeetle extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public DecimatorBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Decimator Beetle enters the battlefield, put a -1/-1 counter on target creature you control.
        Effect effect = new AddCountersTargetEffect(CounterType.M1M1.createInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever Decimator Beetle attacks, remove a -1/-1 counter from target creature you control and put a -1/-1 counter on up to one target creature defending player controls.
        ability = new AttacksTriggeredAbility(new DecimatorBeetleEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private DecimatorBeetle(final DecimatorBeetle card) {
        super(card);
    }

    @Override
    public DecimatorBeetle copy() {
        return new DecimatorBeetle(this);
    }
}

class DecimatorBeetleEffect extends OneShotEffect {

    public DecimatorBeetleEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "remove a -1/-1 counter from target creature you control and put a -1/-1 counter on up to one target creature defending player controls";
    }

    public DecimatorBeetleEffect(DecimatorBeetleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null
                && targetCreature.getCounters(game).containsKey(CounterType.M1M1)) {
            Effect effect = new RemoveCounterTargetEffect(CounterType.M1M1.createInstance(1));
            effect.setTargetPointer(targetPointer);
            effect.apply(game, source);
        }
        targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (targetCreature != null) {
            Effect effect = new AddCountersTargetEffect(CounterType.M1M1.createInstance(1));
            effect.setTargetPointer(new FixedTarget(source.getTargets().get(1).getFirstTarget(), game));
            effect.apply(game, source);
        }

        return true;
    }

    @Override
    public DecimatorBeetleEffect copy() {
        return new DecimatorBeetleEffect(this);
    }
}
