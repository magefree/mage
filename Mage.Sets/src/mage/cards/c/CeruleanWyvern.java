
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class CeruleanWyvern extends CardImpl {

    public CeruleanWyvern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from green
        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));
    }

    private CeruleanWyvern(final CeruleanWyvern card) {
        super(card);
    }

    @Override
    public CeruleanWyvern copy() {
        return new CeruleanWyvern(this);
    }
}
