
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class HomicidalSeclusion extends CardImpl {

    private static final String rule = "As long as you control exactly one creature, that creature gets +3/+1";

    public HomicidalSeclusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{B}");

        // As long as you control exactly one creature, that creature gets +3/+1 and has lifelink.
        ContinuousEffect boostEffect = new BoostControlledEffect(3, 1, Duration.WhileOnBattlefield);
        Effect effect = new ConditionalContinuousEffect(boostEffect, new CreatureCountCondition(1, TargetController.YOU), rule);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ContinuousEffect lifelinkEffect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield);
        effect = new ConditionalContinuousEffect(lifelinkEffect, new CreatureCountCondition(1, TargetController.YOU), "and has lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public HomicidalSeclusion(final HomicidalSeclusion card) {
        super(card);
    }

    @Override
    public HomicidalSeclusion copy() {
        return new HomicidalSeclusion(this);
    }
}
