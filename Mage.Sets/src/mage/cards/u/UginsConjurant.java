package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.PreventDamageAndRemoveCountersEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.counters.CounterType;
import mage.constants.Zone;

/**
 *
 * @author antoni-g
 */
public final class UginsConjurant extends CardImpl {

    public UginsConjurant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}");
        
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ugin’s Conjurant enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));
        // If damage would be dealt to Ugin’s Conjurant while it has a +1/+1 counter on it, prevent that damage and remove that many +1/+1 counters from Ugin’s Conjurant.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageAndRemoveCountersEffect(true, true, false)));
    }

    private UginsConjurant(final UginsConjurant card) {
        super(card);
    }

    @Override
    public UginsConjurant copy() {
        return new UginsConjurant(this);
    }
}
