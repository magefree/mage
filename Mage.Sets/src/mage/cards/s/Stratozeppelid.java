
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class Stratozeppelid extends CardImpl {

    public Stratozeppelid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Stratozeppelid can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private Stratozeppelid(final Stratozeppelid card) {
        super(card);
    }

    @Override
    public Stratozeppelid copy() {
        return new Stratozeppelid(this);
    }
}
