package mage.cards.p;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianSkyflayer extends CardImpl {

    public PhyrexianSkyflayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private PhyrexianSkyflayer(final PhyrexianSkyflayer card) {
        super(card);
    }

    @Override
    public PhyrexianSkyflayer copy() {
        return new PhyrexianSkyflayer(this);
    }
}
