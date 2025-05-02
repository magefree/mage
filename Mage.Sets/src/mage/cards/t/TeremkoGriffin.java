

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class TeremkoGriffin extends CardImpl {

    public TeremkoGriffin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.GRIFFIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private TeremkoGriffin(final TeremkoGriffin card) {
        super(card);
    }

    @Override
    public TeremkoGriffin copy() {
        return new TeremkoGriffin(this);
    }

}
