package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

public class ExuberantFuseling extends CardImpl {

    private static final DynamicValue oilCounters = new CountersSourceCount(CounterType.OIL);

    public ExuberantFuseling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.GOBLIN);
        this.addSubType(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        //Trample
        this.addAbility(TrampleAbility.getInstance());

        //Exuberant Fuseling gets +1/+0 for each oil counter on it.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(oilCounters, StaticValue.get(0),
                Duration.WhileOnBattlefield).setText("{this} gets +1/+0 for each oil counter on it")));

        //When Exuberant Fuseling enters the battlefield and whenever another creature or artifact you control is put
        //into a graveyard from the battlefield, put an oil counter on Exuberant Fuseling.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                new EntersBattlefieldTriggeredAbility(null, false),
                new PutIntoGraveFromBattlefieldAllTriggeredAbility(null, false,
                        StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_OR_CREATURE, false))
                .setTriggerPhrase("When {this} enters the battlefield and whenever another creature or artifact you " +
                        "control is put into a graveyard from the battlefield, "));
    }

    private ExuberantFuseling(final ExuberantFuseling card) {
        super(card);
    }

    @Override
    public ExuberantFuseling copy() {
        return new ExuberantFuseling(this);
    }
}
