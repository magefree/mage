
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class EnclaveElite extends CardImpl {

    public EnclaveElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Multikicker (You may pay an additional any number of times as you cast this spell.)
        this.addAbility(new MultikickerAbility("{1}{U}"));

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Enclave Elite enters the battlefield with a +1/+1 counter on it for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), MultikickerCount.instance, true),
                "with a +1/+1 counter on it for each time it was kicked"));
    }

    private EnclaveElite(final EnclaveElite card) {
        super(card);
    }

    @Override
    public EnclaveElite copy() {
        return new EnclaveElite(this);
    }
}
