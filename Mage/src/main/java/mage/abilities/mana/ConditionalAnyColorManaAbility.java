package mage.abilities.mana;

import java.util.List;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.mana.AddConditionalManaOfAnyColorEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.Zone;
import mage.game.Game;

/**
 * For cards like: {tap}: Add three mana of any one color. Spend this mana only
 * to cast creature spells.
 *
 * @author noxx
 */
public class ConditionalAnyColorManaAbility extends ActivatedManaAbilityImpl {

    private DynamicValue amount;

    public ConditionalAnyColorManaAbility(int amount, ConditionalManaBuilder manaBuilder) {
        this(new TapSourceCost(), amount, manaBuilder);
    }

    public ConditionalAnyColorManaAbility(Cost cost, int amount, ConditionalManaBuilder manaBuilder) {
        this(cost, amount, manaBuilder, false);
    }

    public ConditionalAnyColorManaAbility(Cost cost, int amount, ConditionalManaBuilder manaBuilder, boolean oneChoice) {
        this(cost, StaticValue.get(amount), StaticValue.get(amount), manaBuilder, oneChoice);
    }

    public ConditionalAnyColorManaAbility(Cost cost, DynamicValue amount, DynamicValue netAmount, ConditionalManaBuilder manaBuilder, boolean oneChoice) {
        super(Zone.BATTLEFIELD, new AddConditionalManaOfAnyColorEffect(amount, netAmount, manaBuilder, oneChoice), cost);
        this.amount = amount;
    }

    protected ConditionalAnyColorManaAbility(final ConditionalAnyColorManaAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        this.netMana.clear();
        int count = amount.calculate(game, this, null);
        if (count > 0) {
            this.netMana.add(Mana.AnyMana(count));
        }
        return super.getNetMana(game);
    }

    @Override
    public boolean definesMana(Game game) {
        return true;
    }

    @Override
    public ConditionalAnyColorManaAbility copy() {
        return new ConditionalAnyColorManaAbility(this);
    }
}
