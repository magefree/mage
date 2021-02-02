
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class ArcboundSlith extends CardImpl {

    public ArcboundSlith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.SLITH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Whenever Arcbound Slith deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true), false));

        // Modular 1
        this.addAbility(new ModularAbility(this, 1));
    }

    private ArcboundSlith(final ArcboundSlith card) {
        super(card);
    }

    @Override
    public ArcboundSlith copy() {
        return new ArcboundSlith(this);
    }
}
