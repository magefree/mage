package mage.cards.t;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThroneOfMakindi extends CardImpl {

    public ThroneOfMakindi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Put a charge counter on Throne of Makindi.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Remove a charge counter from Throne of Makindi: Add two mana of any one color. Spend this mana only to cast kicked spells.
        ability = new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 2, new ThroneOfMakindiManaBuilder(), true
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        this.addAbility(ability);
    }

    private ThroneOfMakindi(final ThroneOfMakindi card) {
        super(card);
    }

    @Override
    public ThroneOfMakindi copy() {
        return new ThroneOfMakindi(this);
    }
}

class ThroneOfMakindiManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ThroneOfMakindiConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast kicked spells";
    }
}

class ThroneOfMakindiConditionalMana extends ConditionalMana {

    ThroneOfMakindiConditionalMana(Mana mana) {
        super(mana);
        addCondition(KickedCondition.ONCE);
    }
}
