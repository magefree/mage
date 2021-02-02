
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class DjinnOfTheLamp extends CardImpl {

    public DjinnOfTheLamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.DJINN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private DjinnOfTheLamp(final DjinnOfTheLamp card) {
        super(card);
    }

    @Override
    public DjinnOfTheLamp copy() {
        return new DjinnOfTheLamp(this);
    }
}
