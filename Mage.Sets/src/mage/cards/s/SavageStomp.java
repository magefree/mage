package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavageStomp extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent(SubType.DINOSAUR, "a Dinosaur you control");

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public SavageStomp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Savage Stomp costs {2} less to cast if it targets a Dinosaur you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Put a +1/+1 counter on target creature you control. Then that creature fights target creature you don't control.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        this.getSpellAbility().addEffect(effect);
        effect = new FightTargetsEffect();
        effect.setText("Then that creature fights target creature you don't control. " +
                "<i>(Each deals damage equal to its power to the other.)</i>");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private SavageStomp(final SavageStomp card) {
        super(card);
    }

    @Override
    public SavageStomp copy() {
        return new SavageStomp(this);
    }
}
