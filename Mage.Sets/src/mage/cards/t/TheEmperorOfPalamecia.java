package mage.cards.t;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEmperorOfPalamecia extends CardImpl {

    private final ConditionalManaBuilder manaBuilder
            = new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELLS_NON_CREATURE);
    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1, 3);

    public TheEmperorOfPalamecia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.t.TheLordMasterOfHell.class;

        // {T}: Add {U} or {R}. Spend this mana only to cast a noncreature spell.
        this.addAbility(new ConditionalColoredManaAbility(new TapSourceCost(), Mana.BlueMana(1), manaBuilder));
        this.addAbility(new ConditionalColoredManaAbility(new TapSourceCost(), Mana.RedMana(1), manaBuilder));

        // Whenever you cast a noncreature spell, if at least four mana was spent to cast it, put a +1/+1 counter on The Emperor of Palamecia. Then if it has three or more +1/+1 counters on it, transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_NONCREATURE_SPELL_FOUR_MANA_SPENT, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if it has three or more +1/+1 counters on it, transform it"
        ));
        this.addAbility(ability);
    }

    private TheEmperorOfPalamecia(final TheEmperorOfPalamecia card) {
        super(card);
    }

    @Override
    public TheEmperorOfPalamecia copy() {
        return new TheEmperorOfPalamecia(this);
    }
}
