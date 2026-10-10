package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.abilities.keyword.StoriedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

public final class BifurMelodicRider extends CardImpl {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DWARF);

    public BifurMelodicRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R/W}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Storied
        this.addAbility(new StoriedAbility());

        // Whenever Bifur enters or attacks, put a +1/+1 counter on target creature.
        final Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // As long as you have an enduring story, if a triggered ability of a Dwarf you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filter, EnduringStoryCondition.instance)).addHint(EnduringStoryCondition.getHint()));
    }

    private BifurMelodicRider(final BifurMelodicRider card) {
        super(card);
    }

    @Override
    public BifurMelodicRider copy() {
        return new BifurMelodicRider(this);
    }
}
