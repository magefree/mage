package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CephalopodSentry extends CardImpl {

    public CephalopodSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SQUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cephalopod Sentry's power is equal to the number of artifacts you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(ArtifactYouControlCount.instance)
                .setText("{this}'s power is equal to the number of artifacts you control")
        ));
    }

    private CephalopodSentry(final CephalopodSentry card) {
        super(card);
    }

    @Override
    public CephalopodSentry copy() {
        return new CephalopodSentry(this);
    }
}
