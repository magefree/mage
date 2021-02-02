
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GeistOfTheMoors extends CardImpl {

    public GeistOfTheMoors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private GeistOfTheMoors(final GeistOfTheMoors card) {
        super(card);
    }

    @Override
    public GeistOfTheMoors copy() {
        return new GeistOfTheMoors(this);
    }
}
