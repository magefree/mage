
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SabertoothNishoba extends CardImpl {

    public SabertoothNishoba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // protection from blue and from red
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE, ObjectColor.RED));
    }

    private SabertoothNishoba(final SabertoothNishoba card) {
        super(card);
    }

    @Override
    public SabertoothNishoba copy() {
        return new SabertoothNishoba(this);
    }
}
