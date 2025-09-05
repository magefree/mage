package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.AbilityImpl;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmolderingEgg extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.EMBER, 7);

    public SmolderingEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.a.AshmouthDragon.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, put a number of ember counters on Smoldering Egg equal to the amount of mana spent to cast that spell. Then if Smoldering Egg has seven or more ember counters on it, remove them and transform Smoldering Egg.
        this.addAbility(new TransformAbility());
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.EMBER.createInstance(), SmolderingEggValue.instance)
                        .setText("put a number of ember counters on {this} equal to the amount of mana spent to cast that spell"),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.EMBER), condition,
                "Then if {this} has seven or more ember counters on it, remove them and transform {this}"
        ).addEffect(new TransformSourceEffect()));
        this.addAbility(ability);
    }

    private SmolderingEgg(final SmolderingEgg card) {
        super(card);
    }

    @Override
    public SmolderingEgg copy() {
        return new SmolderingEgg(this);
    }
}

enum SmolderingEggValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Spell) effect.getValue("spellCast"))
                .map(Spell::getSpellAbility)
                .map(AbilityImpl::getManaCostsToPay)
                .map(ManaCost::getUsedManaToPay)
                .map(Mana::count)
                .orElse(0);
    }

    @Override
    public SmolderingEggValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
