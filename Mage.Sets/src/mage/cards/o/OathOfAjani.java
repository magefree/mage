
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPlaneswalkerCard;

/**
 * @author JRHerlehy
 */
public final class OathOfAjani extends CardImpl {

    public OathOfAjani(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Oath of Ajani enters the battlefield, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent())));

        // Planeswalker spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(new FilterPlaneswalkerCard("Planeswalker spells"), 1)));
    }

    private OathOfAjani(final OathOfAjani card) {
        super(card);
    }

    @Override
    public OathOfAjani copy() {
        return new OathOfAjani(this);
    }
}
