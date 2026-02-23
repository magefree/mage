package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class DormantGrove extends TransformingDoubleFacedCard {

    public DormantGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{3}{G}",
                "Gnarled Grovestrider",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.TREEFOLK}, "G");

        // Dormant Grove
        // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control.
        // Then if that creature has toughness 6 or greater, transform Dormant Grove.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(),
                DormatGroveCondition.instance,
                "Then if that creature has toughness 6 or greater, transform {this}"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Gnarled Grovestrider
        this.getRightHalfCard().setPT(3, 6);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Other creatures you control have vigilance.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));
    }

    private DormantGrove(final DormantGrove card) {
        super(card);
    }

    @Override
    public DormantGrove copy() {
        return new DormantGrove(this);
    }
}

enum DormatGroveCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            return permanent.getToughness().getValue() >= 6;
        }
        return false;
    }

    @Override
    public String toString() {
        return "that creature has toughness 6 or greater";
    }
}
