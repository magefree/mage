package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.MoveCounterTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LeechBonder extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a second target creature");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public LeechBonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Leech Bonder enters the battlefield with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)), "with two -1/-1 counters on it"));

        // {U}, {untap}: Move a counter from target creature onto another target creature.
        Ability ability = new SimpleActivatedAbility(new MoveCounterTargetsEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetCreaturePermanent().withChooseHint("to remove a counter from").setTargetTag(1));
        ability.addTarget(new TargetPermanent(filter).withChooseHint("to move a counter to").setTargetTag(2));
        this.addAbility(ability);
    }

    private LeechBonder(final LeechBonder card) {
        super(card);
    }

    @Override
    public LeechBonder copy() {
        return new LeechBonder(this);
    }
}
