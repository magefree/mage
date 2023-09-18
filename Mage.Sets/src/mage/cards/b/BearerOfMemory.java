package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BearerOfMemory extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("enchantment creature");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public BearerOfMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {5}{G}: Put a +1/+1 counter on target enchantment creature. It gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{5}{G}")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance()
        ).setText("It gains trample until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BearerOfMemory(final BearerOfMemory card) {
        super(card);
    }

    @Override
    public BearerOfMemory copy() {
        return new BearerOfMemory(this);
    }
}
