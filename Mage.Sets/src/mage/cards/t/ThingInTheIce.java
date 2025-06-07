package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ThingInTheIce extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    private static final Condition condition = new SourceHasCounterCondition(CounterType.ICE, ComparisonType.EQUAL_TO, 0);

    public ThingInTheIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.a.AwokenHorror.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Thing in the Ice enters the battlefield with four ice counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.ICE.createInstance(4)).setText("with four ice counters on it")
        ));

        // Whenever you cast an instant or sorcery spell, remove an ice counter from Thing in the Ice. Then if it has no ice counters on it, transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SpellCastControllerTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.ICE.createInstance(1)), filter, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if it has no ice counters on it, transform it"
        ));
        this.addAbility(ability);
    }

    private ThingInTheIce(final ThingInTheIce card) {
        super(card);
    }

    @Override
    public ThingInTheIce copy() {
        return new ThingInTheIce(this);
    }
}
