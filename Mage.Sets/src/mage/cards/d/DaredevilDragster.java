package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttackedOrBlockedThisCombatSourceCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class DaredevilDragster extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.VELOCITY, ComparisonType.OR_GREATER, 2);

    public DaredevilDragster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At end of combat, if Daredevil Dragster attacked or blocked this combat, put a velocity counter on it. Then if it has two or more velocity counters on it, sacrifice it and draw two cards.
        Ability ability = new EndOfCombatTriggeredAbility(
                new AddCountersSourceEffect(CounterType.VELOCITY.createInstance())
                        .setText("put a velocity counter on it"), false
        ).withInterveningIf(AttackedOrBlockedThisCombatSourceCondition.instance);
        ability.addEffect(new ConditionalOneShotEffect(
                new SacrificeSourceEffect(), condition, "Then if it has two " +
                "or more velocity counters on it, sacrifice it and draw two cards"
        ).addEffect(new DrawCardSourceControllerEffect(2)));
        this.addAbility(ability, new AttackedOrBlockedThisCombatWatcher());

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private DaredevilDragster(final DaredevilDragster card) {
        super(card);
    }

    @Override
    public DaredevilDragster copy() {
        return new DaredevilDragster(this);
    }
}
