package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HaveInitiativeCondition;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PassagewaySeer extends CardImpl {

    public PassagewaySeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Passageway Seer enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // At the beginning of your end step, if you have the initiative, put a +1/+1 counter on Passageway Seer.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, HaveInitiativeCondition.instance, false
        ));
    }

    private PassagewaySeer(final PassagewaySeer card) {
        super(card);
    }

    @Override
    public PassagewaySeer copy() {
        return new PassagewaySeer(this);
    }
}
