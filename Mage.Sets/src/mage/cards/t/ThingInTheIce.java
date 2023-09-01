package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ThingInTheIce extends TransformingDoubleFacedCard {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.ICE, 0, 0);
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Horror creatures");

    static {
        filter.add(Predicates.not(SubType.HORROR.getPredicate()));
    }

    public ThingInTheIce(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HORROR}, "{1}{U}",
                "Awoken Horror",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.KRAKEN, SubType.HORROR}, "U"
        );
        this.getLeftHalfCard().setPT(0, 4);
        this.getRightHalfCard().setPT(7, 8);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // Thing in the Ice enters the battlefield with four ice counters on it.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.ICE.createInstance(4)),
                "with four ice counters on it"
        ));

        // Whenever you cast an instant or sorcery spell, remove an ice counter from Thing in the Ice. Then if it has no ice counters on it, transform it.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.ICE.createInstance(1)),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if it has no ice counters on it, transform it"
        ));
        this.getLeftHalfCard().addAbility(ability);

        // Awoken Horror
        // When this creature transforms into Awoken Horror, return all non-Horror creatures to their owners' hands.
        this.getRightHalfCard().addAbility(new TransformIntoSourceTriggeredAbility(new ReturnToHandFromBattlefieldAllEffect(filter)));
    }

    private ThingInTheIce(final ThingInTheIce card) {
        super(card);
    }

    @Override
    public ThingInTheIce copy() {
        return new ThingInTheIce(this);
    }
}
