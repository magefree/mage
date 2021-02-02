
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * @author LevelX2
 */
public final class DeadlyWanderings extends CardImpl {

    public DeadlyWanderings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // As long as you control exactly one creature, that creature gets +2/+0 and has deathtouch and lifelink.
        ContinuousEffect boostEffect = new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield);
        Effect effect = new ConditionalContinuousEffect(boostEffect, new CreatureCountCondition(1, TargetController.YOU),
                "As long as you control exactly one creature, that creature gets +2/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ContinuousEffect deathtouchEffect = new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE);
        effect = new ConditionalContinuousEffect(deathtouchEffect, new CreatureCountCondition(1, TargetController.YOU),
                "and has deathtouch");
        ability.addEffect(effect);
        ContinuousEffect lifelinkEffect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE);
        effect = new ConditionalContinuousEffect(lifelinkEffect, new CreatureCountCondition(1, TargetController.YOU),
                "and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private DeadlyWanderings(final DeadlyWanderings card) {
        super(card);
    }

    @Override
    public DeadlyWanderings copy() {
        return new DeadlyWanderings(this);
    }
}
