package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfKaladesh extends TransformingDoubleFacedCard {

    public InvasionOfKaladesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{U}{R}",
                "Aetherwing, Golden-Scale Flagship",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "UR"
        );

        // Invasion of Kaladesh
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Kaladesh enters the battlefield, create a 1/1 colorless Thopter artifact creature token with flying.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken())));

        // Aetherwing, Golden-Scale Flagship
        this.getRightHalfCard().setPT(0, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Aetherwing, Golden-Scale Flagship's power is equal to the number of artifacts you control.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerSourceEffect(ArtifactYouControlCount.instance)
                        .setText("{this}'s power is equal to the number of artifacts you control")
        ));

        // Crew 1
        this.getRightHalfCard().addAbility(new CrewAbility(1));
    }

    private InvasionOfKaladesh(final InvasionOfKaladesh card) {
        super(card);
    }

    @Override
    public InvasionOfKaladesh copy() {
        return new InvasionOfKaladesh(this);
    }
}
