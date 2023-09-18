package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author noxx
 */
public final class HomicidalSeclusion extends CardImpl {

    private static final Condition condition = new CreatureCountCondition(1, TargetController.YOU);

    public HomicidalSeclusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        // As long as you control exactly one creature, that creature gets +3/+1 and has lifelink.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(3, 1, Duration.WhileOnBattlefield),
                condition, "As long as you control exactly one creature, that creature gets +3/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ), condition, "and has lifelink"));
        this.addAbility(ability.addHint(CreaturesYouControlHint.instance));
    }

    private HomicidalSeclusion(final HomicidalSeclusion card) {
        super(card);
    }

    @Override
    public HomicidalSeclusion copy() {
        return new HomicidalSeclusion(this);
    }
}
