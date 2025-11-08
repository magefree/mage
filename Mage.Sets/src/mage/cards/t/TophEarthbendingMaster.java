package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TophEarthbendingMaster extends CardImpl {

    private static final DynamicValue xValue = new CountersControllerCount(CounterType.EXPERIENCE);

    public TophEarthbendingMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Landfall -- Whenever a land you control enters, you get an experience counter.
        this.addAbility(new LandfallAbility(new AddCountersPlayersEffect(
                CounterType.EXPERIENCE.createInstance(), TargetController.YOU
        )));

        // Whenever you attack, earthbend X, where X is the number of experience counters you have.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new EarthbendTargetEffect(xValue), 1);
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private TophEarthbendingMaster(final TophEarthbendingMaster card) {
        super(card);
    }

    @Override
    public TophEarthbendingMaster copy() {
        return new TophEarthbendingMaster(this);
    }
}
