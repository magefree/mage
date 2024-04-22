

package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class QuilledSlagwurm extends CardImpl {

    public QuilledSlagwurm (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
    }

    private QuilledSlagwurm(final QuilledSlagwurm card) {
        super(card);
    }

    @Override
    public QuilledSlagwurm copy() {
        return new QuilledSlagwurm(this);
    }

}
