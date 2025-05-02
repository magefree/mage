package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.common.ActivatedAbilityManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrypticTrilobite extends CardImpl {

    public CrypticTrilobite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{X}");

        this.subtype.add(SubType.TRILOBITE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Cryptic Trilobite enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // Remove a +1/+1 counter from Cryptic Trilobite: Add {C}{C}. Spend this mana only to activate abilities.        
        this.addAbility(new ConditionalColorlessManaAbility(
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance()),
                2, new ActivatedAbilityManaBuilder(),
                new CountersSourceCount(CounterType.P1P1)
        ));

        // {1}, {T}: Put a +1/+1 counter on Cryptic Trilobite.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CrypticTrilobite(final CrypticTrilobite card) {
        super(card);
    }

    @Override
    public CrypticTrilobite copy() {
        return new CrypticTrilobite(this);
    }
}
