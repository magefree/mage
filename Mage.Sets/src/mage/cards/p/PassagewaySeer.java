package mage.cards.p;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HaveInitiativeCondition;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.InitiativeHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
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
                TargetController.YOU, new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, HaveInitiativeCondition.instance
        ).addHint(InitiativeHint.instance));
    }

    private PassagewaySeer(final PassagewaySeer card) {
        super(card);
    }

    @Override
    public PassagewaySeer copy() {
        return new PassagewaySeer(this);
    }
}
