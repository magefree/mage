
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreatureExploresTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class ShadowedCaravel extends CardImpl {

    public ShadowedCaravel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature you control explores, put a +1/+1 counter on Shadowed Caravel.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance());
        Ability ability = new CreatureExploresTriggeredAbility(effect);
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private ShadowedCaravel(final ShadowedCaravel card) {
        super(card);
    }

    @Override
    public ShadowedCaravel copy() {
        return new ShadowedCaravel(this);
    }
}
