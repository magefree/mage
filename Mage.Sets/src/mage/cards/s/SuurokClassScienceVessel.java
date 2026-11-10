package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SuurokClassScienceVessel extends CardImpl {

    public SuurokClassScienceVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Station
        this.addAbility(new StationAbility());

        // STATION 5+
        // Flying
        // 3/2
        this.addAbility(new StationLevelAbility(5)
            .withLevelAbility(FlyingAbility.getInstance())
            .withPT(3, 2));
    }

    private SuurokClassScienceVessel(final SuurokClassScienceVessel card) {
        super(card);
    }

    @Override
    public SuurokClassScienceVessel copy() {
        return new SuurokClassScienceVessel(this);
    }
}
