package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.AddManaOfTwoDifferentColorsEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ComponentPouch extends CardImpl {

    public ComponentPouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}, Remove a component counter from Component Pouch: Add two mana of different colors.
        Ability ability = new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfTwoDifferentColorsEffect(), new TapSourceCost()
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.COMPONENT.createInstance()));
        this.addAbility(ability);

        // {T}: Roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new SimpleActivatedAbility(effect, new TapSourceCost()));

        // 1-9 | Put a component counter on Component Pouch.
        effect.addTableEntry(1, 9, new AddCountersSourceEffect(CounterType.COMPONENT.createInstance()));

        // 10-20 | Put two component counters on Component Pouch.
        effect.addTableEntry(10, 20, new AddCountersSourceEffect(CounterType.COMPONENT.createInstance(2)));
    }

    private ComponentPouch(final ComponentPouch card) {
        super(card);
    }

    @Override
    public ComponentPouch copy() {
        return new ComponentPouch(this);
    }
}
