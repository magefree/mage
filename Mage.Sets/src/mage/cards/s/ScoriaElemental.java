

package mage.cards.s;

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
public final class ScoriaElemental extends CardImpl {

    public ScoriaElemental (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.color.setRed(true);        
        this.power = new MageInt(6);
        this.toughness = new MageInt(1);
    }

    private ScoriaElemental(final ScoriaElemental card) {
        super(card);
    }

    @Override
    public ScoriaElemental copy() {
        return new ScoriaElemental(this);
    }

}
