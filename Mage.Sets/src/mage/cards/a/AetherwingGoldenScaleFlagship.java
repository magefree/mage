package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AetherwingGoldenScaleFlagship extends CardImpl {

    public AetherwingGoldenScaleFlagship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Aetherwing, Golden-Scale Flagship's power is equal to the number of artifacts you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerSourceEffect(ArtifactYouControlCount.instance)
                        .setText("{this}'s power is equal to the number of artifacts you control")
        ));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private AetherwingGoldenScaleFlagship(final AetherwingGoldenScaleFlagship card) {
        super(card);
    }

    @Override
    public AetherwingGoldenScaleFlagship copy() {
        return new AetherwingGoldenScaleFlagship(this);
    }
}
