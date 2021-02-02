
package mage.cards.w;

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
 * @author Plopman
 */
public final class WeatherseedFaeries extends CardImpl {

    public WeatherseedFaeries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.FAERIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private WeatherseedFaeries(final WeatherseedFaeries card) {
        super(card);
    }

    @Override
    public WeatherseedFaeries copy() {
        return new WeatherseedFaeries(this);
    }
}
