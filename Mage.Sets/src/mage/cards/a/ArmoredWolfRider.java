

package mage.cards.a;

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


public final class ArmoredWolfRider extends CardImpl {

    public ArmoredWolfRider (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);


        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

    }

    private ArmoredWolfRider(final ArmoredWolfRider card) {
        super(card);
    }

    @Override
    public ArmoredWolfRider copy() {
        return new ArmoredWolfRider(this);
    }

}
