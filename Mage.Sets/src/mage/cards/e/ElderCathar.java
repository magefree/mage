
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author nantuko
 */
public final class ElderCathar extends CardImpl {

    public ElderCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Elder Cathar dies, put a +1/+1 counter on target creature you control. If that creature is a Human, put two +1/+1 counters on it instead.
        Ability ability = new DiesSourceTriggeredAbility(new ElderCatharAddCountersTargetEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ElderCathar(final ElderCathar card) {
        super(card);
    }

    @Override
    public ElderCathar copy() {
        return new ElderCathar(this);
    }
}

class ElderCatharAddCountersTargetEffect extends OneShotEffect {

    private Counter counter;
    private Counter counter2;

    public ElderCatharAddCountersTargetEffect() {
        super(Outcome.Benefit);
        counter = CounterType.P1P1.createInstance(1);
        counter2 = CounterType.P1P1.createInstance(2);
        staticText = "put a +1/+1 counter on target creature you control. If that creature is a Human, put two +1/+1 counters on it instead";
    }

    private ElderCatharAddCountersTargetEffect(final ElderCatharAddCountersTargetEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
        this.counter2 = effect.counter2.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            if (counter != null) {
                if (permanent.hasSubtype(SubType.HUMAN, game)) {
                    permanent.addCounters(counter2.copy(), source.getControllerId(), source, game);
                } else {
                    permanent.addCounters(counter.copy(), source.getControllerId(), source, game);
                }
                return true;
            }
        }
        return false;

    }

    @Override
    public ElderCatharAddCountersTargetEffect copy() {
        return new ElderCatharAddCountersTargetEffect(this);
    }


}
