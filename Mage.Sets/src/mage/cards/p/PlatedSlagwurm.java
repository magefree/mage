
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PlatedSlagwurm extends CardImpl {

    public PlatedSlagwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        this.addAbility(HexproofAbility.getInstance());
    }

    private PlatedSlagwurm(final PlatedSlagwurm card) {
        super(card);
    }

    @Override
    public PlatedSlagwurm copy() {
        return new PlatedSlagwurm(this);
    }
}
