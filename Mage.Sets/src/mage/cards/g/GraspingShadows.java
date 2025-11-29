package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraspingShadows extends TransformingDoubleFacedCard {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.DREAD, 3);

    public GraspingShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{3}{B}",
                "Shadows' Lair",
                new SuperType[]{}, new CardType[]{CardType.LAND}, new SubType[]{SubType.CAVE}, ""
        );

        // Grasping Shadows
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
        this.getLeftHalfCard().addAbility(ability);

        // Shadows' Lair
        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());

        // {B}, {T}, Remove a dread counter from Shadows' Lair: You draw a card and you lose 1 life.
        Ability backAbility = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1, true), new ManaCostsImpl<>("{B}")
        );
        backAbility.addCost(new TapSourceCost());
        backAbility.addCost(new RemoveCountersSourceCost(CounterType.DREAD.createInstance()));
        backAbility.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.getRightHalfCard().addAbility(backAbility);
    }

    private GraspingShadows(final GraspingShadows card) {
        super(card);
    }

    @Override
    public GraspingShadows copy() {
        return new GraspingShadows(this);
    }
}
