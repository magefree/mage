package mage.cards.f;

import mage.abilities.common.CastAsThoughItHadFlashIfConditionAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FatedClash extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_ATTACKING_CREATURE, false),
            new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_BLOCKING_CREATURES, false)
    );

    public FatedClash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // You may cast this spell as though it had flash if a creature is attacking and a creature is blocking.
        this.addAbility(new CastAsThoughItHadFlashIfConditionAbility(
                condition, "you may cast this spell as though it had flash " +
                "if a creature is attacking and a creature is blocking"
        ));

        // Target creature you control and target creature an opponent controls each gain indestructible until end of turn. Then destroy all creatures.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("target creature you control and target creature an opponent controls each gain indestructible until end of turn")
                .setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES).concatBy("Then"));
    }

    private FatedClash(final FatedClash card) {
        super(card);
    }

    @Override
    public FatedClash copy() {
        return new FatedClash(this);
    }
}
