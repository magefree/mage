
package mage.cards.n;

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
public final class NimbusOfTheIsles extends CardImpl {

    public NimbusOfTheIsles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private NimbusOfTheIsles(final NimbusOfTheIsles card) {
        super(card);
    }

    @Override
    public NimbusOfTheIsles copy() {
        return new NimbusOfTheIsles(this);
    }
}
