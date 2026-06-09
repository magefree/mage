package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author muz
 */
public final class TruckToss extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterControlledPermanent(SubType.VEHICLE, "you control a Vehicle")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Vehicle");

    public TruckToss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");

        // This spell costs {2} less to cast if you control a Vehicle.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(2, condition)
        ).setRuleAtTheTop(true).addHint(hint));

        // Truck Toss deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private TruckToss(final TruckToss card) {
        super(card);
    }

    @Override
    public TruckToss copy() {
        return new TruckToss(this);
    }
}
