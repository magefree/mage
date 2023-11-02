
package mage.cards.s;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author spjspj
 */
public final class SpikeCannibal extends CardImpl {

    public SpikeCannibal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.SPIKE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Spike Cannibal enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), "with a +1/+1 counter on it"));

        // When Spike Cannibal enters the battlefield, move all +1/+1 counters from all creatures onto it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SpikeCannibalEffect()));
    }

    private SpikeCannibal(final SpikeCannibal card) {
        super(card);
    }

    @Override
    public SpikeCannibal copy() {
        return new SpikeCannibal(this);
    }
}

class SpikeCannibalEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures with +1/+1 counter");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    SpikeCannibalEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "move all +1/+1 counters from all creatures onto it";
    }

    private SpikeCannibalEffect(final SpikeCannibalEffect effect) {
        super(effect);
    }

    @Override
    public SpikeCannibalEffect copy() {
        return new SpikeCannibalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int countersRemoved = 0;
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());

        if (sourcePermanent != null) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (!Objects.equals(creature, sourcePermanent)) {
                    int numberCounters = creature.getCounters(game).getCount(CounterType.P1P1);
                    if (numberCounters > 0) {
                        creature.removeCounters(CounterType.P1P1.getName(), numberCounters, source, game);
                        countersRemoved += numberCounters;
                    }
                }
            }

            if (countersRemoved > 0) {
                sourcePermanent.addCounters(CounterType.P1P1.createInstance(countersRemoved), source.getControllerId(), source, game);
                return true;
            }
        }

        return false;
    }
}
