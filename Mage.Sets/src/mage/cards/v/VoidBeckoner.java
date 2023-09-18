package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoidBeckoner extends CardImpl {

    public VoidBeckoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Cycling {2}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{B}")));

        // When you cycle Void Beckoner, put a deathtouch counter on target creature you control.
        Ability ability = new CycleTriggeredAbility(
                new AddCountersTargetEffect(CounterType.DEATHTOUCH.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private VoidBeckoner(final VoidBeckoner card) {
        super(card);
    }

    @Override
    public VoidBeckoner copy() {
        return new VoidBeckoner(this);
    }
}
