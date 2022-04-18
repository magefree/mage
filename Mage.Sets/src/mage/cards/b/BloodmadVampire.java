
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class BloodmadVampire extends CardImpl {

    public BloodmadVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VAMPIRE, SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Whenever Bloodmad Vampire deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false));

        // Madness {1}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{1}{R}")));
    }

    private BloodmadVampire(final BloodmadVampire card) {
        super(card);
    }

    @Override
    public BloodmadVampire copy() {
        return new BloodmadVampire(this);
    }
}
