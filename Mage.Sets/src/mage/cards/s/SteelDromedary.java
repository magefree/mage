package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelDromedary extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1);

    public SteelDromedary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Steel Dromedary enters the battlefield tapped with two +1/+1 counters on it.
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(), "tapped with two +1/+1 counters on it"
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
        this.addAbility(ability);

        // Steel Dromedary doesn't untap during your untap step if it has a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersUntapStepSourceEffect(), condition
        ).setText("{this} doesn't untap during your untap step if it has a +1/+1 counter on it")));

        // At the beginning of combat on your turn, you may move a +1/+1 counter from Steel Dromedary onto target creature.
        ability = new BeginningOfCombatTriggeredAbility(
                new SteelDromedaryEffect(), TargetController.YOU, true
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SteelDromedary(final SteelDromedary card) {
        super(card);
    }

    @Override
    public SteelDromedary copy() {
        return new SteelDromedary(this);
    }
}

class SteelDromedaryEffect extends OneShotEffect {

    SteelDromedaryEffect() {
        super(Outcome.Benefit);
        staticText = "you may move a +1/+1 counter from {this} onto target creature";
    }

    private SteelDromedaryEffect(final SteelDromedaryEffect effect) {
        super(effect);
    }

    @Override
    public SteelDromedaryEffect copy() {
        return new SteelDromedaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (permanent != null
                && creature != null
                && permanent.getCounters(game).getCount(CounterType.P1P1) > 0
                && creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game)) {
            permanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
            return true;
        }
        return false;
    }
}
