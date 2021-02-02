
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RingwardenOwl extends CardImpl {

    public RingwardenOwl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private RingwardenOwl(final RingwardenOwl card) {
        super(card);
    }

    @Override
    public RingwardenOwl copy() {
        return new RingwardenOwl(this);
    }
}
