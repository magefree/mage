package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CollectEvidenceAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VituGhaziInspector extends CardImpl {

    public VituGhaziInspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As an additional cost to cast this spell, you may collect evidence 6.
        this.addAbility(new CollectEvidenceAbility(6));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Vitu-Ghazi Inspector enters the battlefield, if evidence was collected, put a +1/+1 counter on target creature and you gain 2 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance())),
                CollectedEvidenceCondition.instance, "When {this} enters the battlefield, if evidence was " +
                "collected, put a +1/+1 counter on target creature and you gain 2 life."
        );
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private VituGhaziInspector(final VituGhaziInspector card) {
        super(card);
    }

    @Override
    public VituGhaziInspector copy() {
        return new VituGhaziInspector(this);
    }
}
