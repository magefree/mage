
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class MirriTheCursed extends CardImpl {

    public MirriTheCursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Mirri the Cursed deals combat damage to a creature, put a +1/+1 counter on Mirri the Cursed.
        Ability ability;
        ability = new DealsDamageToACreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), true, false, true);
        this.addAbility(ability);
    }

    private MirriTheCursed(final MirriTheCursed card) {
        super(card);
    }

    @Override
    public MirriTheCursed copy() {
        return new MirriTheCursed(this);
    }
}
