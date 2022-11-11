
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox
 */
public final class UrsineFylgja extends CardImpl {

    public UrsineFylgja(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ursine Fylgja enters the battlefield with four healing counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.HEALING.createInstance(4));
        effect.setText("with four healing counters on it.");
        this.addAbility(new EntersBattlefieldAbility(effect));
        // Remove a healing counter from Ursine Fylgja: Prevent the next 1 damage that would be dealt to Ursine Fylgja this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToSourceEffect(Duration.EndOfTurn, 1),
            new RemoveCountersSourceCost(CounterType.HEALING.createInstance(1))));
        // {2}{W}: Put a healing counter on Ursine Fylgja.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.HEALING.createInstance(1)),
            new ManaCostsImpl<>("{2}{W}")));
    }

    private UrsineFylgja(final UrsineFylgja card) {
        super(card);
    }

    @Override
    public UrsineFylgja copy() {
        return new UrsineFylgja(this);
    }
}
