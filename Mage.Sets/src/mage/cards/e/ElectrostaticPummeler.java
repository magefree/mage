
package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ElectrostaticPummeler extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public ElectrostaticPummeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Electrostatic Pummeler enters the battlefield, you get {E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3)));

        // Pay {E}{E}{E}: Electrostatic Pummeler gets +X/+X until end of turn, where X is its power.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                xValue, xValue, Duration.EndOfTurn, true
        ).setText("{this} gets +X/+X until end of turn, where X is its power"), new PayEnergyCost(3)));
    }

    private ElectrostaticPummeler(final ElectrostaticPummeler card) {
        super(card);
    }

    @Override
    public ElectrostaticPummeler copy() {
        return new ElectrostaticPummeler(this);
    }
}
