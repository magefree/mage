package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FiligreeAttendant extends CardImpl {

    public FiligreeAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Filigree Attendant's power is equal to the number of artifacts you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(
                ArtifactYouControlCount.instance, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a
        ).setText("{this}'s power is equal to the number of artifacts you control")));
    }

    private FiligreeAttendant(final FiligreeAttendant card) {
        super(card);
    }

    @Override
    public FiligreeAttendant copy() {
        return new FiligreeAttendant(this);
    }
}
