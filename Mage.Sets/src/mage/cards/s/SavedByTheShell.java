package mage.cards.s;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class SavedByTheShell extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterControlledPermanent(SubType.TURTLE, "you control a Turtle")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Turtle");

    public SavedByTheShell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // This spell costs {1} less to cast if you control a Turtle.
                this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition)
        ).setRuleAtTheTop(true).addHint(hint));

        // Put a +1/+1 counter on target creature you control. It gains trample, hexproof, and indestructible until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("it gains trample"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance()).setText(", hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()).setText(", and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private SavedByTheShell(final SavedByTheShell card) {
        super(card);
    }

    @Override
    public SavedByTheShell copy() {
        return new SavedByTheShell(this);
    }
}
