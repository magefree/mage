package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class SanctuarySmasher extends CardImpl {

    public SanctuarySmasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Cycling {2}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{R}")));

        // When you cycle Sanctuary Smasher, put a first strike counter on target creature you control.
        Ability ability = new CycleTriggeredAbility(
                new AddCountersTargetEffect(CounterType.FIRST_STRIKE.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SanctuarySmasher(final SanctuarySmasher card) {
        super(card);
    }

    @Override
    public SanctuarySmasher copy() {
        return new SanctuarySmasher(this);
    }
}
