
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class Karstoderm extends CardImpl {

    public Karstoderm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Karstoderm enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
            "with five +1/+1 counters on it"));
        
        // Whenever an artifact enters the battlefield, remove a +1/+1 counter from Karstoderm.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, 
                new RemoveCounterSourceEffect(CounterType.P1P1.createInstance(1)),
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN, false));
    }

    private Karstoderm(final Karstoderm card) {
        super(card);
    }

    @Override
    public Karstoderm copy() {
        return new Karstoderm(this);
    }
}
