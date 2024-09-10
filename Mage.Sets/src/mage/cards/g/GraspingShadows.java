package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraspingShadows extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.DREAD, 3);

    public GraspingShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.secondSideCardClazz = mage.cards.s.ShadowsLair.class;

        // Whenever a creature you control attacks alone, it gains deathtouch and lifelink until end of turn. Put a dread counter on Grasping Shadows. Then if there are three or more dread counters on it, transform it.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
                new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                        .setText("it gains deathtouch"),
                true, false
        );
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                .setText("and lifelink until end of turn"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.DREAD.createInstance()));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if there are three or more dread counters on it, transform it"
        ));
        this.addAbility(new TransformAbility());
        this.addAbility(ability);
    }

    private GraspingShadows(final GraspingShadows card) {
        super(card);
    }

    @Override
    public GraspingShadows copy() {
        return new GraspingShadows(this);
    }
}
