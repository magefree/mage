

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class MakindiGriffin extends CardImpl {

    public MakindiGriffin (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.GRIFFIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
    }

    private MakindiGriffin(final MakindiGriffin card) {
        super(card);
    }

    @Override
    public MakindiGriffin copy() {
        return new MakindiGriffin(this);
    }

}
