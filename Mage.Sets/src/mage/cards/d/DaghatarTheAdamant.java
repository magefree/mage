
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.MoveCounterTargetsEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DaghatarTheAdamant extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a second target creature");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public DaghatarTheAdamant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Daghatar the Adamant enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)),
                "with four +1/+1 counters on it"));

        // {1}{B/G}{B/G}: Move a +1/+1 counter from target creature onto a second target creature.
        Ability ability = new SimpleActivatedAbility(
                new MoveCounterTargetsEffect(CounterType.P1P1), new ManaCostsImpl<>("{1}{B/G}{B/G}")
        );
        ability.addTarget(new TargetCreaturePermanent().withChooseHint("to remove a counter from").setTargetTag(1));
        ability.addTarget(new TargetPermanent(filter).withChooseHint("to move a counter to").setTargetTag(2));
        this.addAbility(ability);
    }

    private DaghatarTheAdamant(final DaghatarTheAdamant card) {
        super(card);
    }

    @Override
    public DaghatarTheAdamant copy() {
        return new DaghatarTheAdamant(this);
    }
}
