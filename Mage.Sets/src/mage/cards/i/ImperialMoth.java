package mage.cards.i;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImperialMoth extends CardImpl {

    public ImperialMoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private ImperialMoth(final ImperialMoth card) {
        super(card);
    }

    @Override
    public ImperialMoth copy() {
        return new ImperialMoth(this);
    }
}
