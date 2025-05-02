package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
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
public final class TreetopSnarespinner extends CardImpl {

    public TreetopSnarespinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {2}{G}: Put a +1/+1 counter on target creature you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{2}{G}")
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private TreetopSnarespinner(final TreetopSnarespinner card) {
        super(card);
    }

    @Override
    public TreetopSnarespinner copy() {
        return new TreetopSnarespinner(this);
    }
}
