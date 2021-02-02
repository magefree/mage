
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WitchsFamiliar extends CardImpl {

    public WitchsFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.FROG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private WitchsFamiliar(final WitchsFamiliar card) {
        super(card);
    }

    @Override
    public WitchsFamiliar copy() {
        return new WitchsFamiliar(this);
    }
}
