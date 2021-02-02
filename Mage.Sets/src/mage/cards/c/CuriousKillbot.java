
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class CuriousKillbot extends CardImpl {

    public CuriousKillbot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.KILLBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private CuriousKillbot(final CuriousKillbot card) {
        super(card);
    }

    @Override
    public CuriousKillbot copy() {
        return new CuriousKillbot(this);
    }
}
