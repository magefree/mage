package mage.cards.e;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class EmpoweredAutogenerator extends CardImpl {

    public EmpoweredAutogenerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Empowered Autogenerator enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Put a charge counter on Empowered Autogenerator. Add X mana of any one color, where X is the number of charge counters on Empowered Autogenerator.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true), new TapSourceCost());
        ability.addEffect(new DynamicManaEffect(new Mana(0, 0, 0, 0, 0, 0, 1, 0),
                new CountersSourceCount(CounterType.CHARGE), "Add X mana of any one color, where X is the number of charge counters on {this}", true));
        this.addAbility(ability);
    }

    public EmpoweredAutogenerator(final EmpoweredAutogenerator card) {
        super(card);
    }

    @Override
    public EmpoweredAutogenerator copy() {
        return new EmpoweredAutogenerator(this);
    }
}
