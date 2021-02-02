
package mage.cards.r;

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
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class RuleOfTwo extends CardImpl {

    public RuleOfTwo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // As long as you control exatly two creatures, those creatures get +2/+0 and have deathtouch and lifelink.
        ContinuousEffect boostEffect = new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield);
        Effect effect = new ConditionalContinuousEffect(boostEffect, new CreatureCountCondition(2, TargetController.YOU),
                "As long as you control exactly two creatures, those creatures get +2/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ContinuousEffect deathtouchEffect = new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent());
        effect = new ConditionalContinuousEffect(deathtouchEffect, new CreatureCountCondition(2, TargetController.YOU),
                "and have deathtouch");
        ability.addEffect(effect);
        ContinuousEffect lifelinkEffect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, new FilterCreaturePermanent());
        effect = new ConditionalContinuousEffect(lifelinkEffect, new CreatureCountCondition(2, TargetController.YOU),
                "and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private RuleOfTwo(final RuleOfTwo card) {
        super(card);
    }

    @Override
    public RuleOfTwo copy() {
        return new RuleOfTwo(this);
    }
}
