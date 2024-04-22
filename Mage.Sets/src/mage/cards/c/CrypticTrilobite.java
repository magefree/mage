package mage.cards.c;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;

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
                2, new CrypticTrilobiteManaBuilder(),
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

class CrypticTrilobiteManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CrypticTrilobiteConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities";
    }
}

class CrypticTrilobiteConditionalMana extends ConditionalMana {

    CrypticTrilobiteConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities";
        addCondition(new CrypticTrilobiteManaCondition());
    }
}

class CrypticTrilobiteManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source != null && !source.isActivated()) {
            // ex: SimpleManaAbility is an ACTIVATED ability, but it is categorized as a MANA ability
            return source.getAbilityType() == AbilityType.MANA
                    || source.getAbilityType() == AbilityType.ACTIVATED;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
