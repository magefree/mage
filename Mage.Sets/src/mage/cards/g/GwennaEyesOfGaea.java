package mage.cards.g;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;

/**
 *
 * @author weirddan455
 */
public final class GwennaEyesOfGaea extends CardImpl {

    private static final FilterCreatureSpell filter
            = new FilterCreatureSpell("a creature spell with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public GwennaEyesOfGaea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Add two mana in any combination of colors. Spend this mana only to cast creature spells or activate abilities of a creature or creature card.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new GwennaEyesOfGaeaManaBuilder()));

        // Whenever you cast a creature spell with power 5 or greater, put a +1/+1 counter on Gwenna, Eyes of Gaea and untap it.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                filter, false
        );
        ability.addEffect(new UntapSourceEffect().setText("and untap it"));
        this.addAbility(ability);
    }

    private GwennaEyesOfGaea(final GwennaEyesOfGaea card) {
        super(card);
    }

    @Override
    public GwennaEyesOfGaea copy() {
        return new GwennaEyesOfGaea(this);
    }
}

class GwennaEyesOfGaeaManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new GwennaEyesOfGaeaConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creature spells or activate abilities of a creature or creature card";
    }
}

class GwennaEyesOfGaeaConditionalMana extends ConditionalMana {

    public GwennaEyesOfGaeaConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast creature spells or activate abilities of a creature or creature card";
        addCondition(GwennaEyesOfGaeaManaCondition.instance);
    }
}

enum GwennaEyesOfGaeaManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isCreature(game);
    }
}
