
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CounterRemovedFromSourceWhileExiledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FungalBehemoth extends CardImpl {

    public FungalBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.FUNGUS);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Fungal Behemoth's power and toughness are each equal to the number of +1/+1 counters on creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new P1P1CountersOnControlledCreaturesCount())));

        // Suspend X-{X}{G}{G}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl<>("{G}{G}"), this, true));

        // Whenever a time counter is removed from Fungal Behemoth while it's exiled, you may put a +1/+1 counter on target creature.
        Ability ability = new CounterRemovedFromSourceWhileExiledTriggeredAbility(
                CounterType.TIME,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                true
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FungalBehemoth(final FungalBehemoth card) {
        super(card);
    }

    @Override
    public FungalBehemoth copy() {
        return new FungalBehemoth(this);
    }
}

class P1P1CountersOnControlledCreaturesCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceAbility.getControllerId(), game)) {
            count += permanent.getCounters(game).getCount(CounterType.P1P1);
        }
        return count;
    }

    @Override
    public P1P1CountersOnControlledCreaturesCount copy() {
        return new P1P1CountersOnControlledCreaturesCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "+1/+1 counters on creatures you control";
    }
}
