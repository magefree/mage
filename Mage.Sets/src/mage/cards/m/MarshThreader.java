

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class MarshThreader extends CardImpl {

    public MarshThreader (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SCOUT);
        this.color.setWhite(true);        
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(new SwampwalkAbility());
    }

    private MarshThreader(final MarshThreader card) {
        super(card);
    }

    @Override
    public MarshThreader copy() {
        return new MarshThreader(this);
    }

}
