package mage.cards.c;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class CrucibleOfTheSpiritDragon extends CardImpl {

    public CrucibleOfTheSpiritDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Put a storage counter on Crucible of the Spirit Dragon.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Remove X storage counters from Crucible of the Spirit Dragon: Add X mana in any combination of colors. Spend this mana only to cast Dragon spells or activate abilities of Dragons.
        ability = new ConditionalAnyColorManaAbility(
                new TapSourceCost(),
                RemovedCountersForCostValue.instance,
                new CountersSourceCount(CounterType.STORAGE),
                new CrucibleOfTheSpiritDragonManaBuilder(),
                false
        );
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE.createInstance()));
        this.addAbility(ability);
    }

    private CrucibleOfTheSpiritDragon(final CrucibleOfTheSpiritDragon card) {
        super(card);
    }

    @Override
    public CrucibleOfTheSpiritDragon copy() {
        return new CrucibleOfTheSpiritDragon(this);
    }
}

class CrucibleOfTheSpiritDragonManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CrucibleOfTheSpiritDragonConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Dragon spells or activate abilities of Dragons";
    }
}

class CrucibleOfTheSpiritDragonConditionalMana extends ConditionalMana {

    public CrucibleOfTheSpiritDragonConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast Dragon spells or activate abilities of Dragons";
        addCondition(new CrucibleOfTheSpiritDragonManaCondition());
    }
}

class CrucibleOfTheSpiritDragonManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object != null && object.hasSubtype(SubType.DRAGON, game)) {
            return true;
        }
        return false;
    }
}
