package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KazarovSengirPureblood extends CardImpl {

    public KazarovSengirPureblood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature an opponent controls is dealt damage, put a +1/+1 counter on Kazarov, Sengir Pureblood.
        this.addAbility(new DealtDamageAnyTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE, SetTargetPointer.NONE, false));

        // {3}{R}: Kazarov deals 2 damage to target creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new ManaCostsImpl<>("{3}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KazarovSengirPureblood(final KazarovSengirPureblood card) {
        super(card);
    }

    @Override
    public KazarovSengirPureblood copy() {
        return new KazarovSengirPureblood(this);
    }
}
